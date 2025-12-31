package com.ccit.area.sales.service.archives;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.MaterielPO;
import com.ccit.area.sales.dao.dto.archives.MaterielAddDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielEditDTO;
import com.ccit.area.sales.dao.vo.archives.MaterielDetailsVO;
import com.ccit.area.sales.dao.vo.archives.MaterielPageListVO;
 
/**
 * 
 * 描述 : “产品档案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:40:42
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : IMaterielService
 */
public interface IMaterielService extends IService<MaterielPO> {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:23:43
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<MaterielPageListVO>  
	 *  @throws
	 */
	Page<MaterielPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:24:00
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(MaterielAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:24:41
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  MaterielDetailsVO  
	 *  @throws
	 */
	MaterielDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:25:04
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(MaterielEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:25:09
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(MaterielDeleteDTO entity);
	
}
