package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “菜单树列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月15日 下午9:27:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMenuTreeDTO
 */
@Data
@Schema(description = "【菜单树列表】接受参数实体类")
public class SystemMenuTreeDTO {
	
	/**
     * 菜单名称
     */
	@Schema(description = "菜单名称")
    private String menuName;
	
    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;

}
