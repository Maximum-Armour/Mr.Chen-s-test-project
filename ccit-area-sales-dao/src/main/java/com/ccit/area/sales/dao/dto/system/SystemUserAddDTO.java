package com.ccit.area.sales.dao.dto.system;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “用户新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:57:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemUserAddDTO
 */
@Data
@Schema(description = "【用户新增】接受参数实体类")
public class SystemUserAddDTO {
	
	/**
     * 用户账号
     */
	@NotBlank(message = "用户账号不能为空")
	@Length(max = 20, message = "用户账号长度不能超过20个字符")
	@Schema(description = "用户账号", requiredMode = RequiredMode.REQUIRED)
    private String userName;

    /**
     * 用户姓名
     */
	@Length(max = 100, message = "用户姓名长度不能超过100个字符")
	@Schema(description = "用户姓名,不填写默认生成")
	private String realName;

    /**
     * 用户头像
     */
	@Length(max = 200, message = "用户头像长度不能超过200个字符")
	@Schema(description = "用户头像")
    private String userPhoto;

    /**
     * 邮箱
     */
	@Length(max = 50, message = "邮箱长度不能超过50个字符")
	@Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
	@Length(max = 11, message = "手机号长度不能超过11个字符")
	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "请输入正确的手机号")
	@Schema(description = "手机号")
    private String mobile;

    /**
     * 个性签名
     */
	@Length(max = 500, message = "个性签名长度不能超过500个字符")
	@Schema(description = "个性签名")
    private String signature;

    /**
     * 状态：0-正常；1-禁用；
     */
	@NotNull(message = "状态不能为空")
	@Schema(description = "状态：0-正常；1-禁用；", requiredMode = RequiredMode.REQUIRED)
    private Integer status;
	
	/**
	 * 角色ID
	 */
	@NotNull(message = "角色ID不能为空")
	@Schema(description = "角色ID", requiredMode = RequiredMode.REQUIRED)
	private Long roleId;
	
	/**
	 * 组织ID
	 */
	@NotEmpty(message = "组织ID不能为空")
	@Schema(description = "组织ID", requiredMode = RequiredMode.REQUIRED)
	private List<Long> orgIds;
	
}
