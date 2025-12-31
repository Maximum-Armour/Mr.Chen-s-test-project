package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
  * 描述 : “竞价审核”VO
  * 创建人 : tb
  * 创建时间 :2024年10月16日 下午3:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : ApplyBiddingDetailsVO
 */
@Data
@Schema(description = "【竞价审核】返回结果实体类")
public class ApplyBiddingDetailsVO {

    /**
     * 招标编号
     */
    @Schema(description = "招标编号")
    private String tenderNumber;

    /**
     * 客户
     */
    @Schema(description = "客户")
    private String customer;

    /**
     * 最新报价（元）
     */
    @Schema(description = "最新报价（元）")
    private BigDecimal latestQuotation;

    /**
     * 竞价说明
     */
    @Schema(description = "竞价说明")
    private String biddingDescription;
}
