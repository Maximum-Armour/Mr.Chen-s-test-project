package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “角色详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:15:15
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemRoleDetailsVO
 */
@Data
@Schema(description = "【角色详情】返回结果实体类")
public class SystemRoleDetailsVO {

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
	
}
