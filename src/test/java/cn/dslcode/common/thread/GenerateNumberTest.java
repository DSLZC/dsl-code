package cn.dslcode.common.thread;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by dongsilin on 2018/1/14.
 */
public class GenerateNumberTest {

    @Test
    public void test() throws IOException {
        long start = System.currentTimeMillis();
        int max = 10000000;
        System.out.println(max);
        System.out.println(Integer.MAX_VALUE);
        int threadNum = 10;
        int step = max / threadNum;
        Thread[] threads = new Thread[threadNum];

        GenerateNumberTest $this = this;

        OutputStream os = new FileOutputStream(new File("D:\\a.txt"), true);
        OutputStreamWriter writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        IntStream.range(0, threadNum).forEach(i -> {
            threads[i] = new Thread(() -> {
                String[] nums = new String[step];
                int n = 0;

                for (int num = step * i; num < step * (i + 1); num++) {
                    if (i < 1){
                        String snum = String.valueOf(num);
                        switch (snum.length()){
                            case 1: snum = "0000000" + snum;break;
                            case 2: snum = "000000" + snum;break;
                            case 3: snum = "00000" + snum;break;
                            case 4: snum = "0000" + snum;break;
                            case 5: snum = "000" + snum;break;
                            case 6: snum = "00" + snum;break;
                            case 7: snum = "0" + snum;break;
                        }
                        nums[n++] = snum;
                    } else {
                        nums[n++] = String.valueOf(num);
                    }
                }
                try {
                    if (i > 0) {
                        synchronized (threads[i]){
                            threads[i].wait();
                        }
                    }
                    bw.write(Arrays.stream(nums).collect(Collectors.joining("\n", "", "\n")));
                    nums = null;
                    if (i == threadNum-1) {
                        System.out.println("****************over****************");
                        synchronized ($this){
                            $this.notify();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (i < threadNum-1) synchronized (threads[i+1]){
                    threads[i+1].notify();
                }
                }
            });
        });

        Arrays.stream(threads).forEach(Thread::start);

        synchronized ($this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) bw.flush();
                if (os != null) os.close();
                if (writer != null) writer.close();
                if (bw != null) bw.close();
            }
        }

        long end = System.currentTimeMillis();
        long used = (end - start) / 1000;
        System.out.println(used);
    }
}
