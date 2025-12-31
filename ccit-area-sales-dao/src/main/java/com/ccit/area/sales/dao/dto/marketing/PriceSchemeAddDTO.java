package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * 描述 : “定价方案新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月9日 下午2:56:19
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemeAddDTO
 */
@Data
@Schema(description = "【定价方案新增】接受参数实体类")
public class PriceSchemeAddDTO {

	/**
     * 方案编码
     */
	@Length(max = 50, message = "方案编码长度不能超过50个字符")
    @Schema(description = "方案编码")
    private String schemeNo;

    /**
     * 方案名称
     */
	@Length(max = 50, message = "方案名称长度不能超过50个字符")
    @Schema(description = "方案名称")
    private String schemeName;

    /**
     * 产品分类编码
     */
	@Length(max = 50, message = "产品分类编码长度不能超过50个字符")
    @Schema(description = "产品分类编码")
    private String categoriesNo;
	
    /**
     * 产品分类编码
     */
	@Length(max = 50, message = "产品分类编码长度不能超过50个字符")
    @Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
	@Length(max = 50, message = "产品分类名称长度不能超过50个字符")
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 贸易类型
     */
	@Length(max = 50, message = "贸易类型长度不能超过50个字符")
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 组织编码
     */
    @Length(max = 50, message = "组织编码长度不能超过50个字符")
    @Schema(description = "组织编码")
    private String orgNo;
	
	/**
	 * 定价方案明细数组
	 */
	@NotNull(message = "请新增定价方案明细！")
	@Schema(description = "定价方案明细数组")
	private List<PriceSchemeItemAddDTO> itemAddList;
	
}
