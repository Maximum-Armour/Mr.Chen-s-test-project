package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.vo.marketing.CompaniesListVO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingPageListVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingPageListVO;

import java.util.List;

/**
 *
  * 描述 : “竞价监控”接口类
  * 创建人 : tb
  * 创建时间 : 2024年10月9日 下午10:43:30
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.mapper.bidding
  * 类名 : MonitorBiddingMapper
 */
public interface MonitorBiddingMapper extends BaseMapper<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月10日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : metaCreate
     *  List<MonitorBiddingPageListVO>
     *  @throws
     */
    List<MonitorBiddingPageListVO> metaCreate(String tenderNumber, String customerId);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月10日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : bidDetails
     *  List<OrderBiddingPageListVO>
     *  @throws
     */
    List<OrderBiddingPageListVO> bidDetails(String tenderNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月10日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : bidDetails
     *  List<OrderBiddingPageListVO>
     *  @throws
     */
    List<OrderBiddingPageListVO> bidDetailsJJ(String tenderNumber, String customerId);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月24日 下午3:49:35
     * 描述 : 查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selAddressName
     *  String
     *  @throws
     */
    String selAddressName(String deliveryPlaceNo);

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

    List<MonitorBiddingPageListVO> bidList(String tenderNumber);

    List<CompaniesListVO> getCompaniesList(String tenderNumber);
}
