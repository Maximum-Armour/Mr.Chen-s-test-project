package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品档案分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月16日 下午3:14:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : MaterielPageListVO
 */
@Data
@Schema(description = "【产品档案分页列表】返回结果实体类")
public class MaterielPageListVO {

	/**
     * 产品档案ID
     */
	@Schema(description = "产品档案ID")
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
     * 产品等级
     */
	@Schema(description = "产品等级")
    private String materielLevel;

    /**
     * 产品等级名称
     */
    @Schema(description = "产品等级名称")
    private String materielLevelName;

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
     * 单位
     */
	@Schema(description = "单位")
    private String unit;

    /**
     * 单位名称
     */
    @Schema(description = "单位名称")
    private String unitName;

    /**
     * 生产厂商编码
     */
	@Schema(description = "生产厂商编码")
    private String manufacturerCode;

    /**
     * 生产厂商名称
     */
	@Schema(description = "生产厂商名称")
    private String manufacturerName;

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
     * ERP产品编码
     */
	@Schema(description = "ERP产品编码")
    private String erpMaterielNo;

    /**
     * ERP产品名称
     */
	@Schema(description = "ERP产品名称")
    private String erpMaterielName;

    /**
     * ERP产品属性
     */
	@Schema(description = "ERP产品属性")
    private String erpMaterielShowdesc;

    /**
     * 税额
     */
    @Schema(description = "税额")
    private String tax;

    /**
     * 开票名称
     */
    @Schema(description = "开票名称")
    private String receiptName;

    /**
     * 开票规格
     */
    @Schema(description = "开票规格")
    private String receiptMatrielType;
    
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
