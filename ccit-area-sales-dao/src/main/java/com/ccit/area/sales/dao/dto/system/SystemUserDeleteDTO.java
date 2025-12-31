package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “用户删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:57:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemUserDeleteDTO
 */
@Data
@Schema(description = "【用户删除】接受参数实体类")
public class SystemUserDeleteDTO {

	/**
	 * 用户ID数组
	 */
	@Schema(description = "用户ID数组")
	private List<Long> ids;
	
}
