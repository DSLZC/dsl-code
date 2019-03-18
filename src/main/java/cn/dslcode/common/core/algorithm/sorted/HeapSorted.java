package cn.dslcode.common.core.algorithm.sorted;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author dongsilin
 * @version 2019/1/2.
 * 堆排序
 */
public class HeapSorted {


    /**
     * 堆排序
     * @param list
     * @param comparator
     * @param <T>
     */
    public static<T> void  sorted(T[] list, Comparator<T> comparator) {
        if (list == null) return;

        int maxIdx = list.length - 1;
        if (maxIdx <= 1) return;

        while (maxIdx > 0) {
            // 最大父节点下标
            int maxParentNodeIdx = (maxIdx - 1) / 2;
            // 堆调整
            for (int parentNodeIdx = maxParentNodeIdx; parentNodeIdx >=0; parentNodeIdx--) {
                compareAndAdjust(list, maxIdx, maxParentNodeIdx, parentNodeIdx, comparator);
            }
            // 交换根节点，下次不参与排序
            exchange(list, 0, maxIdx);
            maxIdx--;
        }

    }

    /**
     * 堆排序
     * @param list
     * @param comparator
     * @param <T>
     */
    public static<T> void  sorted(List<T> list, Comparator<T> comparator) {
        if (list == null) return;

        int maxIdx = list.size() - 1;
        if (maxIdx <= 1) return;

        while (maxIdx > 0) {
            // 最大父节点下标
            int maxParentNodeIdx = (maxIdx - 1) / 2;
            // 堆调整
            for (int parentNodeIdx = maxParentNodeIdx; parentNodeIdx >=0; parentNodeIdx--) {
                compareAndAdjust(list, maxIdx, maxParentNodeIdx, parentNodeIdx, comparator);
            }
            // 交换根节点，下次不参与排序
            exchange(list, 0, maxIdx);
            maxIdx--;
        }
    }

    /**
     * 节点比较并向下逐次调整
     * @param list
     * @param maxIdx
     * @param maxParentNodeIdx
     * @param parentNodeIdx
     * @param comparator
     * @param <T>
     */
    private static<T> void compareAndAdjust(T[] list, int maxIdx, int maxParentNodeIdx, int parentNodeIdx, Comparator<T> comparator) {
        int tmpIdx = parentNodeIdx*2+1;
        if (tmpIdx+1 <= maxIdx && comparator.compare(list[tmpIdx+1], list[tmpIdx]) == 1) {
            ++tmpIdx;
        }

        if (comparator.compare(list[tmpIdx], list[parentNodeIdx]) == 1) {
            exchange(list, tmpIdx, parentNodeIdx);
            if (tmpIdx <= maxParentNodeIdx) {
                compareAndAdjust(list, maxIdx, maxParentNodeIdx, tmpIdx, comparator);
            }
        }
    }

    /**
     * 节点比较并向下逐次调整
     * @param list
     * @param maxIdx
     * @param maxParentNodeIdx
     * @param parentNodeIdx
     * @param comparator
     * @param <T>
     */
    private static<T> void compareAndAdjust(List<T> list, int maxIdx, int maxParentNodeIdx, int parentNodeIdx, Comparator<T> comparator) {
        int tmpIdx = parentNodeIdx*2+1;
        if (tmpIdx+1 <= maxIdx && comparator.compare(list.get(tmpIdx+1), list.get(tmpIdx)) == 1) {
            ++tmpIdx;
        }

        if (comparator.compare(list.get(tmpIdx), list.get(parentNodeIdx)) == 1) {
            exchange(list, tmpIdx, parentNodeIdx);
            if (tmpIdx <= maxParentNodeIdx) {
                compareAndAdjust(list, maxIdx, maxParentNodeIdx, tmpIdx, comparator);
            }
        }
    }

    /**
     * 交换
     * @param list
     * @param idx1
     * @param idx2
     */
    private static<T> void exchange(T[] list, int idx1, int idx2) {
        T max = list[idx1];
        list[idx1] = list[idx2];
        list[idx2] = max;
    }

    /**
     * 交换
     * @param list
     * @param idx1
     * @param idx2
     */
    private static<T> void exchange(List<T> list, int idx1, int idx2) {
        T max = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, max);
    }


    public static void main(String[] args) {
        IntStream.rangeClosed(0, 10).forEach(j -> {
            Random random = new Random();
            // 初始化一个序列
            Integer[] array1 = new Integer[100];
            List<Integer> array2 = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                int k = random.nextInt(500);
                array1[i] = k;
                array2.add(k);
            }

            sorted(array1, (c1, c2) -> Integer.compare(c1, c2));
            sorted(array2, (c1, c2) -> Integer.compare(c1, c2));
            System.out.println("排序后:\t"+ ToStringBuilder.reflectionToString(array1));
        });

    }
}
