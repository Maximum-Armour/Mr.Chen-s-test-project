package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “参数配置删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:29:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemConfigDeleteDTO
 */
@Data
@Schema(description = "【参数配置删除】接受参数实体类")
public class SystemConfigDeleteDTO {

	/**
	 * 参数配置ID数组
	 */
	@Schema(description = "参数配置ID数组")
	private List<Long> ids;
	
}
