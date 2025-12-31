package com.ccit.area.sales.dao.domain.system;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 描述 : “菜单”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:32:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemMenuPO
 */
@Data
@TableName(value = "t_system_menu")
@EqualsAndHashCode(callSuper = true)
public class SystemMenuPO extends BasePO<SystemMenuPO> {

    private static final long serialVersionUID = 1L;
    
    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

	/**
	 * 菜单路由
	 */
	@TableField(value = "menu_router", updateStrategy = FieldStrategy.IGNORED)
	private String menuRouter;

    /**
     * 菜单地址
     */
    @TableField(value = "menu_path", updateStrategy = FieldStrategy.IGNORED)
    private String menuPath;
    
    /**
     * 菜单类型：1-目录；2-菜单；3-按钮；
     */
    @TableField(value = "menu_type")
    private Integer menuType;
    
    /**
     * 菜单图标
     */
    @TableField(value = "menu_icon", updateStrategy = FieldStrategy.IGNORED)
    private String menuIcon;
    
    /**
     * 菜单权限
     */
    @TableField(value = "menu_purview")
    private String menuPurview;

    /**
     * 菜单排序
     */
    @TableField(value = "menu_orders")
    private Integer menuOrders;
    
    /**
     * 菜单序列号
     */
    @TableField(value = "menu_seq")
    private String menuSeq;
    
    /**
     * 是否显示菜单：0-否；1-是；
     */
    @TableField(value = "menu_hidden")
    private Integer menuHidden;
    
    /**
     * 菜单展示类型：0-默认；1-弹窗；2-穿透；
     */
    @TableField(value = "menu_show_type")
    private Integer menuShowType;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
    private Integer status;
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:30:58
	 * 构造方法名 : SystemMenuPO()
	 * 描述 : 
	 */
	public SystemMenuPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:31:48
	 * 构造方法名 : SystemMenuPO(Boolean deleted, Boolean isAutoFillUser) 
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemMenuPO(Boolean deleted, Boolean isAutoFillUser) {
    	super.setDeleted(deleted ? 1 : 0);
    	super.setDeletedTime(new Date());
    	if (isAutoFillUser) {
    		super.setDeletedByName(null);
    		super.setDeletedTime(null);
    	}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:32:15
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.system
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemMenuPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemMenuPO> wrapper() {
		LambdaQueryWrapper<SystemMenuPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemMenuPO::getDeleted, 0);
		return wrapper;
	}

}
