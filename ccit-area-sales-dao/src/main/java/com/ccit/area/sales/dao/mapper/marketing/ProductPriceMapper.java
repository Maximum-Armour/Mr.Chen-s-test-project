package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.ProductPriceItemPO;
import com.ccit.area.sales.dao.domain.marketing.ProductPricePO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPricePageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “产品定价”接口类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.marketing
 * 类名 : ProductPriceMapper
 */
public interface ProductPriceMapper extends BaseMapper<ProductPricePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<ProductPricePageListVO>  
	 *  @throws
	 */
	List<ProductPricePageListVO> selectPageList(Page<ProductPricePageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月14日 下午03:46:35
	 * 描述 : 查询个数
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectItemCount
	 *  Long
	 *  @throws
	 */
	Long selectItemCount();

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月14日 下午03:46:35
	 * 描述 : 新增
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : insertItem
	 *  void
	 *  @throws
	 */
	void insertItem(ProductPriceItemPO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月14日 下午03:46:35
	 * 描述 : 修改
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : updateItem
	 *  void
	 *  @throws
	 */
	void updateItem(ProductPriceItemPO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月16日 下午03:46:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : productPriceNoCount
	 *  Long
	 *  @throws
	 */
	Long productPriceNoCount(String userName, String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月16日 下午03:46:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : productPriceNoCount
	 *  Long
	 *  @throws
	 */
	Long productPriceItemNoCount(String productPriceNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午03:46:35
	 * 描述 : 查询组织简称
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : orgAbbreviation
	 *  String
	 *  @throws
	 */
	String orgNoAbbreviation(String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午03:46:35
	 * 描述 : 更新流水号
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : serialNumber
	 *  void
	 *  @throws
	 */
	void serialNumber(String serialNumber);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月20日 下午03:46:35
	 * 描述 : 审核通过后操作
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : shbz
	 *  void
	 *  @throws
	 */
	void shbz(@Param("param") RabbitApprovalRequest param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月3日 下午03:46:35
	 * 描述 : 仓库转换
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : depotName
	 *  String
	 *  @throws
	 */
	String depotName(String depotNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月3日 下午03:46:35
	 * 描述 : 公司编码
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectOrgNo
	 *  String
	 *  @throws
	 */
	String selectOrgNo(String depotNo);

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
	 *  List<ProductPriceItemPageListVO>
	 *  @throws
	 */
	List<ProductPriceItemPageListVO> selectItem(String productPriceNo);
}