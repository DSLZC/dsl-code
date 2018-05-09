package cn.dslcode.common.core.lock;

import cn.dslcode.common.core.exception.BaseBizException;
import cn.dslcode.common.core.random.RandomCode;
import cn.dslcode.common.core.reflect.ReflectUtil;
import cn.dslcode.common.core.string.StringUtil;
import cn.dslcode.common.web.response.CommonResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongsilin
 * @version 2018/4/19.
 * Redis 分布式锁 AOP
 */
@Slf4j
@Aspect
@Configuration
public class RedisDistributedLockAspect {

    @Autowired
    private RedisLock redisLock;

    /**
     * 切入点
     */
    @Pointcut("@annotation(cn.dslcode.common.core.lock.DistributedLock)")
    public void lockPointcut() {
    }


    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("lockPointcut()")
    public Object lockAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===========>RedisDistributedLockAspect  begin ............");
        // 获取注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DistributedLock distributedLock = signature.getMethod().getAnnotation(DistributedLock.class);

        // 获取注解参数
        Object keyParam = joinPoint.getArgs()[distributedLock.pIndex()];
        if (StringUtil.isNotEmpty(distributedLock.propertyName())) {
            keyParam = ReflectUtil.invokeGetter(keyParam, distributedLock.propertyName());
        }

        // 获取lock的key以及value
        String lockKey = distributedLock.key().concat(keyParam.toString());
        String lockValue = String.valueOf(RandomCode.random.nextInt(10000));
        boolean tryLock = false;
        try {
            // 尝试加锁
            tryLock = redisLock.tryAdd(lockKey, lockValue, distributedLock.timeout());
            if (tryLock) {
                // 继续执行业务
                return joinPoint.proceed();
            } else {
                throw new BaseBizException(CommonResponseCode.REPEAT_SUBMIT);
            }
        } finally {
            // 释放锁
            if (tryLock) {
                redisLock.release(lockKey, lockValue);
            }
            log.info("===========>RedisDistributedLockAspect  end ............");
        }
    }


}
