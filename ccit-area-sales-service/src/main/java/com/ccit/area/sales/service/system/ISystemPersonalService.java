package com.ccit.area.sales.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.dto.system.SystemMyInfoDTO;
import com.ccit.area.sales.dao.dto.system.SystemMyPwdDTO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserTokenVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemPersonalMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemSecurityLogPageListVO;

/**
 * 
 * 描述 : “个人中心”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午4:34:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemPersonalService
 */
public interface ISystemPersonalService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:37:24
	 * 描述 : 获取当前登录用户信息
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getCurrentUser
	 *  SystemCurrentUserVO  
	 *  @throws
	 */
	SystemCurrentUserVO getCurrentUser();

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:58:25
	 * 描述 : 获取当前登录用户菜单
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getCurrentUserMenu
	 *  List<SystemCurrentUserMenuVO>  
	 *  @throws
	 */
	List<SystemCurrentUserMenuVO> getCurrentUserMenu();
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月24日 下午4:55:05
	 * 描述 : 根据菜单权限获取菜单详情
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getMenuDetails
	 *  SystemPersonalMenuVO  
	 *  @throws
	 */
	SystemPersonalMenuVO getMenuDetails(String menuPurview);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:58:44
	 * 描述 : 修改个人信息
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : editMyInfo
	 *  String  
	 *  @throws
	 */
	String editMyInfo(SystemMyInfoDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:58:47
	 * 描述 : 修改个人密码
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : editMyPwd
	 *  String  
	 *  @throws
	 */
	String editMyPwd(SystemMyPwdDTO entity);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午4:08:51
	 * 描述 : 安全日志分页列表
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : securityLogPageList
	 *  Page<SystemSecurityLogPageListVO>  
	 *  @throws
	 */
	Page<SystemSecurityLogPageListVO> securityLogPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月21日 下午1:55:55
	 * 描述 : 验证原密码
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : checkOldPwd
	 *  Boolean  
	 *  @throws
	 */
	Boolean checkOldPwd(String oldPwd);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月26日 下午8:50:47
	 * 描述 : 验证令牌是否过期
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : checkToken
	 *  SystemCurrentUserTokenVO  
	 *  @throws
	 */
	SystemCurrentUserTokenVO checkToken();

}
