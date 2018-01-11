package cn.dslcode.common.core.array;

import cn.dslcode.common.core.string.StringUtil;
import cn.dslcode.common.core.util.NullUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by dongsilin on 2016/11/21.
 * 数组操作util
 * @author 董思林
 *  2016-11-21 10:20:25
 */
public class ArrayUtil<T> extends ArrayUtils {

    /**
     * List转换为Array
     * @param list 源List
     * @param arrayClass target class
     * @param <T> 泛型
     * @return T[]
     */
    public static<T> T[] toArray(List<T> list, Class<T> arrayClass){
        if(NullUtil.isNotNullAll(list, arrayClass)){
            T[] ts = (T[]) Array.newInstance(arrayClass, list.size());
            for(int i=0;i<list.size();i++) ts[i] = list.get(i);
            return ts;
        }
        return null;
    }

    /**
     * 模拟js join
     * @param objects 源数组
     * @param split 分隔符
     * @return String
     */
    public static String join(Object[] objects, String split){
        return StringUtil.join(objects, split);
    }

    /**
     * 判断是否不为空
     * @param param 数组
     * @param <T> 泛型
     * @return boolean
     */
    public static<T> boolean isNotEmpty(T... param) {
        return !isEmpty(param);
    }

}
