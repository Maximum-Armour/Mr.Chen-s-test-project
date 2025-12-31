package com.ccit.area.sales.dao.dto.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “产品竞价新增”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月6日 上午9:12:13
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SalesBiddingAddDTO
 */
@Data
@Schema(description = "【产品竞价新增】接受参数实体类")
public class SalesBiddingAddDTO {

    /**
     * 产品分类编码
     */
    @Length(max = 50, message = "产品分类编码长度不能超过50个字符")
    @Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @Length(max = 100, message = "产品分类名称长度不能超过100个字符")
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 牌号编码
     */
    @Length(max = 50, message = "牌号编码长度不能超过50个字符")
    @Schema(description = "牌号编码")
    private String skuNo;

    /**
     * 牌号名称
     */
    @Length(max = 100, message = "牌号名称长度不能超过100个字符")
    @Schema(description = "牌号名称")
    private String skuName;

    /**
     * 产品编码
     */
    @Length(max = 50, message = "产品编码长度不能超过50个字符")
    @Schema(description = "产品编码")
    private String materielNo;

    /**
     * 产品名称
     */
    @Length(max = 100, message = "产品名称长度不能超过100个字符")
    @Schema(description = "产品名称")
    private String materielName;

    /**
     * 规格
     */
    @Length(max = 100, message = "规格长度不能超过100个字符")
    @Schema(description = "规格")
    private String specifications;

    /**
     * 数量
     */
    @Length(max = 100, message = "数量长度不能超过100个字符")
    @Schema(description = "数量")
    private String quantity;

    /**
     * 业务类型
     */
    @Length(max = 100, message = "业务类型长度不能超过100个字符")
    @Schema(description = "业务类型")
    private String businessType;

    /**
     * 区域编码
     */
    private String areaNo;
    /**
     * 区域名称
     */
    @Length(max = 100, message = "区域名称长度不能超过100个字符")
    private String areaName;

    /**
     * 提货地编码
     */
    private String deliveryPlaceNo;
    /**
     * 提货地名称
     */
    @Length(max = 100, message = "提货地名称长度不能超过100个字符")
    private String deliveryPlaceName;
    /**
     * 提货地分类编码
     */
    private String deliveryPlaceClassNo;
    /**
     * 提货地分类名称
     */
    @Length(max = 100, message = "提货地分类长度不能超过100个字符")
    private String deliveryPlaceClassName;

    /**
     * 提货仓库编码
     */
    private String depotNo;

    /**
     * 提货仓库名称
     */
    @Length(max = 100, message = "提货仓库长度不能超过100个字符")
    private String depotName;

    /**
     * 配送方式
     */
    @Length(max = 100, message = "配送方式长度不能超过100个字符")
    @Schema(description = "配送方式")
    private String deliveryMethod;

    /**
     * 运输方式
     */
    @Length(max = 100, message = "运输方式长度不能超过100个字符")
    @Schema(description = "运输方式")
    private String shippingType;

    /**
     * 支付方式
     */
    @Length(max = 100, message = "支付方式长度不能超过100个字符")
    @Schema(description = "支付方式")
    private String paymentMethod;

    /**
     * 单价模式
     */
    @Length(max = 100, message = "单价模式长度不能超过100个字符")
    @Schema(description = "单价模式")
    private String unitPriceMode;

    /**
     * 其他事项说明
     */
    @Length(max = 500, message = "其他事项说明长度不能超过500个字符")
    @Schema(description = "其他事项说明")
    private String remarks;

    /**
     * 竞价模式
     */
    @Length(max = 100, message = "竞价模式长度不能超过100个字符")
    @Schema(description = "竞价模式")
    private String biddingMode;

    /**
     * 招标编号
     */
    @Length(max = 100, message = "招标编号长度不能超过100个字符")
    @Schema(description = "招标编号")
    private String tenderNumber;

    /**
     * 币种
     */
    @Length(max = 100, message = "币种长度不能超过100个字符")
    @Schema(description = "币种")
    private String currency;

    /**
     * 是否需要保证金
     */
    @Length(max = 100, message = "是否需要保证金长度不能超过100个字符")
    @Schema(description = "是否需要保证金")
    private String earnestMoney;

    /**
     * 是否需要确认
     */
    @Length(max = 100, message = "是否需要确认不能超过100个字符")
    @Schema(description = "是否需要确认")
    private String confirm;

    /**
     * 是否允许延时
     */
    @Length(max = 100, message = "是否允许延时长度不能超过100个字符")
    @Schema(description = "是否允许延时")
    private String allowedDelay;

    /**
     * 最少投标人
     */
    @Length(max = 100, message = "最少投标人长度不能超过100个字符")
    @Schema(description = "最少投标人")
    private String minBidders;

    /**
     * 是否需要报名
     */
    @Length(max = 100, message = "是否需要报名长度不能超过100个字符")
    @Schema(description = "是否需要报名")
    private String ifNeedApply;

    /**
     * 竞价底价建议（元）
     */
    @Schema(description = "竞价底价建议（元）")
    private BigDecimal lowPriceSuggestion;

    /**
     * 竞价底价（元）
     */
    @Schema(description = "竞价底价（元）")
    private BigDecimal lowPrice;

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
     * 报名截止时间
     */
    @Schema(description = "报名截止时间")
    private String signUpEndTime;

    /**
     * 保证金截止时间
     */
    @Schema(description = "保证金截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bondEndTime;

    /**
     * 起拍时间
     */
    @Schema(description = "起拍时间")
    private String startingTime;

    /**
     * 竞价时长
     */
    @Schema(description = "竞价时长")
    private BigDecimal biddingDuration;

    /**
     * 起拍量（吨）
     */
    @Schema(description = "起拍量（吨）")
    private BigDecimal startingQuantity;

    /**
     * 加量幅度（吨）
     */
    @Schema(description = "加价幅度（吨）")
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
     * 公司编码
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码")
    private String orgNo;

}
