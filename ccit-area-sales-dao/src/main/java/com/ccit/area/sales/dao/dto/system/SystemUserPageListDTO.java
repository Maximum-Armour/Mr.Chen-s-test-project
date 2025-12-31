package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “用户分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:56:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemUserPageListDTO
 */
@Data
@Schema(description = "【用户分页列表】接受参数实体类")
public class SystemUserPageListDTO {
	
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
     * 手机号
     */
	@Schema(description = "手机号")
    private String mobile;

    /**
     * 状态：0-正常；1-禁用；2-锁定；
     */
	@Schema(description = "状态状态：0-正常；1-禁用；2-锁定；")
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
	private Long orgId;
	
}
