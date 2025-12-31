package com.ccit.area.sales.dao.dto.marketing;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品定价明细修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月16日 下午4:27:40
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ProductPriceItemEditDTO
 */
@Data
@Schema(description = "【产品定价明细修改】接受参数实体类")
public class ProductPriceItemEditDTO {
	
	/**
     * 产品定价明细ID
     */
    @Schema(description = "产品定价明细ID")
	private Long id;

	/**
     * 不含税单价
     */
    @Schema(description = "不含税单价")
	private BigDecimal price;
	
    /**
     * 含税单价
     */
    @Schema(description = "含税单价")
	private BigDecimal taxPrice;
    
    /**
     * 仓库编码
     */
    @Schema(description = "仓库编码")
	private String depotNo;
	
    /**
     * 仓库名称
     */
    @Schema(description = "仓库名称")
	private String depotName;
	
    /**
     * 价格单位
     */
    @Schema(description = "价格单位")
	private String currencyUnit;
	
    /**
     * 对标价格
     */
    @Schema(description = "对标价格")
	private BigDecimal benchmarkPrice;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
	
}
