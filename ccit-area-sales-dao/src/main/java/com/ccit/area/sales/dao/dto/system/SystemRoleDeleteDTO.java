package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “角色删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月19日 下午5:04:56
 * 版本 : 1.0
 * 包名 : 
 * 类名 : SystemRoleDeleteDTO
 */
@Data
@Schema(description = "【角色删除】接受参数实体类")
public class SystemRoleDeleteDTO {

	/**
	 * 角色ID数组
	 */
	@Schema(description = "角色ID数组")
	private List<Long> ids;
	
}
