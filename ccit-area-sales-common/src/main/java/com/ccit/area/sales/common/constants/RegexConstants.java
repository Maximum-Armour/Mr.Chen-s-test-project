package com.ccit.area.sales.common.constants;

/**
 * 
 * 描述 : 正则表达式常量类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:13:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.constants
 * 类名 : RegexConstants
 */
public interface RegexConstants {

	/**
	 * 密码【密码为8-16位，必须包含大小写字母、数字、特殊字符（~!@&%#_*,.）】
	 */
	String PWD = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@&%#_*,.])[a-zA-Z0-9~!@&%#_*,.]{8,16}$";
	
}
