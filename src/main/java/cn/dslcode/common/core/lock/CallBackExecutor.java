package cn.dslcode.common.core.lock;

/**
 * @author dongsilin
 * @version 2018/8/31.
 */
@FunctionalInterface
public interface CallBackExecutor<R> {

	R execute() throws Throwable;
}
