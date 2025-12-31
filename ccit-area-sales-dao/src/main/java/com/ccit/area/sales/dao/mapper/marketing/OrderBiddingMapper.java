package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingOrderLineVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingPageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProcessOperationPageListVO;

import java.util.List;

/**
 *  * 描述 : “竞价订单”接口类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月24日 下午10:43:30
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.mapper.bidding
 *  * 类名 : OrderBiddingMapper
 */
public interface OrderBiddingMapper extends BaseMapper<SalesBiddingPO> {

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月24日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : metadata
     * List<OrderBiddingPageListVO>
     *
     * @throws
     */
    List<OrderBiddingPageListVO> metadata(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月24日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : metadata
     * List<OrderBiddingPageListVO>
     *
     * @throws
     */
    List<ProcessOperationPageListVO> metaCreate(String biddingNo);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月26日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : metadata
     * Integer
     *
     * @throws
     */
    Integer countCreate(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月26日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : participationCreate
     * List<OrderBiddingPageListVO>
     *
     * @throws
     */
    List<OrderBiddingPageListVO> participationCreate(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月26日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : ascCreate
     * List<OrderBiddingPageListVO>
     *
     * @throws
     */
    List<OrderBiddingPageListVO> ascCreate(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年10月9日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : bidDetails
     * List<OrderBiddingPageListVO>
     *
     * @throws
     */
    List<OrderBiddingPageListVO> bidDetails(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月4日 下午3:49:35
     * 描述 : 查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : orderLine
     * List<OrderBiddingOrderLineVO>
     *
     * @throws
     */
    List<OrderBiddingOrderLineVO> orderLine(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月4日 下午3:49:35
     * 描述 : 生成订单行
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : insertOrderLine
     * void
     *
     * @throws
     */
    int insertOrderLine(List<OrderBiddingOrderLineVO> list);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午3:49:35
     * 描述 : 获取组织简称
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : orgAbbreviation
     * String
     *
     * @throws
     */
    String orgNoAbbreviation(String orgNo);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月9日 下午3:49:35
     * 描述 : 获取组织编码
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectOrgNo
     * String
     *
     * @throws
     */
    String selectOrgNo(String userName);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月18日 下午3:49:35
     * 描述 : 更新状态
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : biddingStatus
     * int
     *
     * @throws
     */
    int biddingStatus(Long id);
}
