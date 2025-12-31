package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.marketing.ProductPriceItemPO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “产品定价明细”接口类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.marketing
 * 类名 : ProductPriceItemMapper
 */
public interface ProductPriceItemMapper extends BaseMapper<ProductPriceItemPO> {

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午03:46:35
	 * 描述 : 产品定价明细列表
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<ProductPriceItemPageListVO>
	 *  @throws
	 */
	List<ProductPriceItemPageListVO> selectPageList(ProductPriceItemPageListDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 下午03:46:35
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<ProductPriceItemPageListVO>
	 *  @throws
	 */
	List<ProductPriceItemPageListVO> selectList(Page<ProductPriceItemPageListVO> pages,
													@Param(value = "param") Map<String, Object> param);
}