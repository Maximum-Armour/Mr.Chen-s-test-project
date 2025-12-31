package com.ccit.area.sales.dao.dto.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “订单”DTO
  * 创建人 : cf
  * 创建时间 : 2024年09月14日 下午10:37:23
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.sales
  * 类名 : OrderLineDTO
 */
@Data
@Schema(description = "【订单表】接受参数实体类")
public class OrderLineDTO {

    /**
     * 主键
     */

    private Long id;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 订单编码
     */
    @Schema(description = "订单编码")
    private String orderNo;

    /**
     * 订单行编号
     */
    @Schema(description = "订单行编号")
    private String orderLineNo;

    /**
     * 挂牌编码
     */
    @Schema(description = "挂牌编码")
    private String listingNo;

    /**
     * 订单提交价格
     */
    @Schema(description = "订单提交价格")
    private BigDecimal orderSubmissionPrice;

    /**
     * 订单提交数量
     */
    @Schema(description = "订单提交数量")
    private BigDecimal orderSubmissionQuantity;

    /**
     * 订单成交价格
     */
    @Schema(description = "订单成交价格")
    private BigDecimal transactionPrice;

    /**
     * 订单成交数量
     */
    @Schema(description = "订单成交数量")
    private BigDecimal turnoverQuantity;

    /**
     * 运费
     */
    @Schema(description = "运费")
    private BigDecimal freight;

    /**
     * 对标价格
     */
    @Schema(description = "对标价格")
    private BigDecimal benchmarkPrice;

    /**
     * 订单截至日期
     */
    @Schema(description = "订单截至日期")
    private String ddEndDay;

    /**
     * 订单提醒日期
     */
    @Schema(description = "订单提醒日期")
    private String ddWarnDay;

    /**
     * 客户编码
     */
    @Schema(description = "客户编码")
    private Long customerNo;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;


    /**
     * 备注
     */
    @Schema(description = "备注")
    private  String remark;


    /**
     * 工作流id
     */
    @Schema(description = "工作流ID")
    private String workflowid;

    /**
     * 审核标志
     */
    @Schema(description = "审核标志")
    private String shbz;

    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String companyNo;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;

    /**
     *创建者
     */
    @Schema(description = "创建者")
    private String createBy;

    /**
     *订单类型
     */
    @Schema(description = "订单类型")
    private String orderType;

    private String materielName;
    private String listedAmount;
    private String listedTaxPrice;
    private String totalAmount;
    private String areaName;
    private String amountMoney;
    private String taxAmount;
    private String productClassName;
    //订单生效时间
    private String ddEffectiveDay;
    //订单审批时间
    private Date orderApprovalTime;
    private String tbStatus;

}
