package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingResultPageListDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingSubmitDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesWinBidStatusDTO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingResultPageListVO;

import java.util.List;

/**
 *  * 描述 : “竞价结果”服务类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月18日 上午10:58:05
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding
 *  * 类名 : IResultBiddingService
 */
public interface IResultBiddingService extends IService<SalesBiddingPO> {

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午08:54:25
     * 描述 : 数据分页查询
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : selectPageList
     * String
     *
     * @throws
     */
    List<SalesBiddingResultPageListVO> selectPageList(SalesBiddingResultPageListDTO param);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 下午03:43:14
     * 描述 : 审核数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : submit
     * SalesBiddingSubmitDTO
     *
     * @throws
     */
    String submit(SalesBiddingSubmitDTO entity);

    String updateWinBidStatus(SalesWinBidStatusDTO entity);
}
