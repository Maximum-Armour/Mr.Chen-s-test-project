package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingSubmitVO;

/**
 *
  * 描述 : “竞价订单”服务类
  * 创建人 : tb
  * 创建时间 : 2024年09月24日 上午10:58:05
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.service.bidding
  * 类名 : IOrderBiddingService
 */
public interface IOrderBiddingService extends IService<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月24日 上午09:10:05
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : get
     *  OrderBiddingDetailsVO
     *  @throws
     */
    OrderBiddingDetailsVO get(String tenderNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月25日 上午09:10:05
     * 描述 : 根据主键ID修改结果确认
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : edit
     *  String
     *  @throws
     */
    String edit(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月9日 上午09:10:05
     * 描述 : 提交钉钉审核审核通过后操作
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : apply
     *  String
     *  @throws
     */
    String createOrder(Long id);

    OrderBiddingSubmitVO submit(Long id);


}
