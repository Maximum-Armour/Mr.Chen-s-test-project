package com.ccit.area.sales.dao.vo.marketing;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案明细分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 上午9:26:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.marketing
 * 类名 : PriceSchemeItemPageListVO
 */
@Data
@Schema(description = "【定价方案明细分页列表】返回结果实体类")
public class PriceSchemeItemPageListVO {

	/**
     * 方案明细ID
     */
	@Schema(description = "方案明细ID")
    private Long id;
	
	/**
     * 方案编码
     */
    @Schema(description = "方案编码")
    private String schemeNo;
    
	/**
     * 方案明细编码
     */
    @Schema(description = "方案明细编码")
    private String schemeItemNo;
    
    /**
     * 方案名称
     */
    @Schema(description = "方案名称")
    private String schemeName;
    
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
    @TableField(value = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @TableField(value = "提货地名称")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @TableField(value = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @TableField(value = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String distributionWay;

    /**
     * 配送方式名称
     */
    @Schema(description = "配送方式名称")
    private String distributionWayName;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String transportWay;

    /**
     * 运输方式名称
     */
    @Schema(description = "运输方式名称")
    private String transportWayName;

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
     * 币种
     */
    @Schema(description = "币种")
    private String currency;

    /**
     * 币种名称
     */
    @Schema(description = "币种名称")
    private String currencyName;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 单位名称
     */
    @Schema(description = "单位名称")
    private String unitName;
	
}
