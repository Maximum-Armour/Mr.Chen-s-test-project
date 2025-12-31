package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “角色分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:13:39
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemRolePageListDTO
 */
@Data
@Schema(description = "【角色分页列表】接受参数实体类")
public class SystemRolePageListDTO {
	
	/**
     * 角色ID
     */
	@Schema(description = "角色ID")
    private String id;
	
	/**
     * 角色名称
     */
	@Schema(description = "角色名称")
    private String roleName;

    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
}
