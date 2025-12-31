package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemSequencePO;

/**
 * 	
 * 描述 : “序列号”服务类
 * 创建人 : yn
 * 创建时间 : 2024年08月22日 上午10:05:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemSequenceService
 */
public interface ISystemSequenceService extends IService<SystemSequencePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月22日 上午10:30:14
	 * 描述 : 获取序列号
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  String  
	 *  @throws
	 */
	String get(String code, Integer number);

}
