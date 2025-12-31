package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月9日 下午3:04:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.marketing
 * 类名 : PriceSchemePageListVO
 */
@Data
@Schema(description = "【定价方案分页列表】返回结果实体类")
public class PriceSchemePageListVO {
	
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
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 工作流编码
     */
    @Schema(description = "工作流编码")
    private String workflowid;
    
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

    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String companyNo;

}
