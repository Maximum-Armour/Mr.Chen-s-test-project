package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “字典删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:29:07
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemDictDeleteDTO
 */
@Data
@Schema(description = "【字典删除】接受参数实体类")
public class SystemDictDeleteDTO {

	/**
	 * 字典ID数组
	 */
	@Schema(description = "字典ID")
	private Long id;
	
}
