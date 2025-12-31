package com.ccit.area.sales.service.system;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemMenuPO;
import com.ccit.area.sales.dao.dto.system.SystemMenuAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuMoveDTO;
import com.ccit.area.sales.dao.vo.system.SystemMenuDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemMenuTreeVO;
 
/**
 * 
 * 描述 : “菜单”服务类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:34:03
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : SystemMenuService
 */
public interface ISystemMenuService extends IService<SystemMenuPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午9:29:47
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectTreeList
	 *  List<SystemMenuTreeVO>  
	 *  @throws
	 */
	List<SystemMenuTreeVO> selectTreeList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:45:02
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemMenuAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:45:13
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  SystemMenuDetailsVO  
	 *  @throws
	 */
	SystemMenuDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:45:21
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SystemMenuEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:45:35
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SystemMenuDeleteDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:45:43
	 * 描述 : 移动位置
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : move
	 *  String  
	 *  @throws
	 */
	String move(SystemMenuMoveDTO entity);

}
