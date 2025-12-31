package com.ccit.area.sales.service.archives;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlacePO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceEditDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlacePageListVO;
 
/**
 * 
 * 描述 : “提货地档案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:50:05
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : DeliveryPlaceService
 */
public interface IDeliveryPlaceService extends IService<DeliveryPlacePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:02
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlacePageListVO>  
	 *  @throws
	 */
	Page<DeliveryPlacePageListVO> selectPageList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:20
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(DeliveryPlaceAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:36
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  DeliveryPlaceDetailsVO  
	 *  @throws
	 */
	DeliveryPlaceDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:55
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(DeliveryPlaceEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:04:13
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(DeliveryPlaceDeleteDTO entity);
	
}
