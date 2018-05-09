package cn.dslcode.common.core.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongsilin
 * @version 2018/4/19.
 * 分布式锁注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DistributedLock {


    /**
     * lock key
     */
    String key();

    /**
     * 锁过期时间/秒
     */
    int timeout() default 5;

    /**
     * 方法参数的位置，从0开始
     */
    int pIndex();

    /**
     * 参数内部取值的参数名称，支持多级，如：对象名.对象名.对象名
     */
    String propertyName() default "";

}
