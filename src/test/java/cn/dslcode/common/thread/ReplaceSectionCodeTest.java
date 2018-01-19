package cn.dslcode.common.thread;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Created by dongsilin on 2018/1/16.
 * 替换一段代码
 */
public class ReplaceSectionCodeTest {

    @Test
    public void test() throws IOException {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();

        IntStream.range(-3, 3).forEach(i -> {
            set.add(i);
            list.add(i);
        });

        IntStream.range(-3, 0).forEach(i -> {
            set.remove(i);
            list.remove(i);
        });

        System.out.println(set + "，" + list);


//        set.remove(Integer.valueOf(i));
//        list.remove(Integer.valueOf(i));
//        File rootFile = new File("D:\\dongsilin\\workspace\\tcc-framework\\tcc-transaction-core");
//        File[] listFiles = rootFile.listFiles();
//        Arrays.stream(listFiles).forEach(f -> {
//            if (f.isDirectory()){
//
//            }
//        });

//    replaceFile(new File("D:\\TransactionContext.java"));
    }



    private void replaceFile(File file) throws IOException {
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        bufIn.lines().skip(8).forEach(line -> {
            // 将该行写入内存
            try {
                tempStream.write(line.concat(System.getProperty("line.separator")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 添加换行符
//            tempStream.append(System.getProperty("line.separator"));
        });

        // 关闭 输入流
        bufIn.close();

        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }

}
