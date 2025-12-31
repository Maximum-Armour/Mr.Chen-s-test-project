package com.ccit.area.sales.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.dao.domain.system.SystemUserRolePO;
import com.ccit.area.sales.dao.mapper.system.SystemUserRoleMapper;
import com.ccit.area.sales.service.system.ISystemUserRoleService;

/**
 * 
 * 描述 : “用户角色中间”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:45:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemUserRoleServiceImpl
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRolePO> implements ISystemUserRoleService {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午9:56:43
	 * 描述 : 获取用户菜单数组ID
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getUserMenuIds
	 *  List<Long>
	 *  @throws
	 */
	@Override
	public List<Long> getUserMenuIds(String userName) {
		return this.baseMapper.getUserMenuIds(userName);
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:41:59
	 * 描述 : 获取用户菜单权限数组
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getUserMenuPurview
	 *  List<String>  
	 *  @throws
	 */
	@Override
	public List<String> getUserMenuPurview(String userName) {
		return this.baseMapper.getUserMenuPurview(userName);
	}

}
