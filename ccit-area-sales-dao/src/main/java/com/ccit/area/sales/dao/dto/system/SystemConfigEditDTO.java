package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “参数配置修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:27:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemConfigEditDTO
 */
@Data
@Schema(description = "【参数配置修改】接受参数实体类")
public class SystemConfigEditDTO {

	/**
     * 参数配置ID
     */
	@NotNull(message = "参数配置ID不能为空")
	@Schema(description = "参数配置ID", requiredMode = RequiredMode.REQUIRED)
    private Long id;
	
	/**
     * 参数名称
     */
	@NotBlank(message = "参数名称不能为空")
	@Length(max = 100, message = "参数名称长度不能超过100个字符")
	@Schema(description = "参数名称", requiredMode = RequiredMode.REQUIRED)
    private String configName;

    /**
     * 参数键名
     */
	@NotBlank(message = "参数键名不能为空")
	@Length(max = 100, message = "参数键名长度不能超过100个字符")
	@Schema(description = "参数键名", requiredMode = RequiredMode.REQUIRED)
    private String configKey;

    /**
     * 参数键值
     */
	@NotBlank(message = "参数键值不能为空")
	@Length(max = 500, message = "参数键值长度不能超过500个字符")
	@Schema(description = "参数键值", requiredMode = RequiredMode.REQUIRED)
    private String configValue;

    /**
     * 系统内置：Y-是；N-否；
     */
	@NotBlank(message = "系统内置不能为空")
	@Schema(description = "系统内置：Y-是；N-否；", requiredMode = RequiredMode.REQUIRED)
    private String configType;
	
}
