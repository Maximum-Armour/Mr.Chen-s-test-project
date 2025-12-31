package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “竞价监控分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年10月9日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : MonitorBiddingPageListVO
 */
@Data
@Schema(description = "【竞价监控分页列表】返回结果实体类")
public class MonitorBiddingPageListVO {

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
     * 报价次数
     */
    @Schema(description = "报价次数")
    private int biddingFrequency;

    /**
     * 报价时间
     */
    @Schema(description = "报价时间")
    private Date quotationTime;


    /**
     * 初始报价（元）
     */
    @Schema(description = "初始报价（元）")
    private BigDecimal initialQuotation;

    /**
     * 幅度
     */
    @Schema(description = "幅度")
    private BigDecimal markUp;
}
