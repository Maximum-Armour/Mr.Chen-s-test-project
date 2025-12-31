package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.vo.marketing.ListedPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “挂牌”接口类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.marketing
 * 类名 : ListedMapper
 */
public interface ListedMapper extends BaseMapper<ListedPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectPageList
	 *  List<ListedPageListVO>  
	 *  @throws
	 */
	List<ListedPageListVO> selectPageList(Page<ListedPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月16日 下午03:46:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : listedNoCount
	 *  Long
	 *  @throws
	 */
	Long listedNoCount(String userName, String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月18日 下午03:46:35
	 * 描述 : 挂牌管理提交钉钉成功后续操作
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : listedStatus
	 *  void
	 *  @throws
	 */
	void listedStatus(@Param("param") RabbitApprovalRequest param);

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
	 * 创建时间 : 2024年12月3日 下午03:46:35
	 * 描述 : 查询组织公司
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectOrgNo
	 *  String
	 *  @throws
	 */
	String selectOrgNo(String orgNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月3日 下午03:46:35
	 * 描述 : 查询组织公司
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : selectOrgName
	 *  String
	 *  @throws
	 */
	String selectOrgName(String orgNo);
}