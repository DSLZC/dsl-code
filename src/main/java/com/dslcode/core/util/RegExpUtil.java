package com.dslcode.core.util;

/**
 * Created by dongsilin on 2016/11/2.
 * 正则表达式验证Util
 */
public class RegExpUtil {
    /**
     * 正则表达式验证
     * @param str 目标字符串
     * @param required 是否需要验证
     * @param reg 正则表达式
     * @return
     */
    public static boolean isMatch(String str, String reg, boolean required) {
        if(NullUtil.isNull(reg)) return false;
        if(required && NullUtil.isNull(str)) return false;
        //必须验证
        if(required || NullUtil.isNotNull(str)){
            return str.matches(reg);
        }
        return false;
    }
}
