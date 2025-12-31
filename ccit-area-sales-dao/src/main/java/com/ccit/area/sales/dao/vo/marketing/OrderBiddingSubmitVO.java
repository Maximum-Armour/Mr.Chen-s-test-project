package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderBiddingSubmitVO {

    /**
     * 产品竞价ID
     */
    @Schema(description = "产品竞价ID")
    private Long id;

    /**
     * 招标编号
     */
    @Schema(description = "竞价编号")
    private String tenderNumber;
    /**
     * 产品名称
     */
    private String materielName;
    /**
     * 提货地名称
     */
    private String deliveryPlaceName;
    /**
     * 提货仓库名称
     */
    private String depotName;
    /**
     * 配送方式
     */
    private String deliveryMethod;
    /**
     * 运输方式
     */
    private String shippingType;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * 产品规格
     */
    @Schema(description = "产品规格")
    private String specifications;
    /**
     * 产品数量
     */
    @Schema(description = "产品数量")
    private String quantity;
    /**
     * 竞价底价
     */
    @Schema(description = "竞价底价")
    private BigDecimal lowPrice;
    /**
     * 起拍时间
     */
    @Schema(description = "起拍时间")
    private Date startingTime;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date signUpEndTime;
    /**
     * 竞价时长
     */
    @Schema(description = "竞价时长")
    private BigDecimal biddingDuration;
    /**
     * 最低加价幅度
     */
    @Schema(description = "最低加价幅度")
    private BigDecimal minMarkup;
    /**
     * 起拍量
     */
    @Schema(description = "起拍量")
    private BigDecimal startingQuantity;
    /**
     * 最低加量幅度
     */
    @Schema(description = "最低加量幅度")
    private BigDecimal increaseAmplitude;
    /**
     * 最高成交单价
     */
    @Schema(description = "最高成交单价")
    private BigDecimal transactionPriceMax;
    /**
     * 最低成交单价
     */
    @Schema(description = "最低成交单价")
    private BigDecimal transactionPriceMin;
    /**
     * 成交总量
     */
    @Schema(description = "成交总量")
    private BigDecimal transactionQuantityTotal;
    /**
     * 成交客户数
     */
    @Schema(description = "成交客户数")
    private String transactionCustomer;
    /**
     * 参与客户数
     */
    @Schema(description = "参与客户数")
    private String totalCustomer;

    /**
     * 竞价延长次数
     */
    @Schema(description = "竞价延长次数")
    private String delayFrequency;

    /**
     * 产品说明
     */
    @Schema(description = "产品说明")
    private String productDescription;

    /**
     * 竞价说明
     */
    @Schema(description = "竞价说明")
    private String biddingDescription;
    /**
     * 竞价模式
     */
    @Schema(description = "竞价模式")
    private String biddingMode;

    private SalesBiddingResultPageListVO listVO;
}
