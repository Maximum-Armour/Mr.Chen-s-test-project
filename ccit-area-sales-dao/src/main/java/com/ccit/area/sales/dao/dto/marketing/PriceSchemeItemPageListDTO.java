package com.ccit.area.sales.dao.dto.marketing;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案明细分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 上午9:34:13
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemeItemPageListDTO
 */
@Data
@Schema(description = "【定价方案明细分页列表】接受参数实体类")
public class PriceSchemeItemPageListDTO {

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
    @TableField(value = "delivery_place_no")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @TableField(value = "delivery_place_name")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @TableField(value = "delivery_place_class_no")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @TableField(value = "delivery_place_class_name")
    private String deliveryPlaceClassName;

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
     * 币种
     */
    @Schema(description = "币种")
    private String currency;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;
    
    /**
     * 方案ID
     */
	@Schema(description = "方案ID")
    private Long id;
	
	/**
	 * 不等于ID数组（逗号拼接）
	 */
	@Schema(description = "不等于ID数组（逗号拼接）")
	private String notIdList;

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
