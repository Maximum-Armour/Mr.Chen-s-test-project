package com.ccit.area.sales.service.common;

import com.ccit.area.sales.dao.dto.common.VerifyCodeVO;

/**
 * 
 * 描述 : “基础”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 上午10:08:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.common
 * 类名 : ICommonService
 */
public interface ICommonService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午10:09:49
	 * 描述 : 根据固定值、长度生成随机验证码
	 * 包名 : com.ccit.area.sales.service.common
	 * 方法名 : generateVerifyCode
	 *  VerifyCodeVO  
	 *  @throws
	 */
	VerifyCodeVO generateVerifyCode(String key, Integer size);
	
}
