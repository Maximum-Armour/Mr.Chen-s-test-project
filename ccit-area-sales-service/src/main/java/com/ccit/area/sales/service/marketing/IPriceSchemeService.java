package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemePO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeAddDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeEditDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeSubmitDTO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemePageListVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeSelectVO;

import java.util.Map;
 
/**
 * 
 * 描述 : “定价方案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:12:40
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing
 * 类名 : IPriceSchemeService
 */
public interface IPriceSchemeService extends IService<PriceSchemePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:52
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  Page<PriceSchemePageListVO>
	 *  @throws
	 */
	Page<PriceSchemePageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:42:05
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(PriceSchemeAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:42:08
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : get
	 *  PriceSchemeDetailsVO  
	 *  @throws
	 */
	PriceSchemeDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:42:22
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(PriceSchemeEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:42:25
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(PriceSchemeDeleteDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月7日 下午2:42:22
	 * 描述 : 提交数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : submit
	 *  String
	 *  @throws
	 */
	String submit(PriceSchemeSubmitDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午2:42:22
	 * 描述 : 审核数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : examine
	 *  String
	 *  @throws
	 */
	String examine(PriceSchemeSubmitDTO entity);

	/**
	 *
	 * 创建人 : yn
	 * 创建时间 : 2024年12月24日 下午2:42:08
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : select
	 *  PriceSchemeSelectVO
	 *  @throws
	 */
	PriceSchemeSelectVO select(Long id);
}
