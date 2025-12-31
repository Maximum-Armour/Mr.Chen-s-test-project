package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “修改个人信息”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:16:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMyInfoDTO
 */
@Data
@Schema(description = "【修改个人信息】接受参数实体类")
public class SystemMyInfoDTO {

	/**
     * 用户姓名
     */
	@Schema(description = "用户姓名")
    private String realName;

    /**
     * 用户头像
     */
	@Schema(description = "用户头像")
    private String userPhoto;

    /**
     * 邮箱
     */
	@Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
	@Schema(description = "手机号")
    private String mobile;

    /**
     * 个性签名
     */
	@Schema(description = "个性签名")
    private String signature;
	
}
