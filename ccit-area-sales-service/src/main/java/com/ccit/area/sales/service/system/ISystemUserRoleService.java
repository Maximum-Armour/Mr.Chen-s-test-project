package com.ccit.area.sales.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemUserRolePO;
 
/**
 * 
 * 描述 : “用户角色中间”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:55:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemUserRoleService
 */
public interface ISystemUserRoleService extends IService<SystemUserRolePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午9:55:34
	 * 描述 : 获取用户菜单数组ID
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getUserMenuIds
	 *  List<Long>  
	 *  @throws
	 */
	List<Long> getUserMenuIds(String userName);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:41:25
	 * 描述 : 获取用户菜单权限数组
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getUserMenuPurview
	 *  List<String>  
	 *  @throws
	 */
	List<String> getUserMenuPurview(String userName);
	
}
