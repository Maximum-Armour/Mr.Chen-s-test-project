package com.ccit.area.sales.service.system;

import com.ccit.area.sales.dao.dto.system.SystemLoginDTO;
import com.ccit.area.sales.dao.vo.system.SystemLoginVO;

/**
 * 
 * 描述 : “登录”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午11:02:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemLoginService
 */
public interface ISystemLoginService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月4日 上午11:15:32
	 * 描述 : 登录
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : login
	 *  SystemLoginVO
	 *  @throws
	 */
	SystemLoginVO login(SystemLoginDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:22:32
	 * 描述 : 注销
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : logout
	 *  void  
	 *  @throws
	 */
	void logout();

}
