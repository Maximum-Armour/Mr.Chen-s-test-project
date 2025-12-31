package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “牌号档案新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:00:52
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SkuFileAddDTO
 */
@Data
@Schema(description = "【牌号档案新增】接受参数实体类")
public class SkuFileAddDTO {

	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;
	
	/**
     * 牌号编码
     */
	@Length(max = 50, message = "牌号编码长度不能超过50个字符")
	@Schema(description = "牌号编码")
    private String skuNo;

    /**
     * 牌号名称
     */
	@Length(max = 200, message = "牌号名称长度不能超过200个字符")
	@Schema(description = "牌号名称")
    private String skuName;

    /**
     * 产品分类编码
     */
	@Length(max = 50, message = "产品分类编码长度不能超过50个字符")
	@Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
	@Length(max = 200, message = "产品分类名称长度不能超过200个字符")
	@Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 主副产品
     */
	@Length(max = 10, message = "主副产品长度不能超过10个字符")
	@Schema(description = "主副产品")
    private String mainOrByproduct;

    /**
     * 执行标准
     */
	@Length(max = 200, message = "执行标准长度不能超过200个字符")
	@Schema(description = "执行标准")
    private String executiveStandard;

    /**
     * 标准号
     */
	@Length(max = 200, message = "标准号长度不能超过200个字符")
	@Schema(description = "标准号")
    private String standardNumber;

	/**
	 * 组织编码
	 */
	@Length(max = 50, message = "组织编码长度不能超过50个字符")
	@Schema(description = "组织编码")
	private String orgNo;
}
