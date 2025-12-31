package com.ccit.area.sales.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemRolePO;
import com.ccit.area.sales.dao.dto.system.SystemRoleAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemRolePermissionDTO;
import com.ccit.area.sales.dao.vo.system.SystemRoleDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemRolePageListVO;
 
/**
 * 
 * 描述 : “角色”服务类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:34:55
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : SystemRoleService
 */
public interface ISystemRoleService extends IService<SystemRolePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:17:30
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectPageList
	 *  Page<SystemRolePageListVO>  
	 *  @throws
	 */
	Page<SystemRolePageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:17:37
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemRoleAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:17:41
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  SystemRoleDetailsVO  
	 *  @throws
	 */
	SystemRoleDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:17:45
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SystemRoleEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:17:49
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SystemRoleDeleteDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:51:36
	 * 描述 : 获取权限
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getPermission
	 *  List<Long>  
	 *  @throws
	 */
	List<Long> getPermission(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:51:52
	 * 描述 : 保存权限
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : savePermission
	 *  String  
	 *  @throws
	 */
	String savePermission(SystemRolePermissionDTO entity);
	
}
