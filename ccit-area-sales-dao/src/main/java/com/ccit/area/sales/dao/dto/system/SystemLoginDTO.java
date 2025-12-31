package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “登录”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午11:14:02
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemLoginDTO
 */
@Data
@Schema(description = "【登录】接受参数实体类")
public class SystemLoginDTO {

	/**
     * 账号
     */
	@NotBlank(message = "账号不能为空")
	@Schema(description = "账号", requiredMode = RequiredMode.REQUIRED)
    private String username;

    /**
     * 密码
     */
	@NotBlank(message = "密码不能为空")
	@Schema(description = "密码", requiredMode = RequiredMode.REQUIRED)
    private String password;
    
	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	@Schema(description = "验证码", requiredMode = RequiredMode.REQUIRED)
    private String verifyCode;
	
	/**
	 * 验证码私钥
	 */
	@NotBlank(message = "验证码私钥不能为空")
	@Schema(description = "验证码私钥", requiredMode = RequiredMode.REQUIRED)
    private String verifyCodeKey;
	
}
