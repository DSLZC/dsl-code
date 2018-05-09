package cn.dslcode.common.core.reflect;

import cn.dslcode.common.core.array.ArrayUtil;
import cn.dslcode.common.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsilin on 2016/12/9.
 * 反射工具类
 */
@Slf4j
public class ReflectUtil<S, T> {
    /**
     * 包装类-基本类
     */
    public static final List<Class[]> BASE_BOXED_AND_PRIMITIVE_CLASSES = new ArrayList(9){
        {
            add(new Class[]{Integer.class, int.class});
            add(new Class[]{Long.class, long.class});
            add(new Class[]{Boolean.class, boolean.class});
            add(new Class[]{Byte.class, byte.class});
            add(new Class[]{Short.class, short.class});
            add(new Class[]{Character.class, char.class});
            add(new Class[]{Float.class, float.class});
            add(new Class[]{Double.class, double.class});
            add(new Class[]{Void.class, void.class});
        }
    };

    /**
     * 数组包装类-基本类
     */
    public static final List<Class[]> BASE_BOXED_AND_PRIMITIVE_ARRAY_CLASSES = new ArrayList(8){
        {
            add(new Class[]{Integer[].class, int[].class});
            add(new Class[]{Long[].class, long[].class});
            add(new Class[]{Boolean[].class, boolean[].class});
            add(new Class[]{Byte[].class, byte[].class});
            add(new Class[]{Short[].class, short[].class});
            add(new Class[]{Character[].class, char[].class});
            add(new Class[]{Float[].class, float[].class});
            add(new Class[]{Double[].class, double[].class});
        }
    };
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";

    /**
     * 根据Class类型将String转化为对应的值
     * @param c
     * @param value
     * @return
     * @throws Exception
     */
    public static<T> Object getValue(Class<T> c, String value) {
        if(Integer.class.isAssignableFrom(c)) return Integer.parseInt(value);
        else if(Long.class.isAssignableFrom(c)) return Long.parseLong(value);
        else if(Double.class.isAssignableFrom(c)) return Double.parseDouble(value);
        else if(Float.class.isAssignableFrom(c)) return Float.parseFloat(value);
        else if(BigDecimal.class.isAssignableFrom(c)) return new BigDecimal(value);
        else throw new RuntimeException("类型"+ c.getName() + "，暂不能解析...");
    }

