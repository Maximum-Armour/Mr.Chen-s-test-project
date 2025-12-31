package com.ccit.area.sales.dao.dto.marketing;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案明细新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 上午11:02:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemeItemAddDTO
 */
@Data
@Schema(description = "【定价方案明细新增】接受参数实体类")
public class PriceSchemeItemAddDTO {

    /**
     * 方案明细编码
     */
	@Length(max = 50, message = "方案明细编码长度不能超过50个字符")
    @Schema(description = "方案明细编码")
    private String schemeItemNo;

    /**
     * 区域编码
     */
    @Length(max = 50, message = "区域编码长度不能超过50个字符")
    @Schema(description = "区域编码")
    private String areaNo;

    /**
     * 区域名称
     */
    @Length(max = 50, message = "区域名称长度不能超过50个字符")
    @Schema(description = "区域名称")
    private String areaName;

    /**
     * 提货地编码
     */
    @Length(max = 50, message = "提货地编码长度不能超过50个字符")
    @Schema(description = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @Length(max = 200, message = "提货地名称长度不能超过200个字符")
    @Schema(description = "提货地名称")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @Length(max = 50, message = "提货地分类编码长度不能超过50个字符")
    @Schema(description = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @Length(max = 200, message = "提货地分类名称长度不能超过200个字符")
    @Schema(description = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Length(max = 50, message = "配送方式长度不能超过50个字符")
    @Schema(description = "配送方式")
    private String distributionWay;

    /**
     * 运输方式
     */
    @Length(max = 50, message = "运输方式长度不能超过50个字符")
    @Schema(description = "运输方式")
    private String transportWay;

    /**
     * 支付方式
     */
    @Length(max = 50, message = "支付方式长度不能超过50个字符")
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 币种
     */
    @Length(max = 50, message = "币种长度不能超过50个字符")
    @Schema(description = "币种")
    private String currency;

    /**
     * 单位
     */
    @Length(max = 50, message = "单位长度不能超过50个字符")
    @Schema(description = "单位")
    private String unit;

    /**
     * 创建者账号
     */
    @Length(max = 50, message = "创建者账号不能超过50个字符")
    @Schema(description = "创建者账号")
    private String userName;

    /**
     * 组织编码
     */
    @Length(max = 50, message = "组织编码不能超过50个字符")
    @Schema(description = "组织编码")
    private String orgNo;
}
