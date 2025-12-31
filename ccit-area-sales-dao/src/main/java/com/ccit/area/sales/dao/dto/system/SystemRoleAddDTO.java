package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “角色新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:14:14
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemRoleAddDTO
 */
@Data
@Schema(description = "【角色新增】接受参数实体类")
public class SystemRoleAddDTO {

	/**
     * 角色名称
     */
	@NotBlank(message = "角色名称不能为空")
	@Length(max = 50, message = "角色名称长度不能超过50个字符")
	@Schema(description = "角色名称", requiredMode = RequiredMode.REQUIRED)
    private String roleName;

    /**
     * 备注
     */
	@Length(max = 500, message = "备注长度不能超过500个字符")
	@Schema(description = "备注")
    private String remark;
	
    /**
     * 状态：0-正常；1-禁用；
     */
	@NotNull(message = "状态不能为空")
	@Schema(description = "状态：0-正常；1-禁用；", requiredMode = RequiredMode.REQUIRED)
    private String status;
	
}
