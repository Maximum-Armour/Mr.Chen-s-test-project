package com.ccit.area.sales.dao.domain.sales;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “订单行保存”DTO
  * 创建人 : cf
  * 创建时间 : 2024年09月19日 下午15:37:23
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.sales
  * 类名 : OrderLinePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_order_mould")
public class OrderMouldPO extends BasePO<OrderMouldPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 订单类型
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 流向协议
     */
    @TableField(value = "flow_protocol")
    private String flowProtocol;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 订单行号
     */
    @TableField(value = "order_line_no")
    private String orderLineNo;

    /**
     * 设施成本
     */
    @TableField(value = "fed_cost")
    private BigDecimal fedCost;

    /**
     * 设施成本单位
     */
    @TableField(value = "fed_cost_unit")
    private String fedCostUnit;

    /**
     * 电子转帐
     */
    @TableField(value = "eft_cycle")
    private String eftCycle;

    /**
     * 接受周期
     */
    @TableField(value = "accept_cycle")
    private String acceptCycle;

    /**
     * 货币结束日期
     */
    @TableField(value = "money_end_date")
    private Date moneyEndDate;

    /**
     * 流程协议
     */
    @TableField(value = "flow_agreement")
    private String flowAgreement;

    /**
     * 流动周期
     */
    @TableField(value = "flow_cycle")
    private String flowCycle;

    /**
     * 流动结束日期
     */
    @TableField(value = "flow_end_date")
    private Date flowEndDate;

    /**
     * 星期
     */
    @TableField(value = "week")
    private String week;

    /**
     * 价格加成
     */
    @TableField(value = "price_markup")
    private BigDecimal priceMarkup;

    /**
     * 价格加成单位
     */
    @TableField(value = "price_markup_unit")
    private String priceMarkupUnit;

    /**
     * 设施开始日期
     */
    @TableField(value = "fed_start_date")
    private Date fedStartDate;

    /**
     * 设施结束日期
     */
    @TableField(value = "fed_end_date")
    private Date fedEndDate;

    /**
     * 结算起始日期
     */
    @TableField(value = "settlement_start_day")
    private String settlementStartDay;

    /**
     * 结算终止日期
     */
    @TableField(value = "settlement_end_day")
    private String settlementEndDay;

    /**
     * 结算数量
     */
    @TableField(value = "settlement_quantity")
    private BigDecimal settlementQuantity;

    /**
     * 最后开始日期
     */
    @TableField(value = "last_syart_day")
    private String lastStartDay;

    /**
     * 最后结束日期
     */
    @TableField(value = "last_end_day")
    private String lastEndDay;

    /**
     * 工作流id
     */
    @TableField(value = "workflowid")
    private String workflowId;

    /**
     * 审核标志
     */
    @TableField(value = "shbz")
    private String shbz;

    /**
     * 更改日期
     */
    @TableField(value = "change_date")
    private Date changeDate;

    /**
     * 更改用户ID
     */
    @TableField(value = "change_user_id")
    private String changeUserId;

    /**
     * 更改用户名
     */
    @TableField(value = "change_user_name")
    private String changeUserName;

    /**
     * 最后用户ID
     */
    @TableField(value = "last_user_id")
    private String lastUserId;

    /**
     * 最后用户名
     */
    @TableField(value = "last_user_name")
    private String lastUserName;

    /**
     * 最后日期
     */
    @TableField(value = "last_date")
    private Date lastDate;

    /**
     *
     */
    @TableField(value = "unflag")
    private Integer unflag;

    /**
     * 公司编号
     */
    @TableField(value = "company_no")
    private String companyNo;

    /**
     * 金额差异
     */
    @TableField(value = "amountdifference")
    private BigDecimal amountDifference;

    /**
     * 交货地点
     */
    @TableField(value = "place_delivery")
    private String placeDelivery;

    /**
     * 最后交货日期
     */
    @TableField(value = "last_delivery_date")
    private Date lastDeliveryDate;

    /**
     * HT结束日
     */
    @TableField(value = "ht_end_day")
    private Date htEndDay;

    /**
     *
     */
    @TableField(value = "ysfs")
    private String ysfs;

    /**
     * 收购区
     */
    @TableField(value = "buyout_area")
    private String buyoutArea;

    /**
     * 合同新增条款
     */
    @TableField(value = "new_clause")
    private String newClause;

    /**
     * 合同编号
     */
    @TableField(value = "ht_no")
    private String htNo;

    /**
     * 采购员
     */
    @TableField(value = "buyer")
    private String buyer;

    /**
     * 买方担保人
     */
    @TableField(value = "buyer_buarantor")
    private String buyerBuarantor;

    /**
     * 包装
     */
    @TableField(value = "pack")
    private String pack;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 端口
     */
    @TableField(value = "port")
    private String port;

    /**
     * 付款
     */
    @TableField(value = "payment")
    private BigDecimal payment;

    /**
     * 发货日期
     */
    @TableField(value = "ship_date")
    private Date shipDate;

    /**
     * 暂停交货
     */
    @TableField(value = "delivery_clause")
    private String deliveryClause;

    /**
     * 产品分类编码
     */
    @TableField(value = "product_class_no")
    private String productClassNo;

    /**
     * 提前率
     */
    @TableField(value = "advance_ratio")
    private BigDecimal advanceRatio;

    /**
     * 存款比率
     */
    @TableField(value = "deposit_ratio")
    private BigDecimal depositRatio;

    /**
     * 比率
     */
    @TableField(value = "rate")
    private BigDecimal rate;

    /**
     * 延时段
     */
    @TableField(value = "delay_number")
    private Integer delayNumber;

    /**
     * 延迟日期
     */
    @TableField(value = "delay_date")
    private Date delayDate;

    /**
     * 有效日期
     */
    @TableField(value = "valid_date")
    private BigDecimal validDate;

    /**
     * 流向提报周期
     */
    @TableField(value = "flow_reporting_cycle")
    private Integer flowReportingCycle;

    /**
     * 现汇到账周期
     */
    @TableField(value = "cash_transfer_cycle")
    private Integer cashTransferCycle;

    /**
     * 承兑到账周期
     */
    @TableField(value = "acceptance_payment_cycle")
    private Integer acceptancePaymentCycle;

    /**
     * 运输方式
     */
    @TableField(value = "mode_of_transport")
    private String modeOfTransport;

    /**
     * 延期天数
     */
    @TableField(value = "delay_days")
    private String delayDays;

    /**
     * 订单有效天数
     */
    @TableField(value = "order_validity_days")
    private Integer orderValidityDays;

    /**
     * 新增条款
     */
    @TableField(value = "new_terms")
    private String newTerms;



    /**
     *
      * 创建人 : cf
      * 创建时间 : 2024年9月9日 下午16:07:09
      * 构造方法名 : WeightPageListPO()
      * 描述 :
     */
    public OrderMouldPO() {
    }

    /**
     *
      * 创建人 : cf
      * 创建时间 : 2024年9月9日 下午16:07:09
      * 构造方法名 : WeightPageListPO(Boolean deleted, Boolean isAutoFillUser) 
     * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
     */
    public OrderMouldPO(Boolean deleted, Boolean isAutoFillUser) {
        super.setDeleted(deleted ? 1 : 0);
        super.setDeletedTime(new Date());
        if (isAutoFillUser) {
            super.setDeletedByName(null);
            super.setDeletedTime(null);
        }
    }

    /**
     *
     * 创建人 : cf
     * 创建时间 :  2024年9月9日 下午16:09:13
     * 描述 : 包装器
     * 包名 : com.ccit.area.sales.dao.domain.weight
     * 方法名 : wrapper
     *  LambdaQueryWrapper<WeightPageListPO>
     *  @throws
     */
    public static LambdaQueryWrapper<OrderMouldPO> wrapper() {
        LambdaQueryWrapper<OrderMouldPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMouldPO::getDeleted, 0);
        return wrapper;
    }
}
