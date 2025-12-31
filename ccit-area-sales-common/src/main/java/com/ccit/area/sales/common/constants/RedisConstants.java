package com.ccit.area.sales.common.constants;

/**
 * 
 * 描述 : 缓存常量类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午2:41:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.constants
 * 类名 : RedisConstants
 */
public interface RedisConstants {

	/**
	 * 验证码私钥
	 */
	String VERIFY_CODE_KEY = "verify_code_key:";
	
	/**
	 * 验证码有效期【分钟】
	 */
	String VERIFY_CODE_EXPIRATION = "5";
	
	/**
	 * 用户
	 */
	String USER = "user:";
	
	/**
	 * 用户令牌有效期【分钟】
	 */
	String USER_EXPIRATION = "60";
	
	/**
	 * 用户令牌
	 */
	String USER_TOKEN = "user_token:";
	
}
