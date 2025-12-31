package com.ccit.area.sales.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * 描述 : 密码工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午3:37:04
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : PasswordUtil
 */
public class PasswordUtil {

	/**
	 * BCryptPasswordEncoder方法采用SHA-256+随机盐+密钥对密码进行加密
	 * 	1、密码加密(encode)
	 * 	2、密码匹配(matches)
	 */
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:37:11
	 * 描述 : 密码加密
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getEncodePwd
	 *  String  
	 *  @throws
	 */
	public static String getEncodePwd(String pwd) {
		return encoder.encode(pwd);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:37:15
	 * 描述 : 密码匹配
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getMatchesPwd
	 *  Boolean  
	 *  @throws
	 */
	public static Boolean getMatchesPwd(String pwd, String encodePwd) {
		return encoder.matches(pwd, encodePwd);
	}
	
	public static void main(String[] args) {
		System.out.println(getEncodePwd("123456"));
	}
	
}
