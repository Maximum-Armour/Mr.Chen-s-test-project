package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemeItemPO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “定价方案明细”接口类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:09:47
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.marketing
 * 类名 : PriceSchemeItemMapper
 */
public interface PriceSchemeItemMapper extends BaseMapper<PriceSchemeItemPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 上午9:17:13
	 * 描述 : 定价方案明细列表
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	List<PriceSchemeItemPageListVO> selectPageList(PriceSchemeItemPageListDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 上午9:17:13
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	List<PriceSchemeItemPageListVO> selectItemList(Page<PriceSchemeItemPageListVO> pages,
												   @Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月4日 下午3:49:35
	 * 描述 : 新增
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : insertOrderLine
	 *  void
	 *  @throws
	 */
	void insertPriceSchemeItem(String schemeNo, String schemeItemNo, String areaName, String deliveryPlaceName,
						   String deliveryPlaceClassName, String distributionWay, String transportWay,
						   String payWay, String currency, String unit,
						   Date gmtCreate, Date gmtModified);
}
