package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品档案新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月16日 下午2:06:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : MaterielAddDTO
 */
@Data
@Schema(description = "【产品档案新增】接受参数实体类")
public class MaterielAddDTO {
	
	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

    /**
     * 产品编码
     */
	@Length(max = 50, message = "产品编码长度不能超过50个字符")
	@Schema(description = "产品编码")
    private String materielNo;

    /**
     * 产品名称
     */
	@Length(max = 200, message = "产品名称长度不能超过200个字符")
	@Schema(description = "产品名称")
    private String materielName;

    /**
     * 产品等级
     */
	@Length(max = 50, message = "产品等级长度不能超过50个字符")
	@Schema(description = "产品等级")
    private String materielLevel;

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
     * 单位
     */
	@Length(max = 10, message = "单位长度不能超过10个字符")
	@Schema(description = "单位")
    private String unit;

    /**
     * 生产厂商编码
     */
	@Length(max = 50, message = "生产厂商编码长度不能超过50个字符")
	@Schema(description = "生产厂商编码")
    private String manufacturerCode;

    /**
     * 生产厂商名称
     */
	@Length(max = 200, message = "生产厂商名称长度不能超过200个字符")
	@Schema(description = "生产厂商名称")
    private String manufacturerName;

    /**
     * 执行标准
     */
	@Length(max = 200, message = "执行标准长度不能超过200个字符")
	@Schema(description = "执行标准")
    private String executiveStandard;

    /**
     * 标准号
     */
	@Length(max = 200, message = "标准号不能超过200个字符")
	@Schema(description = "标准号")
    private String standardNumber;

    /**
     * ERP产品编码
     */
	@Length(max = 50, message = "ERP产品编码不能超过50个字符")
	@Schema(description = "ERP产品编码")
    private String erpMaterielNo;

    /**
     * ERP产品名称
     */
	@Length(max = 100, message = "ERP产品名称不能超过100个字符")
	@Schema(description = "ERP产品名称")
    private String erpMaterielName;

    /**
     * ERP产品属性
     */
	@Length(max = 200, message = "ERP产品属性不能超过200个字符")
	@Schema(description = "ERP产品属性")
    private String erpMaterielShowdesc;

    /**
     * 税额
     */
	@Length(max = 20, message = "税额不能超过20个字符")
    @Schema(description = "税额")
    private String tax;

    /**
     * 开票名称
     */
	@Length(max = 50, message = "开票名称不能超过50个字符")
    @Schema(description = "开票名称")
    private String receiptName;

    /**
     * 开票规格
     */
	@Length(max = 200, message = "开票规格不能超过200个字符")
    @Schema(description = "开票规格")
    private String receiptMatrielType;

    /**
     * 组织编码
     */
    @Length(max = 50, message = "组织编码不能超过50个字符")
    @Schema(description = "组织编码")
    private String orgNo;
}
