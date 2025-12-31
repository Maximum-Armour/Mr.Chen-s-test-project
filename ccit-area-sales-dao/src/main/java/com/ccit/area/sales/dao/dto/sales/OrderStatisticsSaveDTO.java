package com.ccit.area.sales.dao.dto.sales;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderStatisticsSaveDTO {

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
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

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
     * 创建者
     */
    private String createBy;

    /**
     * 创建者名称
     */
    private String createByName;
}
