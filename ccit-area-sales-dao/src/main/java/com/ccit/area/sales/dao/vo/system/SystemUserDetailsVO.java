package com.ccit.area.sales.dao.vo.system;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “用户详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:58:44
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemUserDetailsVO
 */
@Data
@Schema(description = "【用户详情】返回结果实体类")
public class SystemUserDetailsVO {

	/**
     * 用户ID
     */
	@Schema(description = "用户ID")
    private Long id;
	
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
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private Long roleId;
	
	/**
	 * 组织ID
	 */
	@Schema(description = "组织ID")
	private List<Long> orgIds;

	/**
	 * 钉钉用户ID
	 */
	@Schema(description = "钉钉用户ID")
	private String dingdingUserId;
	
}
