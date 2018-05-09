package cn.dslcode.common.core.office.excel.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author dongsilin
 * @version 2018/4/28.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBody {

    /**
     * 是否显示序号
     * @return
     */
    boolean showIndex() default true;

    /**
     * 序号名称
     * @return
     */
    String indexName() default "序号";

    CellStyle$ indexStyle();

}
