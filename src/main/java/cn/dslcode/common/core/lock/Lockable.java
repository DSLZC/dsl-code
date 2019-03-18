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
public @interface Lockable {


    /** lock key前缀 */
    String key();

	/** 加锁策略，默认redis，可选zookeeper */
	Strategy strategy() default Strategy.REDIS;

	/** 是否可重入 */
	boolean reentrant() default false;

	/** 锁过期时间/毫秒 */
    int timeout() default 5000;

	/** 等待时间/毫秒 */
	int waittime() default 0;

    /**
     * 方法参数field名称，支持多级，如：对象名.对象名.对象名。利用反射取值，用于和key组合起来组成lock名称
     */
    String[] fields();

	/**
	 * 加锁策略
	 */
	enum Strategy {
		REDIS,
		ZOOKEEPER
	}

}
