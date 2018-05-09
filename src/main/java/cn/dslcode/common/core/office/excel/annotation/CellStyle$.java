package cn.dslcode.common.core.office.excel.annotation;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongsilin
 * @version 2018/4/28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CellStyle$ {

    /**
     * 对齐方式，CellStyle.ALIGN_*
     * @return
     */
    short align() default CellStyle.ALIGN_CENTER;
    int width() default 3000;
    short height() default 15;

    /**
     * 背景颜色
     * @return
     */
    IndexedColors bgColor() default IndexedColors.WHITE;

    /**
     * 字体样式
     * @return
     */
    FontStyle fontStyle();

}
