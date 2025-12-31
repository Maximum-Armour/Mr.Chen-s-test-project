package com.ccit.area.sales.dao.dto.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “订单表修改”DTO
  * 创建人 : cf
  * 创建时间 : 2024年09月19日 下午15:37:23
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.sales
  * 类名 : OrderLineDTO
 */
@Data
@Schema(description = "【订单行表】接受参数实体类")
public class OrderMouldDTO {

    private Long id;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 订单类型
     */
    @Schema(description = "订单类型")
    private String orderType;

    /**
     * 流向协议
     */
    @Schema(description = "流向协议")
    private String flowProtocol;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 订单行号
     */
    @Schema(description = "订单行号")
    private String orderLineNo;

    /**
     * 设施成本
     */
    @Schema(description = "设施成本")
    private BigDecimal fedCost;

    /**
     * 设施成本单位
     */
    @Schema(description = "设施成本单位")
    private String fedCostUnit;

    /**
     * 电子转帐
     */
    @Schema(description = "电子转帐")
    private String eftCycle;

    /**
     * 接受周期
     */
    @Schema(description = "接受周期")
    private String acceptCycle;

    /**
     * 货币结束日期
     */
    @Schema(description = "货币结束日期")
    private Date moneyEndDate;

    /**
     * 流程协议
     */
    @Schema(description = "流程协议")
    private String flowAgreement;

    /**
     * 流动周期
     */
    @Schema(description = "流动周期")
    private String flowCycle;

    /**
     * 流动结束日期
     */
    @Schema(description = "流动结束日期")
    private Date flowEndDate;

    /**
     * 星期
     */
    @Schema(description = "星期")
    private String week;

    /**
     * 价格加成
     */
    @Schema(description = "价格加成")
    private BigDecimal priceMarkup;

    /**
     * 价格加成单位
     */
    @Schema(description = "价格加成单位")
    private String priceMarkupUnit;

    /**
     * 设施开始日期
     */
    @Schema(description = "设施开始日期")
    private Date fedStartDate;

    /**
     * 设施结束日期
     */
    @Schema(description = "设施结束日期")
    private Date fedEndDate;

    /**
     * 结算起始日期
     */
    @Schema(description = "结算起始日期")
    private String settlementStartDay;

    /**
     * 结算终止日期
     */
    @Schema(description = "结算终止日期")
    private String settlementEndDay;

    /**
     * 结算数量
     */
    @Schema(description = "结算数量")
    private BigDecimal settlementQuantity;

    /**
     * 最后开始日期
     */
    @Schema(description = "最后开始日期")
    private String lastStartDay;

    /**
     * 最后结束日期
     */
    @Schema(description = "最后结束日期")
    private String lastEndDay;

    /**
     * 工作流id
     */
    @Schema(description = "工作流id")
    private String workflowId;

    /**
     * 审核标志
     */
    @Schema(description = "审核标志")
    private String shbz;
    /**
     * 订单生效日期
     */
    private String orderStartTime;
    /**
     * 订单生效日期
     */
    private String orderEndTime;

    /**
     * 更改日期
     */
    @Schema(description = "更改日期")
    private Date changeDate;

    /**
     * 更改用户ID
     */
    @Schema(description = "更改用户ID")
    private String changeUserId;

    /**
     * 更改用户名
     */
    @Schema(description = "更改用户名")
    private String changeUserName;

    /**
     * 最后用户ID
     */
    @Schema(description = "最后用户ID")
    private String lastUserId;

    /**
     * 最后用户名
     */
    @Schema(description = "最后用户名")
    private String lastUserName;

    /**
     * 最后日期
     */
    @Schema(description = "最后日期")
    private Date lastDate;

    /**
     *
     */
    @Schema(description = "")
    private Integer unflag;

    /**
     * 公司编号
     */
    @Schema(description = "公司编号")
    private String companyNo;

    /**
     * 金额差异
     */
    @Schema(description = "金额差异")
    private BigDecimal amountDifference;

    /**
     * 交货地点
     */
    @Schema(description = "交货地点")
    private String placeDelivery;

    /**
     * 最后交货日期
     */
    @Schema(description = "最后交货日期")
    private Date lastDeliveryDate;

    /**
     * HT结束日
     */
    @Schema(description = "HT结束日")
    private Date htEndDay;

    /**
     *
     */
    @Schema(description = "")
    private String ysfs;

    /**
     * 收购区
     */
    @Schema(description = "收购区")
    private String buyoutArea;

    /**
     * 合同新增条款
     */
    @Schema(description = "合同新增条款")
    private String newClause;

    /**
     * 合同编号
     */
    @Schema(description = "合同编号")
    private String htNo;

    /**
     * 采购员
     */
    @Schema(description = "采购员")
    private String buyer;

    /**
     * 买方担保人
     */
    @Schema(description = "买方担保人")
    private String buyerBuarantor;

    /**
     * 包装
     */
    @Schema(description = "包装")
    private String pack;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal price;

    /**
     * 端口
     */
    @Schema(description = "端口")
    private String port;

    /**
     * 付款
     */
    @Schema(description = "付款")
    private BigDecimal payment;

    /**
     * 发货日期
     */
    @Schema(description = "发货日期")
    private Date shipDate;

    /**
     * 暂停交货
     */
    @Schema(description = "暂停交货")
    private String deliveryClause;

    /**
     * 产品分类编码
     */
    @Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 提前率
     */
    @Schema(description = "提前率")
    private BigDecimal advanceRatio;

    /**
     * 存款比率
     */
    @Schema(description = "存款比率")
    private BigDecimal depositRatio;

    /**
     * 比率
     */
    @Schema(description = "比率")
    private BigDecimal rate;

    /**
     * 延时段
     */
    @Schema(description = "延时段")
    private Integer delayNumber;

    /**
     * 延迟日期
     */
    @Schema(description = "延迟日期")
    private Date delayDate;

    /**
     * 有效日期
     */
    @Schema(description = "有效日期")
    private BigDecimal validDate;

    /**
     * 流向提报周期
     */
    @Schema(description = "流向提报周期")
    private Integer flowReportingCycle;

    /**
     * 现汇到账周期
     */
    @Schema(description = "现汇到账周期")
    private Integer cashTransferCycle;

    /**
     * 承兑到账周期
     */
    @Schema(description = "承兑到账周期")
    private Integer acceptancePaymentCycle;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String modeOfTransport;

    /**
     * 延期天数
     */
    @Schema(description = "延期天数")
    private String delayDays;

    /**
     * 订单有效天数
     */
    @Schema(description = "订单有效天数")
    private Integer orderValidityDays;

    /**
     * 新增条款
     */
    @Schema(description = "新增条款")
    private String newTerms;


}
