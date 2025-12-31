package com.ccit.area.sales.dao.vo.system;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “用户分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:58:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemUserPageListVO
 */
@Data
@Schema(description = "【用户分页列表】返回结果实体类")
public class SystemUserPageListVO {

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
     * 状态：0-正常；1-禁用；2-锁定；
     */
	@Schema(description = "状态：0-正常；1-禁用；2-锁定；")
    private Integer status;
	
	/**
	 * 状态名称
	 */
	@Schema(description = "状态名称")
	private String statusName;
	
	/**
     * 角色名称
     */
	@Schema(description = "角色名称")
	private String roleName;
	
    /**
     * 创建时间
     */
	@Schema(description = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    
    /**
     * 更新时间
     */
	@Schema(description = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
	
}
