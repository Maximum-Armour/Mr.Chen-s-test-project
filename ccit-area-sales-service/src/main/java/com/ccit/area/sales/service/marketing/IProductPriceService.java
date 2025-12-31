package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.ProductPricePO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceAddDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceSubmitDTO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPricePageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceSelectVO;

import java.util.Map;
 
/**
 * 	
 * 描述 : “产品定价”服务类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:05:05
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing
 * 类名 : IProductPriceService
 */
public interface IProductPriceService extends IService<ProductPricePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:05
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  Page<ProductPricePageListVO>
	 *  @throws
	 */
	Page<ProductPricePageListVO> selectPageList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:05
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : add
	 *  String
	 *  @throws
	 */
	String add(ProductPriceAddDTO entity);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:05
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : get
	 *  ProductPriceDetailsVO
	 *  @throws
	 */
	ProductPriceDetailsVO get(Long id);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:05
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : edit
	 *  String
	 *  @throws
	 */
	String edit(ProductPriceEditDTO entity);
	
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
	String delete(ProductPriceDeleteDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月12日 下午04:05:05
	 * 描述 : 提交数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : submit
	 *  String
	 *  @throws
	 */
	String submit(ProductPriceSubmitDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月12日 下午04:05:05
	 * 描述 : 审核数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : examine
	 *  String
	 *  @throws
	 */
	String examine(ProductPriceSubmitDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月24日 下午04:05:05
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : select
	 *  ProductPriceSelectVO
	 *  @throws
	 */
	ProductPriceSelectVO select(Long id);
}
