package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “个人中心菜单详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年10月24日 下午4:54:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemPersonalMenuVO
 */
@Data
@Schema(description = "【个人中心菜单详情】返回结果实体类")
public class SystemPersonalMenuVO {

	/**
     * 菜单路由
     */
    @Schema(description = "菜单路由")
    private String menuRouter;
    
    /**
     * 菜单地址
     */
    @Schema(description = "菜单地址")
    private String menuPath;
    
    /**
     * 菜单展示类型：0-默认；1-弹窗；2-穿透；
     */
	@Schema(description = "菜单展示类型：0-默认；1-弹窗；2-穿透；")
    private Integer menuShowType;
	
}
