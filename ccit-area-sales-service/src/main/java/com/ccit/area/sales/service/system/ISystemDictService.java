package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemDictPO;
import com.ccit.area.sales.dao.dto.system.SystemDictAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemDictEditDTO;
import com.ccit.area.sales.dao.vo.system.SystemDictChildrenVO;
import com.ccit.area.sales.dao.vo.system.SystemDictDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemDictListVO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;

import java.util.List;
import java.util.Map;
 
/**
 * 
 * 描述 : “字典”服务类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:33:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : SystemDictService
 */
public interface ISystemDictService extends IService<SystemDictPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:32:49
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectTreeList
	 *  List<SystemDictTreeVO>  
	 *  @throws
	 */
	List<SystemDictTreeVO> selectTreeList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午11:13:37
	 * 描述 : 列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectList
	 *  List<SystemDictListVO>  
	 *  @throws
	 */
	List<SystemDictListVO> selectList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:33:26
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemDictAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:33:31
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  SystemDictDetailsVO  
	 *  @throws
	 */
	SystemDictDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:33:35
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SystemDictEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:33:40
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:33:44
	 * 描述 : 根据字典编码获取子节点数组
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : getChildrenList
	 *  List<SystemDictChildrenVO>  
	 *  @throws
	 */
	List<SystemDictChildrenVO> getChildrenList(String dictCode);

}
