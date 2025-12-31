package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * 描述 : “字典修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:28:33
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemDictEditDTO
 */
@Data
@Schema(description = "【字典修改】接受参数实体类")
public class SystemDictEditDTO {
	
	/**
     * 字典ID
     */
	@NotNull(message = "字典ID不能为空")
	@Schema(description = "字典ID", requiredMode = RequiredMode.REQUIRED)
    private Long id;

	/**
	 * 字典编码
	 */
	@NotBlank(message = "字典编码不能为空")
	@Length(max = 50, message = "字典编码长度不能超过50个字符")
	@Schema(description = "字典编码", requiredMode = RequiredMode.REQUIRED)
	private String dictCode;

    /**
     * 字典名称
     */
	@NotBlank(message = "字典名称不能为空")
	@Length(max = 50, message = "字典名称长度不能超过50个字符")
	@Schema(description = "字典名称", requiredMode = RequiredMode.REQUIRED)
    private String dictName;

    /**
     * 字典值
     */
	@Length(max = 100, message = "字典值长度不能超过100个字符")
	@Schema(description = "字典值")
    private String dictValue;

    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
	@NotNull(message = "状态不能为空")
	@Schema(description = "状态：0-正常；1-禁用；", requiredMode = RequiredMode.REQUIRED)
    private Integer status;
	
}
