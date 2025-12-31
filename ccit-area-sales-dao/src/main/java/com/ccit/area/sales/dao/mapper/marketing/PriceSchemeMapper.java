package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemeItemPO;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemePO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemePageListVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “定价方案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:09:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.marketing
 * 类名 : PriceSchemeMapper
 */
public interface PriceSchemeMapper extends BaseMapper<PriceSchemePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:59:07
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<PriceSchemePageListVO>  
	 *  @throws
	 */
	List<PriceSchemePageListVO> selectPageList(Page<PriceSchemePageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月12日 下午3:49:35
	 * 描述 : 查询数据
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : selectItemId
	 *  String
	 *  @throws
	 */
	Long selectItemCount();

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月4日 下午3:49:35
	 * 描述 : 生成订单行
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : insertOrderLine
	 *  int
	 *  @throws
	 */
	void insertItem(PriceSchemeItemPO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月16日 下午3:49:35
	 * 描述 : 根据id修改竞价说明
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : updateBdY
	 *  void
	 *  @throws
	 */
	void updateItem(PriceSchemeItemPO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午3:49:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : schemeNoCount
	 *  Long
	 *  @throws
	 */
	BigDecimal selectSchemeNo(String code);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午3:49:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : schemeItemNoCount
	 *  Long
	 *  @throws
	 */
	Long schemeItemNoCount(String schemeNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月18日 下午3:49:35
	 * 描述 : 生成流水号
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : workflowid
	 *  void
	 *  @throws
	 */
	void workflowid(String workflowid);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月18日 下午3:49:35
	 * 描述 : 定价方案提交钉钉审核通过后续操作
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : shbz
	 *  void
	 *  @throws
	 */
	void shbz(@Param("param") RabbitApprovalRequest param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午3:49:35
	 * 描述 : 获取组织简称
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : orgAbbreviation
	 *  String
	 *  @throws
	 */
	String orgNoAbbreviation(String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月3日 下午3:49:35
	 * 描述 : 获取组织公司编码
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : selectOrgNo
	 *  String
	 *  @throws
	 */
	String selectOrgNo(String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月4日 下午3:49:35
	 * 描述 : 获取组织公司名称
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : selectOrgName
	 *  String
	 *  @throws
	 */
	String selectOrgName(String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月24日 下午3:49:35
	 * 描述 : 获取明细
	 * 包名 : com.ccit.area.sales.dao.mapper.bidding
	 * 方法名 : selectItem
	 *  List<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	List<PriceSchemeItemPageListVO> selectItem(String schemeNo);
}
