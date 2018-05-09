package cn.dslcode.common.core.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongsilin
 * @version 2018/4/19.
 */
@Slf4j
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 尝试添加锁
     * @param lockKey
     * @param lockValue
     * @param timeoutSeconds 锁过期时间/秒
     * @return true：加锁成功 fasle：加锁失败
     */
    public boolean tryAdd(String lockKey, String lockValue, int timeoutSeconds){
//        String script = "return redis.call('SET', KEYS[1], '1', 'NX', 'PX', ARGV[1])";
//        String script = "if redis.call('SETNX', KEYS[1], '1') == 1 then redis.call('EXPIRE', KEYS[1], ARGV[1]); return true; else return false; end";
        String script = "if redis.call('EXISTS', KEYS[1]) == 0 then redis.call('SETEX', KEYS[1], ARGV[2], ARGV[1]); return true else return false end";
        List<String> keys = new ArrayList<>(1);
        keys.add(lockKey);
        Boolean tryLock = (Boolean) stringRedisTemplate.execute(new DefaultRedisScript(script, Boolean.class), keys, lockValue, String.valueOf(timeoutSeconds));
        return tryLock.booleanValue();
    }

    /**
     * 释放锁
     * @param lockKey
     * @param lockValue
     */
    public void release(String lockKey, String lockValue) {
        String script = "if redis.call('GET', KEYS[1]) == ARGV[1] then redis.call('DEL', KEYS[1]) end";
        List<String> keys = new ArrayList<>(1);
        keys.add(lockKey);
        stringRedisTemplate.execute(new DefaultRedisScript(script, Void.class), keys, lockValue);
//	    stringRedisTemplate.delete(lockKey);
    }

}
