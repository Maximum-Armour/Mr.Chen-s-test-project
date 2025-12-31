package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “用户解锁”DTO
 * 创建人 : yn
 * 创建时间 : 2024年11月1日 下午3:11:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemUserUnlockDTO
 */
@Data
@Schema(description = "【用户解锁】接受参数实体类")
public class SystemUserUnlockDTO {

	/**
	 * 用户ID数组
	 */
	@Schema(description = "用户ID数组")
	private List<Long> ids;
	
}
