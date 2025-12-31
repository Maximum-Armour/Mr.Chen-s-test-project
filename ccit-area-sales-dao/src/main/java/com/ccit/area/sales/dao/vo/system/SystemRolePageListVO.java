package com.ccit.area.sales.dao.vo.system;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “角色分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:15:02
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemRolePageListVO
 */
@Data
@Schema(description = "【角色分页列表】返回结果实体类")
public class SystemRolePageListVO {

	/**
     * 角色ID
     */
	@Schema(description = "角色ID")
    private Long id;
	
	/**
     * 角色名称
     */
	@Schema(description = "角色名称")
    private String roleName;

    /**
     * 备注
     */
	@Schema(description = "备注")
    private String remark;

    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
    /**
     * 状态名称
     */
	@Schema(description = "状态名称")
    private String statusName;
	
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

	/**
	 * 组织编码
	 */
	@Schema(description = "组织编码")
	private String orgNo;

	/**
	 * 组织名称
	 */
	@Schema(description = "组织名称")
	private String orgName;

}
