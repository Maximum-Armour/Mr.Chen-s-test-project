package com.ccit.area.sales.dao.vo.system;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “当前登录用户菜单”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:17:33
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemCurrentUserMenuVO
 */
@Data
@Schema(description = "【当前登录用户菜单】返回结果实体类")
public class SystemCurrentUserMenuVO {

	/**
	 * 菜单ID
	 */
	@Schema(description = "菜单ID")
	private Long id;

	/**
	 * 菜单图标
	 */
	@Schema(description = "菜单图标")
	private String menuIcon;

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
     * 父级ID
     */
	@Schema(description = "父级ID")
    private Long parentId;
	
    /**
     * 菜单类型：1-目录；2-菜单；3-按钮；
     */
	@Schema(description = "菜单类型：1-目录；2-菜单；3-按钮；")
    private Integer menuType;
	
    /**
     * 菜单展示类型：0-默认；1-弹窗；2-穿透；
     */
	@Schema(description = "菜单展示类型：0-默认；1-弹窗；2-穿透；")
    private Integer menuShowType;
	
	/**
	 * 是否隐藏菜单
	 */
	@Schema(description = "是否隐藏菜单")
	private Boolean menuHidden = false;
	
	/**
     * 是否有叶子节点
     */
	@Schema(description = "是否有叶子节点")
    private Boolean menuIsLeaf = false;
    
    /**
     * 子菜单集合
     */
	@Schema(description = "子菜单集合")
    private List<SystemCurrentUserMenuVO> children = new ArrayList<>();
	
}
