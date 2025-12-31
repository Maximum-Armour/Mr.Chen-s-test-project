package com.ccit.area.sales.service.archives;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.SkuFilePO;
import com.ccit.area.sales.dao.dto.archives.SkuFileAddDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileEditDTO;
import com.ccit.area.sales.dao.vo.archives.SkuFileDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SkuFilePageListVO;
 
/**
 * 
 * 描述 : “牌号档案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:43:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : ISkuFileService
 */
public interface ISkuFileService extends IService<SkuFilePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:50:54
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<SkuFilePageListVO>  
	 *  @throws
	 */
	Page<SkuFilePageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:50:59
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SkuFileAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:51:03
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  SkuFileDetailsVO  
	 *  @throws
	 */
	SkuFileDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:51:11
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SkuFileEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:51:16
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SkuFileDeleteDTO entity);
	
}
