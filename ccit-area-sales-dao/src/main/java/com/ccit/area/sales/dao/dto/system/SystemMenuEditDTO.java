package com.ccit.area.sales.dao.dto.system;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

/**
 * 
 * 描述 : “菜单修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午8:42:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemMenuEditDTO
 */
@Data
@Schema(description = "【菜单修改】接受参数实体类")
public class SystemMenuEditDTO {
	
	/**
     * 菜单ID
     */
	@NotNull(message = "菜单ID不能为空")
	@Schema(description = "菜单ID", requiredMode = RequiredMode.REQUIRED)
    private Long id;

	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	@Length(max = 50, message = "菜单名称长度不能超过50个字符")
	@Schema(description = "菜单名称", requiredMode = RequiredMode.REQUIRED)
	private String menuName;

	/**
	 * 菜单路由
	 */
	@Length(max = 200, message = "菜单路由长度不能超过200个字符")
	@Schema(description = "菜单路由", requiredMode = RequiredMode.REQUIRED)
	private String menuRouter;

    /**
     * 菜单地址
     */
	@Length(max = 200, message = "菜单地址长度不能超过200个字符")
	@Schema(description = "菜单地址", requiredMode = RequiredMode.REQUIRED)
    private String menuPath;

    /**
     * 菜单类型：1-目录；2-菜单；3-按钮；
     */
	@NotNull(message = "菜单类型不能为空")
	@Schema(description = "菜单类型：1-目录；2-菜单；3-按钮；", requiredMode = RequiredMode.REQUIRED)
    private Integer menuType;
	
    /**
     * 菜单图标
     */
	@Length(max = 200, message = "菜单图标长度不能超过200个字符")
	@Schema(description = "菜单图标")
    private String menuIcon;
	
    /**
     * 菜单权限
     */
	@Length(max = 100, message = "菜单权限长度不能超过100个字符")
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
	@NotNull(message = "状态不能为空")
	@Schema(description = "状态：0-正常；1-禁用；", requiredMode = RequiredMode.REQUIRED)
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
