package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
  * 描述 : “定价方案明细修改”DTO
  * 创建人 : tb
  * 创建时间 : 2024年11月4日 下午2:50:52
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.marketing
  * 类名 : PriceSchemeItemEditDTO
 */
@Data
@Schema(description = "【定价方案明细修改】接受参数实体类")
public class PriceSchemeItemEditDTO {

    /**
     * 方案明细ID
     */
    @Schema(description = "方案明细ID")
    private Long id;

    /**
     * 方案编码
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "方案编码")
    private String schemeNo;

    /**
     * 方案明细编码
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "方案明细编码")
    private String schemeItemNo;

    /**
     * 区域名称
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "区域名称")
    private String areaName;

    /**
     * 提货地名称
     */
    @Length(max = 200, message = "方案编码长度不能超过50个字符")
    @Schema(description = "提货地名称")
    private String deliveryPlaceName;

    /**
     * 提货地分类名称
     */
    @Length(max = 200, message = "方案编码长度不能超过50个字符")
    @Schema(description = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "配送方式")
    private String distributionWay;

    /**
     * 运输方式
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "运输方式")
    private String transportWay;

    /**
     * 支付方式
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 币种
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "币种")
    private String currency;

    /**
     * 单位
     */
    @Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "单位")
    private String unit;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date gmtModified;
}
