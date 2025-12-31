package com.ccit.area.sales.service.system;

import java.util.List;

import com.ccit.area.sales.dao.vo.system.SystemCurrentUserMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;
import com.ccit.area.sales.dao.vo.system.SystemMenuTreeVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgTreeVO;

/**
 * 
 * 描述 : “树节点”服务类
 * 创建人 : yn
 * 创建时间 : 2024年6月15日 下午1:47:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : SystemTreeNodeService
 */
public interface ISystemTreeNodeService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午2:06:38
	 * 描述 : 转化用户菜单树结构
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : changeDataUserMenuTree
	 *  List<SystemCurrentUserMenuVO>  
	 *  @throws
	 */
	List<SystemCurrentUserMenuVO> changeDataUserMenuTree(List<SystemCurrentUserMenuVO> list);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午9:34:29
	 * 描述 : 转化菜单树结构
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : changeDataMenuTree
	 *  List<SystemMenuTreeVO>  
	 *  @throws
	 */
	List<SystemMenuTreeVO> changeDataMenuTree(List<SystemMenuTreeVO> list);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:41:22
	 * 描述 : 转化字典树结构
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : changeDataDictTree
	 *  List<SystemDictTreeVO>  
	 *  @throws
	 */
	List<SystemDictTreeVO> changeDataDictTree(List<SystemDictTreeVO> list);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年9月25日 下午3:50:51
	 * 描述 : 转化组织树结构
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : changeDataOrgTree
	 *  List<SystemOrgTreeVO>  
	 *  @throws
	 */
	List<SystemOrgTreeVO> changeDataOrgTree(List<SystemOrgTreeVO> list);
	
}
