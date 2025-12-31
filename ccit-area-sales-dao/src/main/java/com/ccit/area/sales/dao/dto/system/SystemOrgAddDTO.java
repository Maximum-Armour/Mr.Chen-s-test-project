package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * 描述 : “组织新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:48:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemOrgAddDTO
 */
@Data
@Schema(description = "【组织新增】接受参数实体类")
public class SystemOrgAddDTO {

	/**
     * 组织编码
     */
	@Length(max = 50, message = "组织编码长度不能超过50个字符")
	@Schema(description = "组织编码", requiredMode = RequiredMode.REQUIRED)
    private String orgNo;

    /**
     * 组织名称
     */
	@NotBlank(message = "组织名称不能为空")
	@Length(max = 100, message = "组织名称长度不能超过100个字符")
	@Schema(description = "组织名称", requiredMode = RequiredMode.REQUIRED)
    private String orgName;

	/**
	 * 组织编码简称
	 */
	@NotBlank(message = "组织编码简称不能为空")
	@Length(max = 100, message = "组织编码简称长度不能超过50个字符")
	@Schema(description = "组织编码简称", requiredMode = RequiredMode.REQUIRED)
	private String orgNoAbbreviation;

	/**
	 * 组织简称
	 */
	@Length(max = 100, message = "组织简称长度不能超过100个字符")
	@Schema(description = "组织简称", requiredMode = RequiredMode.REQUIRED)
	private String orgNameAbbreviation;

	/**
	 * ERP组织编码
	 */
	@Length(max = 100, message = "ERP组织编码长度不能超过50个字符")
	@Schema(description = "ERP组织编码", requiredMode = RequiredMode.REQUIRED)
	private String erpOrgNo;

	/**
	 * 钉钉部门id
	 */
	@Length(max = 50, message = "钉钉部门长度不能超过50个字符")
	@Schema(description = "钉钉部门id", requiredMode = RequiredMode.REQUIRED)
	private String dingdingDeptId;

	/**
	 * 钉钉部门名称
	 */
	@Length(max = 100, message = "钉钉部门名称长度不能超过100个字符")
	@Schema(description = "钉钉部门名称", requiredMode = RequiredMode.REQUIRED)
	private String dingdingDeptName;
	
    /**
     * 组织类型
     */
	@NotBlank(message = "组织类型不能为空")
	@Schema(description = "组织类型，从字典获取，字典编码为[orgType]", requiredMode = RequiredMode.REQUIRED)
    private String orgType;

    /**
     * 组织排序
     */
	@Schema(description = "组织排序")
    private Integer orgOrders;

    /**
     * 上级ID
     */
	@NotNull(message = "上级ID不能为空")
	@Schema(description = "上级ID", requiredMode = RequiredMode.REQUIRED)
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
    @NotNull(message = "状态不能为空")
	@Schema(description = "状态：0-正常；1-禁用；", requiredMode = RequiredMode.REQUIRED)
    private Integer status;
	
}
