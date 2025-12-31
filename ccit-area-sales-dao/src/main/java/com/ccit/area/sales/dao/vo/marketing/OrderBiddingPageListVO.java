package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “竞价结果分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年9月24日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : OrderBiddingPageListVO
 */
@Data
@Schema(description = "【竞价结果分页列表】返回结果实体类")
public class OrderBiddingPageListVO {

    /**
     * 产品竞价ID
     */
    @Schema(description = "产品竞价ID")
    private Long id;

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
     * 数量（吨）
     */
    @Schema(description = "数量（吨）")
    private BigDecimal quantity;

    /**
     * 成交价（元）
     */
    @Schema(description = "成交价（元）")
    private BigDecimal transactionPrice;

    /**
     * 成交数量（吨）
     */
    @Schema(description = "成交数量（吨）")
    private BigDecimal transactionQuantity;

    /**
     * 报价时间
     */
    @Schema(description = "报价时间")
    private String quotationTime;

    /**
     * 中标状态
     */
    @Schema(description = "中标状态")
    private String winBidStatus;
}
