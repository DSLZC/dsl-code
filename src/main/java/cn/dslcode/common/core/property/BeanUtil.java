package cn.dslcode.common.core.property;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dongsilin
 * @version 2018/3/21.
 */
@Slf4j
public class BeanUtil<S, T> {

    private static final Mapper mapper = new DozerBeanMapper();

    /**
     * Dozer 拷贝属性，性能非常好
     *
     * @param source
     * @param targetClass
     * @return
     */
    public static <S, T> T mapper(S source, Class<T> targetClass) {
        if (null == source) return null;
        return mapper.map(source, targetClass);
    }

    /**
     * Dozer 拷贝属性，性能非常好
     *
     * @param source
     * @param t
     * @return
     */
    public static <S, T> T mapper(S source, T t) {
        if (null == source) return null;
        mapper.map(source, t);
        return t;
    }

    /**
     * Dozer 拷贝集合属性，性能非常好
     *
     * @param sources
     * @param targetClass
     * @return
     */
    public static <S, T> List<T> mapper2List(Collection<S> sources, Class<T> targetClass) {
        if (null == sources) return null;
        if (sources.size() == 0) return new ArrayList<T>(0);
        return sources.stream().map(s -> mapper(s, targetClass)).collect(Collectors.toList());
    }

    /**
     * Dozer 拷贝集合属性，性能非常好
     *
     * @param sources
     * @param targetClass
     * @return
     */
    public static <S, T> Set<T> mapper2Set(Collection<S> sources, Class<T> targetClass) {
        if (null == sources) return null;
        if (sources.size() == 0) return new HashSet<T>(0);
        return sources.parallelStream().map(s -> mapper(s, targetClass)).collect(Collectors.toSet());
    }
}
