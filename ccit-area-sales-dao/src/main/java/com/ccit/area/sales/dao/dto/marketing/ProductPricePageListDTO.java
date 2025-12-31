package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品定价分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月14日 下午1:57:12
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ProductPricePageListDTO
 */
@Data
@Schema(description = "【产品定价分页列表】接受参数实体类")
public class ProductPricePageListDTO {
	
	/**
     * 审核标志：1-未提交；2、审核中；
     */
    @Schema(description = "审核标志：1-未提交；2、审核中；")
	private String shbz;
	
	/**
     * 状态
     */
    @Schema(description = "状态")
	private String status;
	
    /**
     * 产品定价编码
     */
    @Schema(description = "产品定价编码")
	private String productPriceNo;
	
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
     * 单价模式
     */
    @Schema(description = "单价模式")
	private String univalenceModel;
    
    /**
     * 业务类型
     */
	@Schema(description = "业务类型")
	private String businessType;
	
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
