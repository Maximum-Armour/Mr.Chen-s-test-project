package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “牌号档案分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月26日 上午10:18:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SkuFilePageListDTO
 */
@Data
@Schema(description = "【牌号档案分页列表】接受参数实体类")
public class SkuFilePageListDTO {
	
	/**
     * 牌号ID
     */
	@Schema(description = "牌号ID")
    private Long id;
	
	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;
	
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
     * 主副产品
     */
	@Schema(description = "主副产品")
    private String mainOrByproduct;

    /**
     * 执行标准
     */
	@Schema(description = "执行标准")
    private String executiveStandard;

    /**
     * 标准号
     */
	@Schema(description = "标准号")
    private String standardNumber;
	
	/**
     * 组织编码
     */
	@Schema(description = "组织编码")
    private String orgNo;

    /**
     * 组织名称
     */
	@Schema(description = "组织名称")
    private String orgName;

    /**
     * 部门编码
     */
	@Schema(description = "部门编码")
    private String deptNo;

    /**
     * 部门名称
     */
	@Schema(description = "部门名称")
    private String deptName;

}
