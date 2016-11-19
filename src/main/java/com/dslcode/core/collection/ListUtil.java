package com.dslcode.core.collection;

import com.dslcode.core.string.StringUtil;
import com.dslcode.core.util.NullUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/4.
 * List集合工具类
 */
public class ListUtil<T> {

    /**
     * 根据参数，获取List
     * @param datas
     * @param <T>
     * @return
     */
    public static<T> List<T> getList(T... datas){
        List<T> list = new ArrayList<T>(5);
        if(NullUtil.isNull(datas)) return list;
        for(T data : datas){
            list.add(data);
        }
        return list;
    }

    /**
     * 模拟js join
     * @param c
     * @param split
     * @return
     */
    public static String join(Collection c, String split){
        return StringUtil.join(c, split);
    }

    /**
     * 模拟js join
     * @param objects
     * @param split
     * @return
     */
    public static String join(Object[] objects, String split){
        return StringUtil.join(objects, split);
    }

}
