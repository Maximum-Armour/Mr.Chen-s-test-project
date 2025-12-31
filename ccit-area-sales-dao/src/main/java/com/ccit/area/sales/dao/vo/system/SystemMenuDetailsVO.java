package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “菜单详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:43:40
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemMenuDetailsVO
 */
@Data
@Schema(description = "【菜单详情】返回结果实体类")
public class SystemMenuDetailsVO {

	/**
     * 菜单ID
     */
	@Schema(description = "菜单ID")
    private Long id;
	
	/**
     * 菜单名称
     */
	@Schema(description = "菜单名称")
    private String menuName;

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
     * 菜单类型：1-目录；2-菜单；3-按钮；
     */
	@Schema(description = "菜单类型：1-目录；2-菜单；3-按钮；")
    private Integer menuType;
	
    /**
     * 菜单图标
     */
	@Schema(description = "菜单图标")
    private String menuIcon;

    /**
     * 菜单权限
     */
	@Schema(description = "菜单权限")
    private String menuPurview;
	
    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
    /**
     * 菜单排序
     */
	@Schema(description = "菜单排序")
    private Integer menuOrders;
	
    /**
     * 是否显示菜单：0-否；1-是；
     */
	@Schema(description = "是否显示菜单：0-否；1-是；")
    private Integer menuHidden;
	
    /**
     * 菜单展示类型：0-默认；1-弹窗；2-穿透；
     */
	@Schema(description = "菜单展示类型：0-默认；1-弹窗；2-穿透；")
    private Integer menuShowType;
	
}
