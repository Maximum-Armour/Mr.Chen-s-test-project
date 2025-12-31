package com.ccit.area.sales.dao.vo.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “产品竞价详情”VO
  * 创建人 : tb
  * 创建时间 : 2024年9月6日 上午10:20:45
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : SalesBiddingDetailsVO
 */
@Data
@Schema(description = "【产品竞价详情】返回结果实体类")
public class SalesBiddingDetailsVO {

    /**
     * 产品竞价ID
     */
    @Schema(description = "产品竞价ID")
    private Long id;

    /**
     * 竞价编码
     */
    @Schema(description = "竞价编码")
    private String biddingNo;

    /**
     * 产品编码
     */
    private String materielNo;

    /**
     * 产品名称
     */
    private String materielName;
    /**
     * 竞价模式
     */
    @Schema(description = "竞价模式")
    private String biddingMode;

    /**
     * 区域编码
     */
    private String areaNo;
    /**
     * 区域名称
     */
    private String areaName;

    /**
     * '提货地编码'
     */
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    private String deliveryPlaceName;
    /**
     * 提货地分类编码
     */
    private String deliveryPlaceClassNo;
    /**
     * 提货地分类名称
     */
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String deliveryMethod;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String shippingType;

    /**
     * 竞价底价（元）
     */
    @Schema(description = "竞价底价（元）")
    private BigDecimal lowPrice;

    /**
     * 报名截止时间
     */
    @Schema(description = "报名截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signUpEndTime;

    /**
     * 起拍时间
     */
    @Schema(description = "起拍时间")
    private Date startingTime;

    /**
     * 竞价时长
     */
    @Schema(description = "竞价时长")
    private BigDecimal biddingDuration;

    /**
     * 竞价状态
     */
    @Schema(description = "竞价状态")
    private String status;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private String enableStatus;

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
    private String skuNo;

    /**
     * 牌号名称
     */
    private String skuName;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String specifications;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private String quantity;

    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String businessType;

    /**
     * 提货仓库编码
     */
    private String depotNo;

    /**
     * 提货仓库名称
     */
    private String depotName;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String paymentMethod;

    /**
     * 单价模式
     */
    @Schema(description = "单价模式")
    private String unitPriceMode;

    /**
     * 其他事项说明
     */
    @Schema(description = "其他事项说明")
    private String remarks;

    /**
     * 招标编号
     */
    @Schema(description = "招标编号")
    private String tenderNumber;

    /**
     * 币种
     */
    @Schema(description = "币种")
    private String currency;

    /**
     * 是否需要保证金
     */
    @Schema(description = "是否需要保证金")
    private String earnestMoney;

    /**
     * 是否需要确认
     */
    @Schema(description = "是否需要确认")
    private String confirm;

    /**
     * 是否允许延时
     */
    @Schema(description = "是否允许延时")
    private String allowedDelay;

    /**
     * 最少投标人
     */
    @Schema(description = "最少投标人")
    private String minBidders;

    /**
     * 是否需要报名
     */
    private String ifNeedApply;

    /**
     * 竞价底价建议（元）
     */
    @Schema(description = "竞价底价建议（元）")
    private BigDecimal lowPriceSuggestion;

    /**
     * 最低加价幅度（元）
     */
    @Schema(description = "最低加价幅度（元）")
    private BigDecimal minMarkup;

    /**
     * 保证金
     */
    @Schema(description = "保证金")
    private BigDecimal bond;

    /**
     * 保证金截止时间
     */
    @Schema(description = "保证金截止时间")
    private Date bondEndTime;

    /**
     * 竞价结束时间
     */
    private Date biddingEndTime;

    /**
     * 起拍量（吨）
     */
    @Schema(description = "起拍量（吨）")
    private BigDecimal startingQuantity;

    /**
     * 加量幅度（吨）
     */
    @Schema(description = "加量幅度（吨）")
    private BigDecimal increaseAmplitude;

    /**
     * 延时时长
     */
    @Schema(description = "延时时长")
    private Long delayDuration;

    /**
     * 距离结束时长
     */
    @Schema(description = "距离结束时长")
    private Long distanceEndDuration;

    /**
     * 创建者名称
     */
    private String createByName;
    /**
     * 创建者ID
     */
    private String createBy;

}
