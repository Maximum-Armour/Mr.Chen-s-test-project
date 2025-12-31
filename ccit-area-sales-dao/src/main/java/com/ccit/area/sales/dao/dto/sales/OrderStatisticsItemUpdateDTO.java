package com.ccit.area.sales.dao.dto.sales;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderStatisticsItemUpdateDTO {
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
     * 订单提交价格
     */
    private BigDecimal orderSubmissionPrice;

    /**
     * 订单提交数量
     */
    private BigDecimal orderSubmissionQuantity;

    /**
     * '删除标识：0-未删除；1-已删除；',
     */
    private Integer deleted;
}
