package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
  * 描述 : “产品竞价分页列表”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月9日 下午1:38:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SalesBiddingPageListDTO
 */
@Data
@Schema(description = "【产品竞价分页列表】接受参数实体类")
public class SalesBiddingPageListDTO {

    /**
     * 竞价状态
     */
    @Schema(description = "竞价状态")
    private String status;

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
    private String signUpEndTime;

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
     * 启用状态
     */
    @Schema(description = "启用状态")
    private String enableStatus;

    /**
     * 创建者账号
     */
    @Schema(description = "创建者账号")
    private String userName;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码")
    private String orgNo;
}
