package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “组织删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:43:59
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemOrgDeleteDTO
 */
@Data
@Schema(description = "【组织删除】接受参数实体类")
public class SystemOrgDeleteDTO {

	/**
	 * 组织ID数组
	 */
	@Schema(description = "组织ID数组")
	private List<Long> ids;
	
}
