package com.ccit.area.sales.dao.dto.sales;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatisticsItemDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 接单编码
     */
    private String takeOrderNo;

    /**
     * 订单行编码
     */
    private String orderLineNo;

    /**
     * 客户编码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 产品编码
     */
    private String materielNo;

    /**
     * 产品名称
     */
    private String materielName;

    /**
     * 订单成交价格
     */
    private BigDecimal transactionPrice;

    /**
     * 订单成交数量
     */
    private BigDecimal turnoverQuantity;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 创建者名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
