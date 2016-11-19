package com.dslcode.core.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * date 工具类
 */
public class DateUtil {
    public static final String TIMEZONE = "GMT+8";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMdd = "yyyy-MM-dd";


    /**
     * 现在时间
     * @return
     */
    public static Date now(){
        return DateTime.now().toDate();
    }

    /**
     * 转换时间字符串为Date
     * @param dateStr 时间字符串
     * @param format 格式
     * @return
     */
    public static Date parseToDate(String dateStr, String format){
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
    }

}
