package com.ccit.area.sales.dao.domain.sales;

import com.baomidou.mybatisplus.annotation.*;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 接单统计明细表
 * @TableName t_order_statistics_item
 */
@TableName(value ="t_order_statistics_item")
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderStatisticsItemPO extends BasePO<OrderStatisticsItemPO>{
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接单编码
     */
    @TableField(value = "take_order_no")
    private String takeOrderNo;

    /**
     * 订单行编码
     */
    @TableField(value = "order_line_no")
    private String orderLineNo;

    /**
     * 客户编码
     */
    @TableField(value = "customer_no")
    private String customerNo;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 公司编码
     */
    @TableField(value = "company_no", fill = FieldFill.INSERT)
    private String companyNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name", fill = FieldFill.INSERT)
    private String companyName;

    /**
     * 产品编码
     */
    @TableField(value = "materiel_no")
    private String materielNo;

    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
    private String materielName;

    /**
     * 订单提交价格
     */
    @TableField(value = "order_submission_price")
    private BigDecimal orderSubmissionPrice;

    /**
     * 订单提交数量
     */
    @TableField(value = "order_submission_quantity")
    private BigDecimal orderSubmissionQuantity;
}