package cn.dslcode.common.core.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导入实体
 * @author dongsilin
 * @version 2018/4/27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportEntity {

    /**
     * 是否显示序号
     * @return
     */
    boolean showIndex() default true;

}
