package com.ccit.area.sales.dao.vo.sales;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  * 描述 : “订单”VO
 *  * 创建人 : cf
 *  * 创建时间 : 2024年9月18日 上午9:52:12
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.vo.sales
 *  * 类名 : OrderLineVO
 */
@Data
@Schema(description = "【客户意向详情】返回结果实体类")
public class  OrderLineVO {
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
    private String listedNo;

    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
    private String materielNo;
    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    private String materielName;
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
     * 订单生效日期
     */
    private Date orderStartTime;
    /**
     * 订单截至日期
     */
    private Date orderEndTime;

    /**
     * 订单提醒日期
     */
    @Schema(description = "订单提醒日期")
    private String ddWarnDay;

    /**
     * 订单审批时间
     */
    private Date orderApprovalTime;

    /**
     * 客户编码
     */
    @Schema(description = "客户编码")
    private String customerNo;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;


    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;


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
     * 产品分类
     */
    @Schema(description = "产品分类")
    private String productCategoryName;


    /**
     * 产品分类状态
     */
    @Schema(description = "产品分类状态")
    private String productStatus;

    /**
     * 流向协议
     */
    @Schema(description = "流向协议")
    private String flowprotocol;

    /**
     * 流向提报周期
     */
    @Schema(description = "流向提报周期")
    private String flowreportingcycle;

    /**
     * 现汇到账周期
     */
    @Schema(description = "现汇到账周期")
    private String cashtransfercycle;

    /**
     * 承兑到账周期
     */
    @Schema(description = "承兑到账周期")
    private String acceptancepaymentcycle;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String modeoftransport;

    /**
     * 延期天数
     */
    @Schema(description = "延期天数")
    private String delaydays;

    /**
     * 订单有效天数
     */
    @Schema(description = "订单有效天数")
    private String ordervaliditydays;

    /**
     * 新增条款
     */
    @Schema(description = "新增条款")
    private String newTerms;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 订单类型
     */
    @Schema(description = "订单类型")
    private String orderType;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer listedAmount;

    /**
     * 挂牌含税单价
     */
    @Schema(description = "挂牌含税单价")
    private BigDecimal listedTaxPrice;

    /**
     * 总金额
     */
    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    private String areaName;

    /**
     *
     */
    @Schema(description = "")
    private BigDecimal amountMoney;

    /**
     *
     */
    @Schema(description = "")
    private BigDecimal taxAmount;


    private Date gmtModified;

    private String productClassName;
    private String tbStatus;

}



