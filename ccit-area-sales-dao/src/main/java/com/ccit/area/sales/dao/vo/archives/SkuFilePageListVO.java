package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “牌号档案列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:07:15
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : SkuFilePageListVO
 */
@Data
@Schema(description = "【牌号档案列表】返回结果实体类")
public class SkuFilePageListVO {

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
     * 状态名称
     */
	@Schema(description = "状态名称")
    private String statusName;
	
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
     * 主副产品名称
     */
	@Schema(description = "主副产品名称")
    private String mainOrByproductName;

    /**
     * 执行标准
     */
	@Schema(description = "执行标准")
    private String executiveStandard;
	
    /**
     * 执行标准名称
     */
	@Schema(description = "执行标准名称")
    private String executiveStandardName;

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
