package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “修改个人密码”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:10:55
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMyPwdDTO
 */
@Data
@Schema(description = "【修改个人密码】接受参数实体类")
public class SystemMyPwdDTO {

	/**
	 * 原密码
	 */
	@NotBlank(message = "原密码不能为空")
	@Schema(description = "原密码", requiredMode = RequiredMode.REQUIRED)
	private String oldPwd;
	
	/**
	 * 新密码
	 */
	@NotBlank(message = "新密码不能为空")
	@Schema(description = "新密码", requiredMode = RequiredMode.REQUIRED)
	private String newPwd;
	
	/**
	 * 确认密码
	 */
	@NotBlank(message = "确认密码不能为空")
	@Schema(description = "确认密码", requiredMode = RequiredMode.REQUIRED)
	private String confirmPwd;
	
}
