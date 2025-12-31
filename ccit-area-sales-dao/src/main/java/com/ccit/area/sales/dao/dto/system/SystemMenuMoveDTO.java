package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “菜单移动”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:42:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMenuMoveDTO
 */
@Data
@Schema(description = "【菜单移动】接受参数实体类")
public class SystemMenuMoveDTO {
	
	/**
	 * 移动位置：正数节点向上移动；负数节点向下移动；
	 */
	@NotNull(message = "移动位置不能为空")
	@Schema(description = "移动位置：正数节点向上移动；负数节点向下移动；", requiredMode = RequiredMode.REQUIRED)
	private Integer moveNum;
	
    /**
     * 菜单ID
     */
	@NotNull(message = "菜单ID不能为空")
	@Schema(description = "菜单ID", requiredMode = RequiredMode.REQUIRED)
    private Long id;

}
