package cn.dslcode.common.core.office.excel.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongsilin
 * @version 2018/4/28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {

    String title() default "";

    CellStyle$ style();

}
