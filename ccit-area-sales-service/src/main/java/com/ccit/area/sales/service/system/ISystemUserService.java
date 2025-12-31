package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.dto.system.*;
import com.ccit.area.sales.dao.vo.system.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “用户”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午10:34:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemUserService
 */
public interface ISystemUserService extends IService<SystemUserPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午1:50:22
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectPageList
	 *  Page<SystemUserPageListVO>  
	 *  @throws
	 */
	Page<SystemUserPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午1:50:31
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemUserAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午1:50:36
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  SystemUserDetailsVO  
	 *  @throws
	 */
	SystemUserDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午1:50:40
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SystemUserEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午1:50:45
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SystemUserDeleteDTO entity);

	/**
	 * 根据用户名查询用户详情
	 */
	SystemUserDetailVO selectUserDetails(String userName);

	List<SystemUserDetailVO> selectUserDetailsList(String userName);



	String updateUserOAInformation(Long id);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年11月1日 下午3:13:26
	 * 描述 : 用户解锁
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : unlock
	 *  String  
	 *  @throws
	 */
	String unlock(SystemUserUnlockDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年11月1日 下午3:13:34
	 * 描述 : 重置密码
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : resetPwd
	 *  String  
	 *  @throws
	 */
	String resetPwd(SystemUserResetPwdDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午1:50:45
	 * 描述 : 组织
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectOrgName
	 *  List<SystemUserDetailVO>
	 *  @throws
	 */
	List<SystemOrgVO> selectOrgName(String userName);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午1:50:45
	 * 描述 : 组织更新
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : updateOrgName
	 *  String
	 *  @throws
	 */
	String updateOrgName(String orgId, String userId);



	SystemUserDetailsVO	getUserName(SystemUserPageListDTO entity);
}
