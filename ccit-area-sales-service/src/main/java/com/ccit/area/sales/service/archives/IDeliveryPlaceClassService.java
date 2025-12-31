package com.ccit.area.sales.service.archives;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceClassPO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassEditDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassPageListVO;
 
/**
 * 
 * 描述 : “提货地分类”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:49:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : DeliveryPlaceClassService
 */
public interface IDeliveryPlaceClassService extends IService<DeliveryPlaceClassPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:02:57
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlaceClassPageListVO>  
	 *  @throws
	 */
	Page<DeliveryPlaceClassPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:15
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(DeliveryPlaceClassAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:31
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  DeliveryPlaceClassDetailsVO  
	 *  @throws
	 */
	DeliveryPlaceClassDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:03:50
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(DeliveryPlaceClassEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:04:07
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(DeliveryPlaceClassDeleteDTO entity);
	
}
