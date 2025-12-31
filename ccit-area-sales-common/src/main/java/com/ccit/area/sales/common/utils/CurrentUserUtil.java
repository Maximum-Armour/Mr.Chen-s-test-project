package com.ccit.area.sales.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.constants.RedisConstants;
import com.ccit.area.sales.common.jwt.JwtUtils;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.common.utils.AESUtil;
import com.ccit.common.utils.StringUtil;

/**
 * 
 * 描述 : 当前登录用户工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午2:28:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : CurrentUserUtil
 */
public class CurrentUserUtil {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:29:27
	 * 描述 : 获取当前登录用户信息
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getCurrentUser
	 *  JSONObject  
	 *  @throws
	 */
	public static JSONObject getCurrentUser() {
		JwtUtils jwtUtils = SpringUtil.getBean(JwtUtils.class);
		RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
		if (StringUtil.isNoneBlank(jwtUtils.getToken())) {
			String key = RedisConstants.USER + jwtUtils.getTokenBySubject(false);
			String resultUserJson = (String) redisUtils.get(key);
			if (StringUtil.isNoneBlank(resultUserJson)) {
				resultUserJson = AESUtil.decrypt(resultUserJson, GlobalConstants.AES_PARAM_KEY);
				return JSONObject.parseObject(resultUserJson);
			}
		}
		return new JSONObject();
	}
	
}
