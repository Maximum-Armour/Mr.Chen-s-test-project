package com.ccit.area.sales.dao.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.system.SystemUserRolePO;

/**
 * 
 * 描述 : “用户角色中间”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:52:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemUserRoleMapper
 */
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRolePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午9:52:46
	 * 描述 : 获取用户菜单数组ID
	 * 包名 : com.ccit.area.sales.dao.mapper.system
	 * 方法名 : getUserMenuIds
	 *  List<Long>  
	 *  @throws
	 */
	List<Long> getUserMenuIds(@Param(value = "userName") String userName);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:42:18
	 * 描述 : 获取用户菜单权限数组
	 * 包名 : com.ccit.area.sales.dao.mapper.system
	 * 方法名 : getUserMenuPurview
	 *  List<String>  
	 *  @throws
	 */
	List<String> getUserMenuPurview(@Param(value = "userName") String userName);

}
