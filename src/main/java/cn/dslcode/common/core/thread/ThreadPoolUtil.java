package cn.dslcode.common.core.thread;

import cn.dslcode.common.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongsilin on 2017/7/21.
 */
public class ThreadPoolUtil {

    private static ExecutorService executorService = null;
    private static final int CORE_THREAD_SIZE = Runtime.getRuntime().availableProcessors();// 核心线程数
    private static final int MAX_THREAD_SIZE = CORE_THREAD_SIZE * 2;// 最大线程数，即线程池中允许存在的最大线程数
    private static final int KEEP_ALIVE_TIME = 10;// 线程存活时间，对于超过核心线程数的线程，当线程处理空闲状态下，且维持时间达到keepAliveTime时，线程将被销毁


    /**
     * 初始化线程池
     * @param threadSize 池容量大小
     */
    public static final void initPool(int threadSize){
        if (null == executorService) {
            synchronized (ThreadPoolUtil.class) {
                if (null != executorService) return;
                if (threadSize > MAX_THREAD_SIZE) throw new RuntimeException(String.format("当前线程数量：%s，大于最大线程数量：%s", threadSize, MAX_THREAD_SIZE));
                // newFixedThreadPool使用的队列是new LinkedBlockingQueue，这是一个无边界队列，如果不断的往里加任务时，最终会导致内存问题
                 executorService = Executors.newFixedThreadPool(threadSize);
//                BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);
//                ThreadFactory threadFactory = new CustomThreadFactory();
//                RejectedExecutionHandler rejectedHandler = new CustomRejectedExecutionHandler();
//                executorService = new ThreadPoolExecutor(
//                        threadSize,
//                        MAX_THREAD_SIZE,
//                        KEEP_ALIVE_TIME,
//                        TimeUnit.SECONDS,
//                        workQueue,
//                        threadFactory,
//                        rejectedHandler
//                );
            }
        }
    }

    /**
     * 关闭线程池
     */
    public static final void closePool(){
        if (null != executorService) executorService.shutdown();
    }

    /**
     * 执行Runnable任务
     * @param task
     */
    public static final void run(Runnable task){
        initPool(CORE_THREAD_SIZE);
        executorService.submit(task);
    }

    /**
     * 执行Callable任务
     * @param task
     * @param <T> 返回值类型
     * @return Future实例，通过调用Future.get()获得线程返回值
     */
    public static final <T> Future<T> runCallable(Callable<T> task){
        initPool(CORE_THREAD_SIZE);
        return executorService.submit(task);
    }


    /**
     * 自定义threadFactory
     */
    private static class CustomThreadFactory implements ThreadFactory{
        private static final Logger log = LoggerFactory.getLogger(CustomThreadFactory.class);
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
        private final ThreadGroup threadGroup;
        private final String namePrefix;

        public CustomThreadFactory() {
            SecurityManager securityManager = System.getSecurityManager();
            threadGroup = securityManager == null? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
            // 自定义创建的线程名称，方便问题排查
            namePrefix = StringUtil.append2String("custom-pool-", POOL_NUMBER.getAndIncrement(), "-thread-");
        }

        @Override
        public Thread newThread(Runnable runnable) {
            log.debug("**********开启新线程......");
            Thread thread = new Thread(threadGroup, runnable, StringUtil.append2String(namePrefix, THREAD_NUMBER.getAndIncrement()), 0);
            if (thread.isDaemon()) thread.setDaemon(false);
            if (thread.getPriority() != Thread.NORM_PRIORITY) thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }

    /**
     * 自定义RejectedExecutionHandler
     */
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler{
        private static final Logger log = LoggerFactory.getLogger(CustomRejectedExecutionHandler.class);
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            log.warn("**********向线程池添加任务被拒绝，由当前线程直接执行......");
            // option1：当前线程执行
            runnable.run();
        }
    }

    public static void main(String[] args){
        initPool(CORE_THREAD_SIZE);
        ThreadPoolUtil.closePool();
    }

//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        run(() -> {
//            int sum = 0;
//            for(int i = 1; i < 10; i++) {
//                sum += i;
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
//            }
//            System.out.println(sum);
//        });
//        Future<Integer> future =  run(() -> {
//            int sum = 0;
//            for(int i = 1; i < 10; i++) {
//                sum += i;
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
//            }
//            System.out.println("f =  "+ sum);
//            return sum;
//        });
//        System.out.println("ff = " + future.get());
//
//        Thread.sleep(2000);
//        closePool();
//    }
}
