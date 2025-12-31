package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 描述 : “角色菜单中间”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:34:42
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemRoleMenuPO
 */
@Data
@TableName(value = "t_system_role_menu")
public class SystemRoleMenuPO {

    /**
     * 角色ID
     */
    @TableId(value = "role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    private Long menuId;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 下午5:35:01
     * 构造方法名 : SystemRoleMenuPO() 
     * 描述 : 
     */
    public SystemRoleMenuPO() {
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 下午5:35:13
     * 构造方法名 : SystemRoleMenuPO(Long roleId, Long menuId) 
     * 描述 : 角色菜单中间实体传入“角色ID、菜单ID”参数
     * 参数 :	roleId	角色ID
     *		menuId	菜单ID
     */
    public SystemRoleMenuPO(Long roleId, Long menuId) {
    	this.roleId = roleId;
    	this.menuId = menuId;
    }

}
