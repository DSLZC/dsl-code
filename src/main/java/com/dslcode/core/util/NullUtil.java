package com.dslcode.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 判断非null Util
 * @author DSL 
 * @date 2016-07-13
 */
public class NullUtil {

	/**
	 * 判断对象是否为空或String是否为empty
	 * @param param
	 * @return
	 */
	public static boolean isNull(Object param) {
		if (param instanceof String) {	// 字符串
			return null == param || ((String) param).length() == 0;
		} else if (param instanceof Collection) {	// 集合
			Collection obj = (Collection) param;
			return null == obj || obj.size() == 0;
		} else if (param instanceof Map) {	// Map
			Map obj = (Map) param;
			return null == obj || obj.isEmpty();
		} else if (param.getClass().isArray()) {	// 数组
			Object[] obj = (Object[]) param;
			return null == obj || obj.length == 0;
		}else {	// 其他
			return null == param;
		}
	}


	/**
	 * 判断对象是否不为空或String是否不为empty
	 * @param param
	 * @return
	 */
	public static boolean isNotNull(Object param) {
		return !isNull(param);
	}


}
