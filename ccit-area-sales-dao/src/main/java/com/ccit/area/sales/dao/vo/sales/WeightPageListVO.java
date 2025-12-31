package com.ccit.area.sales.dao.vo.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;



/**
 *
  * 描述 : “分量管理类详情”VO
  * 创建人 : cf
  * 创建时间 : 2024年9月11日 上午13:41:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.sales
  * 类名 : WeightPageListVO
 */
@Data
@Schema(description = "【分类管理详情】返回结果实体类")
public class WeightPageListVO {
    /**
     * 挂牌ID
     */
    @Schema(description = "挂牌ID")
    private Long id;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 挂牌编码
     */
    @Schema(description = "挂牌编码")
    private String listedNo;

    /**
     * 挂牌模式
     */
    @Schema(description = "挂牌模式")
    private String listedMode;

    /**
     * 挂牌模式名称
     */
    @Schema(description = "挂牌模式名称")
    private String listedModeName;

    /**
     * 挂牌不含税单价
     */
    @Schema(description = "挂牌不含税单价")
    private BigDecimal listedPrice;

    /**
     * 挂牌含税单价
     */
    @Schema(description = "挂牌含税单价")
    private BigDecimal listedTaxPrice;

    /**
     * 对标价格
     */
    @Schema(description = "对标价格")
    private BigDecimal benchmarkPrice;

    /**
     * 单价模式
     */
    @Schema(description = "单价模式")
    private String priceModel;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private String useStatus;

    /**
     * 上下架状态名称
     */
    @Schema(description = "上下架状态名称")
    private String useStatusName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String distributionWay;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String transportWay;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 支付方式名称
     */
    @Schema(description = "支付方式名称")
    private String payWayName;

    /**
     * 有效开始时间
     */
    @Schema(description = "有效开始时间")
    private String effectiveStartTime;

    /**
     * 有效结束时间
     */
    @Schema(description = "有效结束时间")
    private String effectiveEndTime;

    /**
     * 币种
     */
    @Schema(description = "币种")
    private String currency;

    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 单价单位
     */
    @Schema(description = "单价单位")
    private String unit;

    /**
     * 单价单位名称
     */
    @Schema(description = "单价单位名称")
    private String unitName;

    /**
     * 重量单位
     */
    @Schema(description = "重量单位")
    private String unitWeight;

    /**
     * 重量单位名称
     */
    @Schema(description = "重量单位名称")
    private String unitWeightName;

    /**
     * 区域编码
     */
    @Schema(description = "区域编码")
    private String areaNo;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    private String areaName;

    /**
     * 提货地编码
     */
    @Schema(description = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @Schema(description = "提货地名称")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @Schema(description = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @Schema(description = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 产品分类编码
     */
    @Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 牌号编码
     */
    @Schema(description = "牌号编码")
    private String skuNo;

    /**
     * 牌号名称
     */
    @Schema(description = "牌号名称")
    private String skuName;

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
     * 已分配订单数
     */
    @Schema(description = "已分配订单数")
    private String assignedOrders;

    /**
     * 待分配订单数
     */
    @Schema(description = "待分配订单数")
    private String pendingOrderAllocation;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码")
    private String orgNo;

    /**
     * 组织名称
     */
    @Schema(description = "组织名称")
    private String orgName;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptNo;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String gmtCreate;


}
