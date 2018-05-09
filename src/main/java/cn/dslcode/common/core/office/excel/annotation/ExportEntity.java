package cn.dslcode.common.core.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出实体
 * @author dongsilin
 * @version 2018/4/27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportEntity {

    /**
     * 头部标题
     */
    ExcelHeader header();


    /**
     * 表头
     * @return
     */
    CellStyle$ head();


    /**
     * 内容
     * @return
     */
    ExcelBody body();

}
