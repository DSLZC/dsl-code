package com.dslcode.core.string;

import com.dslcode.core.util.NullUtil;

import java.util.Collection;

/**
 * StringUtil
 * @author 董思林
 * @date 2016年11月08日 15:55:30
 */
public class StringUtil {

	/**
	 * 将第一个字母转为大写
	 * @param str
	 * @return
	 */
	public static String toUpperFirstCase(String str) {
		if(NullUtil.isNull(str)) return "";
		char[] cbuf = str.toCharArray();
		cbuf[0] = Character.toUpperCase(cbuf[0]);
		return String.valueOf(cbuf);
	}
	
	
	/**
	* 模拟js join
	* @param c 
	* @param split
	* @return 
	*/
	public static String join(Collection c, String split){
		if(NullUtil.isNull(c)) return "";
		StringBuffer sb = new StringBuffer();
		c.forEach(o -> sb.append(o).append(split));
		return sb.deleteCharAt(sb.length() - split.length()).toString();
	}
	
	/**
	* 模拟js join
	* @param objects 
	* @param split
	* @return 
	*/
	public static String join(Object[] objects, String split){
		if(NullUtil.isNull(objects)) return "";
		StringBuffer sb = new StringBuffer();
		for (Object o : objects) sb.append(o).append(split);
		return sb.deleteCharAt(sb.length() - split.length()).toString();
	}
	
	/**
	* 字符串拼接连续添加
	* @param objects
	* @return 
	*/
	public static String append(Object...objects){
		StringBuffer sb = new StringBuffer();
		append(sb, objects);
		return sb.toString();
	}

	/**
	* 字符串拼接连续添加
	* @param sb
	* @param objects
	* @return 
	*/
	public static String append(StringBuffer sb, Object...objects){
		for (Object o : objects) sb.append(o);
		return sb.toString();
	}

}
