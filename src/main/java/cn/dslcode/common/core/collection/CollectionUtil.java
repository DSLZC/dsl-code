package cn.dslcode.common.core.collection;

import cn.dslcode.common.core.array.ArrayUtil;
import cn.dslcode.common.core.string.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/4.
 * List集合工具类
 * @author 董思林
 *  2016-11-15 10:20:25
 *
 */
public class CollectionUtil<T, U> extends CollectionUtils {


    /**
     * 创建 ArrayList
     * @param initialCapacity
     * @param <T>
     * @return ArrayList
     */
    public static<T> List<T> newArrayList(int initialCapacity){
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 创建 ArrayList
     * @param <T>
     * @return ArrayList
     */
    public static<T> List<T> newArrayList(){
        return new ArrayList<>();
    }

    /**
     * 数组转List
     * @param datas 数组
     * @param <T> 泛型
     * @return List
     */
    public static<T> List<T> getList(T... datas){
        if(ArrayUtil.isEmpty(datas)) return new ArrayList<>(0);
        List<T> list = new ArrayList<>(datas.length);
        for(T data : datas){
            list.add(data);
        }
        return list;
    }

    /**
     * 数组转List
     * @param ts 数组
     * @param <T> 泛型
     * @return List
     */
    public static<T> List<T> asList(T[] ts){
        return Arrays.asList(ts);
    }

    /**
     * 移除null元素
     * @param list 源List
     * @param <T> 泛型
     * @return List
     */
    public static<T> List<T> removeNull(List<T> list){
        List<T> results = new ArrayList<T>(list.size());
        list.forEach(l -> {if(null != l) results.add(l);});
        return results;
    }

    /**
     * 模拟js join
     * @param c 源集合
     * @param split 分隔符
     * @return String
     */
    public static String join(Collection<? extends CharSequence> c, CharSequence split){
        return StringUtil.join(c, split);
    }
}
