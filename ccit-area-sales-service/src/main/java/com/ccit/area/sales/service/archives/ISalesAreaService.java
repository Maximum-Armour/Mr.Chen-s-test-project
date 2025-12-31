package com.ccit.area.sales.service.archives;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.SalesAreaPO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaAddDTO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaEditDTO;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
import com.ccit.area.sales.dao.vo.archives.SalesAreaDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SalesAreaPageListVO;
 
/**
 * 
 * 描述 : “销售区域”服务类
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:39:46
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : ISalesAreaService
 */
public interface ISalesAreaService extends IService<SalesAreaPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:48:12
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<SalesAreaPageListVO>  
	 *  @throws
	 */
	Page<SalesAreaPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:48:18
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SalesAreaAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:48:26
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  SalesAreaDetailsVO  
	 *  @throws
	 */
	SalesAreaDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:48:35
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SalesAreaEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:48:44
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SalesAreaDeleteDTO entity);


//	/**
//	 *
//	 * 创建人 : tb
//	 * 创建时间 : 2024年10月18日 下午2:32:01
//	 * 描述 : 树列表，支持高级查询
//	 * 包名 : com.ccit.area.sales.service.archives
//	 * 方法名 : selectTreeList
//	 *  List<SalesAreaPageListVO>
//	 *  @throws
//	 */
//	List<SalesAreaPageListVO> selectTreeList(SalesAreaPageListVO param);
	
}