    /**
     * 调用Getter方法. 支持多级，如：对象名.对象名.对象名
     * @param obj
     * @param propertyName
     * @return
     */
    public static<S, T> T invokeGetter(S obj, String propertyName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object oo = obj;
        if (StringUtil.contains(propertyName, ".")) {
            for (String name : StringUtil.split(propertyName, ".")) {
                oo = invokeMethod(oo, GETTER_PREFIX + StringUtil.toUpperCaseFirst(name), null, null, null);
            }
        } else {
            oo = invokeMethod(obj, GETTER_PREFIX + StringUtil.toUpperCaseFirst(propertyName), null, null, null);
        }
        return (T) oo;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。 支持多级，如：对象名.对象名.对象名
     * @param obj
     * @param propertyName
     * @param value
     * @param isBasicType
     */
    public static<S, T> void invokeSetter(S obj, String propertyName, T value, boolean isBasicType) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object oo = obj;
        if (StringUtil.contains(propertyName, ".")) {
            int lastIndex = StringUtil.lastIndexOf(propertyName, ".");
            oo = invokeGetter(oo, StringUtil.substring(propertyName, 0, lastIndex));
            propertyName = StringUtil.substring(propertyName, lastIndex + 1);
        }
        invokeMethod(oo, SETTER_PREFIX + StringUtil.toUpperCaseFirst(propertyName), new Class[]{value.getClass()}, new Object[] {value}, isBasicType? new int[]{0} : null);
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数
     * @param obj
     * @param fieldName
     * @param <S>
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     */
    public static<S, T> T getFieldValue(S obj, String fieldName) throws NoSuchFieldException {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }
        try {
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("{}", e.getMessage());
            return null;
        }
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数
     * @param obj
     * @param fieldName
     * @param value
     * @param <S>
     * @param <T>
     * @throws NoSuchFieldException
     */
    public static<S, T> void setFieldValue(S obj, String fieldName, T value) throws NoSuchFieldException {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("{}", e.getMessage());
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用，同时匹配方法名+参数类型
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param args
     * @param parameterTypeBasicIndexs 基本类型参数类型的位置
     * @param <S>
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public static<S, T> T invokeMethod(S obj, String methodName, Class<?>[] parameterTypes, T[] args, int[] parameterTypeBasicIndexs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes, parameterTypeBasicIndexs);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }
        return (T) method.invoke(obj, args);
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问，如向上转型到Object仍无法找到, 返回null
     * @param obj
     * @param fieldName
     * @param <S>
     * @return
     * @throws NoSuchFieldException
     */
    public static<S> Field getAccessibleField(S obj, String fieldName) throws NoSuchFieldException {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                return makeAccessible(field);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
                continue;
            }
        }
        throw new NoSuchFieldException("Could not find filed [" + fieldName + "] on target [" + obj + "]");
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null. 匹配函数名+参数类型
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param parameterTypeBasicIndexs
     * @param <S>
     * @return
     * @throws NoSuchMethodException
     */
    public static<S> Method getAccessibleMethod(S obj, String methodName, Class<?>[] parameterTypes, int[] parameterTypeBasicIndexs) throws NoSuchMethodException {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        // 基本类型处理
        if (ArrayUtil.isNotEmpty(parameterTypeBasicIndexs)) {
            for (int index : parameterTypeBasicIndexs) {
                parameterTypes[index] = toPrimitiveType(parameterTypes[index]);
            }
        }

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                return makeAccessible(method);
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
                continue;
            }
        }
        throw new NoSuchMethodException("Could not find method [" + methodName + "] on target [" + obj + "]");
    }

    /**
     * 改变private/protected的方法为public
     * @param method
     * @return
     */
    public static Method makeAccessible(Method method) {
        if (
            !method.isAccessible() &&
                (!Modifier.isPublic(method.getModifiers())|| !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
            ) {
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 改变private/protected的成员变量为public
     * @param field
     * @return
     */
    public static Field makeAccessible(Field field) {
        if (
            !field.isAccessible()
                && (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers()))
            ) {
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class
     * @param clazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> Class<T> getGenricsType(Class<S> clazz) {
        return getGenricsType(clazz, 0);
    }

    /**
     * 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class
     * @param clazz
     * @param index
     * @return
     */
    public static<S, T> Class<T> getGenricsType(Class<S> clazz, int index) {

        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return (Class<T>) Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()  + "'s Parameterized Type: " + params.length);
            return (Class<T>) Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return (Class<T>) Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 获取已经存在的注解
     * @param clazz
     * @param annotationClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static<S, T> T getExistsAnnotation(Class<S> clazz, Class annotationClass) {
        T annotation = (T) clazz.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new NullPointerException("Could not find annotation [" + annotationClass.getName() + "] on class [" + clazz.getName() + "]");
        }
        return annotation;
    }

    /**
     * 获取已经存在的注解
     * @param field
     * @param annotationClass
     * @param <T>
     * @return
     */
    public static<T> T getExistsAnnotation(Field field, Class annotationClass) {
        T annotation = (T) field.getAnnotation(annotationClass);
        if (annotation == null) {
            throw new NullPointerException("Could not find annotation [" + annotationClass.getName() + "] on field [" + field.getName() + "]");
        }
        return annotation;
    }

    /**
     * 是否有注解
     * @param field
     * @param annotationClass
     * @return
     */
    public static boolean hasAnnotation(Field field, Class annotationClass) {
        return field.getAnnotation(annotationClass) != null;
    }

    /**
     * 将包装类型转化为基本类型
     * @param c
     * @return
     */
    public static Class toPrimitiveType(Class c) {
        // 基本类型，直接返回
        if (c.isPrimitive()) return c;
        try {
            if (c.isArray()) {
                return BASE_BOXED_AND_PRIMITIVE_ARRAY_CLASSES.stream().filter(cs -> cs[0].equals(c)).findFirst().get()[1];
            }
            return BASE_BOXED_AND_PRIMITIVE_CLASSES.stream().filter(cs -> cs[0].equals(c)).findFirst().get()[1];
        } catch (Exception e) {
            throw new ClassCastException(c.getName() + "cannot cast primitive type");
        }
    }

}
