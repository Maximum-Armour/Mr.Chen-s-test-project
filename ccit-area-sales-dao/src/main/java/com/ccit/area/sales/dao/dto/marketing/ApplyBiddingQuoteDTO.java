package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
  * 描述 : “竞价客户报价”DTO
  * 创建人 : tb
  * 创建时间 : 2024年12月20日 下午1:42:13
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : ApplyBiddingQuoteDTO
 */
@Data
@Schema(description = "【竞价申请报名】接受参数实体类")
public class ApplyBiddingQuoteDTO {

    private String  userName;


    private String realName;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerId;

    /**
     * 客户
     */
    @Schema(description = "客户")
    private String customer;

    /**
     * 客户IP
     */
    @Schema(description = "客户IP")
    private String customerIp;

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
     * 报价时间
     */
    @Schema(description = "报价时间")
    private String quotationTime;

    /**
     * 招标编号
     */
    @Schema(description = "招标编号")
    private String tenderNumber;

    /**
     * 竞价模式
     */
    @Schema(description = "竞价模式")
    private String bdMode;

    /**
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司编码
     */
    private String companyName;
}
