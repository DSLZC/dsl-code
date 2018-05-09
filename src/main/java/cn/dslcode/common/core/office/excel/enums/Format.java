package cn.dslcode.common.core.office.excel.enums;

/**
 * 导出格式
 */
public enum Format {
    _2003(".xls"),
    _2007(".xlsx");

    public String suffix;

    Format(String suffix) {
        this.suffix = suffix;
    }
}