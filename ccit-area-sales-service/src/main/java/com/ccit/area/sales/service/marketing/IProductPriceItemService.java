package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.ProductPriceItemPO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;

import java.util.List;
import java.util.Map;

/**
 * 	
 * 描述 : “产品定价明细”服务类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:05:05
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing
 * 类名 : IProductPriceItemService
 */
public interface IProductPriceItemService extends IService<ProductPriceItemPO> {

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午04:05:05
	 * 描述 : 产品定价明细列表
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  List<ProductPriceItemPageListVO>
	 *  @throws
	 */
	List<ProductPriceItemPageListVO> selectPageList(ProductPriceItemPageListDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 下午04:05:05
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  Page<ProductPriceItemPageListVO>
	 *  @throws
	 */
	Page<ProductPriceItemPageListVO> selectList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月16日 下午4:23:04
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(ProductPriceItemEditDTO entity);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:05
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : delete
	 *  String
	 *  @throws
	 */
	String delete(ProductPriceItemDeleteDTO entity);

}
