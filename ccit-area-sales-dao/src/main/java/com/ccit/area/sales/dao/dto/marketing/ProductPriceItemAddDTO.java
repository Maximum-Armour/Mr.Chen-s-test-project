package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 
 * 描述 : “产品定价明细新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月16日 下午2:26:39
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ProductPriceItemAddDTO
 */
@Data
@Schema(description = "【产品定价明细新增】接受参数实体类")
public class ProductPriceItemAddDTO {

    /**
     * 产品定价编码
     */
    @Schema(description = "产品定价编码")
	private String productPriceNo;
	
    /**
     * 产品定价明细编码
     */
    @Schema(description = "产品定价明细编码")
	private String productPriceItemNo;
	
    /**
     * 方案明细编码
     */
	@Length(max = 50, message = "方案明细编码长度不能超过50个字符")
    @Schema(description = "方案明细编码")
	private String schemeItemNo;
	
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
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

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
