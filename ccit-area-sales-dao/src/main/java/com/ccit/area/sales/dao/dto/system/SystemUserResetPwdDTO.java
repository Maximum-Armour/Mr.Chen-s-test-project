package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “重置密码”DTO
 * 创建人 : yn
 * 创建时间 : 2024年11月1日 下午3:11:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemUserResetPwdDTO
 */
@Data
@Schema(description = "【重置密码】接受参数实体类")
public class SystemUserResetPwdDTO {

	/**
	 * 用户ID数组
	 */
	@Schema(description = "用户ID数组")
	private List<Long> ids;
	
}
