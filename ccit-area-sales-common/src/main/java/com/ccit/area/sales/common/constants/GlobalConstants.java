package com.ccit.area.sales.common.constants;

/**
 * 
 * 描述 : 全局常量类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:02:32
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.constants
 * 类名 : GlobalConstants
 */
public interface GlobalConstants {

	/**
	 * 超级管理员【对应t_system_user->user_name字段值】
	 */
	String SUPER_ADMIN = "sysadmin";
	
	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWRD = "Z@m1x#s2";
	
	/**
	 * 逻辑删除【未删除】
	 */
	Integer DELETE_NO = 0;
	
	/**
	 * 逻辑删除【已删除】
	 */
	Integer DELETE_YES = 1;
	
	/**
	 * AES参数私钥
	 */
	String AES_PARAM_KEY = "91e5d559a4b64dca8930802b5636b7f2";
	
}
