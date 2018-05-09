package cn.dslcode.common.core.office.excel.annotation;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongsilin
 * @version 2018/4/28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FontStyle {

    IndexedColors color() default IndexedColors.BLACK;
    String family() default "Arial";

    /**
     * Font.BOLDWEIGHT_*
     * @return
     */
    short weight() default Font.BOLDWEIGHT_NORMAL;
    short size() default 10;

}
