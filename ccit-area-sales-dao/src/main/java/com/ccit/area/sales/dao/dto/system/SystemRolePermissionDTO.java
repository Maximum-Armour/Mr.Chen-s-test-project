package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : (用一句话描述该文件做什么)
 * 创建人 : yn
 * 创建时间 : 2024年10月9日 下午2:52:22
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemRolePermissionDTO
 */
@Data
@Schema(description = "【保存角色权限】接受参数实体类")
public class SystemRolePermissionDTO {
	
	/**
     * 角色ID
     */
	@NotNull(message = "角色ID不能为空，请进行检查！")
	@Schema(description = "角色ID", requiredMode = RequiredMode.REQUIRED)
    private Long id;
	
	/**
	 * 菜单ID数组
	 */
	@NotNull(message = "菜单ID数组不能为空，请进行检查！")
	@Schema(description = "菜单ID数组", requiredMode = RequiredMode.REQUIRED)
	private List<Long> menuIds;

}
