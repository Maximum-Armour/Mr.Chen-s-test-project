package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月9日 下午3:06:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemePageListDTO
 */
@Data
@Schema(description = "【定价方案分页列表】接受参数实体类")
public class PriceSchemePageListDTO {

	/**
     * 方案ID
     */
	@Schema(description = "方案ID")
    private Long id;
	
	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

	/**
     * 方案编码
     */
    @Schema(description = "方案编码")
    private String schemeNo;

    /**
     * 方案名称
     */
    @Schema(description = "方案名称")
    private String schemeName;

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
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;

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
