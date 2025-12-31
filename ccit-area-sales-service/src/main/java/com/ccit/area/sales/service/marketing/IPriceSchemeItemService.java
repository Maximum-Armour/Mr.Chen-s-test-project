package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemeItemPO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “定价方案明细”服务类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:12:55
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing
 * 类名 : IPriceSchemeItemService
 */
public interface IPriceSchemeItemService extends IService<PriceSchemeItemPO> {

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 上午8:58:50
	 * 描述 : 定价方案明细列表
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  List<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	List<PriceSchemeItemPageListVO> selectPageList(PriceSchemeItemPageListDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 上午8:58:50
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  Page<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	Page<PriceSchemeItemPageListVO> selectItemList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 上午8:58:53
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(PriceSchemeItemDeleteDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月04日 下午03:23:23
	 * 描述 : 修改/新增数据
	 * 包名 : com.ccit.area.sales.service.bidding
	 * 方法名 : edit
	 *  PriceSchemeItemEditDTO
	 *  @throws
	 */
	String edit(PriceSchemeItemEditDTO entity);
	
}
