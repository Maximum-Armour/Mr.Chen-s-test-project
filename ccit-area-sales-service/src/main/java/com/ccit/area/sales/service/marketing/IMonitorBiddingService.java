package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingAddDTO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingVO;

/**
 *
  * 描述 : “竞价监控”服务类
  * 创建人 : tb
  * 创建时间 : 2024年10月9日 上午10:58:05
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.service.bidding
  * 类名 : IMonitorBiddingService
 */
public interface IMonitorBiddingService extends IService<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 上午09:10:05
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : get
     *  MonitorBiddingDetailsVO
     *  @throws
     */
    MonitorBiddingDetailsVO get(String tenderNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 上午09:10:05
     * 描述 : 根据对象查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : process
     *  MonitorBiddingDetailsVO
     *  @throws
     */
    MonitorBiddingDetailsVO process(ApplyBiddingAddDTO entity);

    MonitorBiddingVO supervisoryControl(String tenderNumber);
}
