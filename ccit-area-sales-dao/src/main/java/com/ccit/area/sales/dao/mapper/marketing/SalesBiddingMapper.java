package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
  * 描述 : “产品竞价”接口类
  * 创建人 : tb
  * 创建时间 : 2024年09月06日 下午10:43:30
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.mapper.bidding
  * 类名 : SalesBiddingMapper
 */
public interface SalesBiddingMapper extends BaseMapper<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午08:49:35
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectPageList
     *  List<SalesAreaPageListVO>
     *  @throws
     */
    List<SalesBiddingPageListVO> selectPageList(Page<SalesBiddingPageListVO> pages,
                                                @Param(value = "param") Map<String, Object> param);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月16日 上午08:49:35
     * 描述 : 生成编码
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : biddingNoCount
     *  Long
     *  @throws
     */
    Long biddingNoCount(String userName, String orgNo);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 上午08:49:35
     * 描述 : 增加流水号
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : serialNumber
     *  void
     *  @throws
     */
    void serialNumber(String serialNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 上午08:49:35
     * 描述 : 钉钉审核通过后操作
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : biddingStatus
     *  void
     *  @throws
     */
    void biddingStatus(@Param(value = "param") RabbitApprovalRequest param);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 上午08:49:35
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
     * 创建时间 : 2024年12月4日 下午3:49:35
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
     * 创建时间 : 2024年12月19日 下午3:49:35
     * 描述 : 获取客户个数
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectCustomer
     *  Long
     *  @throws
     */
    Long selectCustomer(String tenderNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月24日 下午3:49:35
     * 描述 : 查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selDeliveryWarehouseName
     *  String
     *  @throws
     */
    String selDeliveryWarehouseName(String depotNo);
}
