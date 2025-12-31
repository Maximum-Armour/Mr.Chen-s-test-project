package com.ccit.area.sales.service.archives;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.DepotPO;
import com.ccit.area.sales.dao.dto.archives.DepotAddDTO;
import com.ccit.area.sales.dao.dto.archives.DepotDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DepotEditDTO;
import com.ccit.area.sales.dao.vo.archives.DepotDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DepotPageListVO;
import com.ccit.area.sales.dao.vo.archives.DepotTreeVO;
 
/**
 * 
 * 描述 : “仓库档案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:50:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : DepotService
 */
public interface IDepotService extends IService<DepotPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:02:53
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectTreeList
	 *  List<DepotTreeVO>  
	 *  @throws
	 */
	List<DepotTreeVO> selectTreeList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 下午4:28:46
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<DepotPageListVO>  
	 *  @throws
	 */
	Page<DepotPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:09
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(DepotAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:26
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  DepotDetailsVO  
	 *  @throws
	 */
	DepotDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:45
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(DepotEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:04:01
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(DepotDeleteDTO entity);

}
