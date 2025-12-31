package com.ccit.area.sales.dao.dto.marketing;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesBiddingResultPageListDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户账号
     */
    private String customerId;

    /**
     * 客户
     */
    private String customer;

    /**
     * 最新报价(元)
     */
    private BigDecimal latestQuotation;
    /**
     * 数量(吨)
     */
    private BigDecimal quantity;

    /**
     * 总金额
     */
    private BigDecimal  totalAmount;

    /**
     * 报价时间
     */
    private String quotationTime;
    /**
     * 中标状态
     */
    private String winBidStatus;

    private String tenderNumber;
}
