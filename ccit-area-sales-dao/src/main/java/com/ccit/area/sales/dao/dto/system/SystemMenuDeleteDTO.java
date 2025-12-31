package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “菜单删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:43:08
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMenuDeleteDTO
 */
@Data
@Schema(description = "【菜单删除】接受参数实体类")
public class SystemMenuDeleteDTO {

	/**
	 * 菜单ID数组
	 */
	@Schema(description = "菜单ID数组")
	private List<Long> ids;
	
}
