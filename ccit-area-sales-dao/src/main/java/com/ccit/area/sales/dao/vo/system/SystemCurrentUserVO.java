package com.ccit.area.sales.dao.vo.system;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “当前登录用户”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午4:37:02
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemCurrentUserVO
 */
@Data
@Schema(description = "【当前登录用户】返回结果实体类")
public class SystemCurrentUserVO {

	/**
     * 用户账号
     */
	@Schema(description = "用户账号")
    private String userName;
	
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
	
	/**
	 * 权限数组
	 */
	@Schema(description = "权限数组")
	private List<String> purview = new ArrayList<>();
	
}
