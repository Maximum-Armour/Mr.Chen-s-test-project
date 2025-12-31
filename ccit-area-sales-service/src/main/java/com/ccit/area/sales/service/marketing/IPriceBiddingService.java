package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.PriceBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingQuoteDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;

public interface IPriceBiddingService extends IService<PriceBiddingPO> {
    String quote(ApplyBiddingQuoteDTO entity);

    String updateStatus(ApplyBiddingSubmitDTO entity);
}
