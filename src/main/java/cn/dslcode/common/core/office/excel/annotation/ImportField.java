package cn.dslcode.common.core.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入字段注解定义
 * 
 * @author ThinkGem
 * @version 2013-03-10
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportField {

    /**
     * 导出字段标题
     */
    String title();

    /**
     * 显示类型，如日期格式，数字格式等（DateFormat, NumberFormat用）
     */
    String pattern() default "";

}
