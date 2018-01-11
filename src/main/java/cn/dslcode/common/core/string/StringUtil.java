package cn.dslcode.common.core.string;

import cn.dslcode.common.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil
 * @author 董思林
 *  2016年11月08日 15:55:30
 */
public class StringUtil extends StringUtils {

	/**
	 * 判断是否全部为空或empty
	 * @param ss
	 * @return
	 */
	public static boolean isEmptyAll(String... ss){
		for(String s : ss) if(!isEmpty(s)) return false;
		return true;
	}

	/**
	 * 判断是否全部不为空或empty
	 * @param ss
	 * @return
	 */
	public static boolean isNotEmptyAll(String... ss){
		for(String s : ss) if(isEmpty(s)) return false;
		return true;
	}

	/**
	 * 将第一个字母转为大写
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str) {
		if(isEmpty(str)) return "";
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
	public static String join(Collection<? extends CharSequence> c, CharSequence split){
		if(CollectionUtil.isEmpty(c)) return "";
		StringBuffer sb = new StringBuffer();
		c.forEach(o -> sb.append(o).append(split));
		return sb.deleteCharAt(sb.length() - split.length()).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param sb 已存在的StringBuffer对象，可以为null
	 * @param objects 拼接对象
	 * @return 拼接过后的字符串
	 */
	public static String append2String(StringBuffer sb, Object...objects){
		return append(sb, objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的字符串
	 */
	public static String append2String(Object...objects){
		return append(objects).toString();
	}

	/**
	 * 字符串拼接连续添加
	 * @param sb 已存在的StringBuffer对象，可以为null
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuffer append(StringBuffer sb, Object...objects) {
		if(null == sb) sb = new StringBuffer();
		for (Object o : objects) sb = sb.append(null == o? "" : o);
		return sb;
	}

	/**
	 * 字符串拼接连续添加
	 * @param objects 拼接对象
	 * @return 拼接过后的StringBuffer对象
	 */
	public static StringBuffer append(Object...objects) {
		return append(null, objects);
	}

	/**
	 * 将用户名除第一个中文字符的其他字符替换为*
	 * @param origin
	 * @return
	 */
	public static String replaceUserName(String origin) {
		if(isEmpty(origin)) return "";
		return origin.replaceAll("([\\u4e00-\\u9fa5_a-zA-Z0-9]{1})[\\u4e00-\\u9fa5_a-zA-Z0-9]*", "$1**");
	}

	/**
	 * 将手机号码的中间四位替换为*
	 * 13112324567 -->131****4567
	 * @param origin
	 * @return
	 */
	public static String replacePhone(String origin) {
		if(isEmpty(origin)) return "";
		if (origin.length() < 11) return origin;
		return origin.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * Not equals
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean ne(String s1, String s2) {
		return !equals(s1, s2);
	}

	/**
	 * equals
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean eq(String s1, String s2) {
		return equals(s1, s2);
	}

	/**
	 * 是否有长度，长度是否大于0
	 * @param str
	 * @return
	 */
	public static boolean hasLength(CharSequence str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 是否有有效字符
	 * @param str
	 * @return
	 */
	public static boolean hasText(CharSequence str) {
		if(!hasLength(str)) {
			int strLen = str.length();
			for(int i = 0; i < strLen; ++i) {
				if(!Character.isWhitespace(str.charAt(i))) return true;
			}
			return false;
		} else return false;
	}

	/**
	 * 是否匹配正则表达式
	 * @param source 匹配字符串
	 * @param regex 正则表达式
	 * @return
	 */
	public static boolean isMatch(String source, String regex) {
		if (isEmpty(source)) return false;
		return Pattern.compile(regex).matcher(source).find();
	}

	/**
	 * 找出正则表达式匹配的结果
	 * @param source 匹配字符串
	 * @param regex 正则表达式
	 * @param pos 匹配位置 1开始
	 * @return
	 */
	public static String getMatchResult(String source, String regex, int pos) {
		if (isEmpty(source)) return "";
		Matcher matcher = Pattern.compile(regex).matcher(source);
		if (matcher.find(pos)) return matcher.group(pos);
		return "";
	}

}
