package com.ccit.area.sales.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.system.SystemRoleMenuPO;
import com.ccit.area.sales.dao.domain.system.SystemRolePO;
import com.ccit.area.sales.dao.dto.system.SystemRoleAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemRolePermissionDTO;
import com.ccit.area.sales.dao.mapper.system.SystemRoleMapper;
import com.ccit.area.sales.dao.vo.system.SystemRoleDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemRolePageListVO;
import com.ccit.area.sales.service.system.ISystemRoleMenuService;
import com.ccit.area.sales.service.system.ISystemRoleService;

/**
 * 
 * 描述 : “角色”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:45:26
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemRoleServiceImpl
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRolePO> implements ISystemRoleService {
	
	/**
	 * “角色菜单中间”服务类
	 */
	@Autowired
	private ISystemRoleMenuService systemRoleMenuService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:18:26
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectPageList
	 *  Page<SystemRolePageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SystemRolePageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SystemRolePageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		pages.setRecords(this.baseMapper.selectPageList(pages, param));
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:20:04
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemRoleAddDTO entity) {
		LambdaQueryWrapper<SystemRolePO> wrapper = SystemRolePO.wrapper();
		wrapper.eq(SystemRolePO::getRoleName, entity.getRoleName());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("角色名称已存在，请进行检查！");
		}
		SystemRolePO systemRole = new SystemRolePO();
		BeanUtils.copyProperties(entity, systemRole);
		int insertFlag = this.baseMapper.insert(systemRole);
		if (insertFlag > 0) {
			return "新增成功！";
		}
		throw new BusinessException("新增失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:23:26
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : get
	 *  SystemRoleDetailsVO  
	 *  @throws
	 */
	@Override
	public SystemRoleDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("角色ID不能为空，请进行检查！");
		}
		SystemRolePO systemRole = this.baseMapper.selectById(id);
		if (null == systemRole) {
			throw new BusinessException("获取角色失败，请刷新浏览器重新操作！");
		}
		SystemRoleDetailsVO result = new SystemRoleDetailsVO();
		BeanUtils.copyProperties(systemRole, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:24:28
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SystemRoleEditDTO entity) {
		LambdaQueryWrapper<SystemRolePO> wrapper = SystemRolePO.wrapper();
		wrapper.eq(SystemRolePO::getRoleName, entity.getRoleName());
		wrapper.ne(SystemRolePO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("角色名称已存在，请进行检查！");
		}
		SystemRolePO systemRole = new SystemRolePO();
		BeanUtils.copyProperties(entity, systemRole);
		int updateByIdFlag = this.baseMapper.updateById(systemRole);
		if (updateByIdFlag > 0) {
			return "修改成功！";
		}
		throw new BusinessException("修改失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:25:25
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SystemRoleDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("角色ID不能为空，请进行检查！");
		}
		LambdaQueryWrapper<SystemRolePO> wrapper = SystemRolePO.wrapper();
		wrapper.in(SystemRolePO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new SystemRolePO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功！";
		}
		throw new BusinessException("删除失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:54:53
	 * 描述 : 获取权限
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getPermission
	 *  List<Long>  
	 *  @throws
	 */
	@Override
	public List<Long> getPermission(Long id) {
		List<Long> result = new ArrayList<>();
		LambdaQueryWrapper<SystemRoleMenuPO> rolemenuWrapper = new LambdaQueryWrapper<>();
		rolemenuWrapper.eq(SystemRoleMenuPO::getRoleId, id);
		List<SystemRoleMenuPO> systemRoleMenuList = systemRoleMenuService.list(rolemenuWrapper);
		if (!CollectionUtils.isEmpty(systemRoleMenuList)) {
			result.addAll(systemRoleMenuList.stream().filter(v -> v.getMenuId() != null).map(v -> v.getMenuId())
					.collect(Collectors.toList()));
		}
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:54:55
	 * 描述 : 保存权限
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : savePermission
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String savePermission(SystemRolePermissionDTO entity) {
		LambdaQueryWrapper<SystemRoleMenuPO> rolemenuWrapper = new LambdaQueryWrapper<>();
		rolemenuWrapper.eq(SystemRoleMenuPO::getRoleId, entity.getId());
		systemRoleMenuService.remove(rolemenuWrapper);
		List<SystemRoleMenuPO> entityList = new ArrayList<>();
		entity.getMenuIds().forEach(menuId -> {
			entityList.add(new SystemRoleMenuPO(entity.getId(), menuId));
		});
		systemRoleMenuService.saveBatch(entityList);
		return "保存权限！";
	}

}
