package cn.dslcode.common.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by dongsilin on 2018/1/12.
 */
public class CompletableFutureTest {

    ExecutorService executor = Executors.newFixedThreadPool(10);

    private long getTime(){
        long start = System.currentTimeMillis();
        System.out.println(start);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end);
        return end - start;
    }

    @Test
    public void test1() throws InterruptedException, ExecutionException, TimeoutException {
        Long time = CompletableFuture.supplyAsync(() -> getTime(), executor).get(1005, TimeUnit.MILLISECONDS);
        System.out.println(time);
        executor.shutdown();
    }

    @Test
    public void test2() throws InterruptedException, ExecutionException, TimeoutException {
        Long time = executor.submit(() -> getTime()).get(1005, TimeUnit.MILLISECONDS);
        System.out.println(time);
        executor.shutdown();
    }

}


