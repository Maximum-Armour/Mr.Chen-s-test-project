package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * 描述 : “用户角色中间”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:51:48
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemUserRolePO
 */
@Data
@TableName(value = "t_system_user_role")
public class SystemUserRolePO {

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年6月10日 下午5:23:27
     * 构造方法名 : SystemUserRolePO() 
     * 描述 : 
     */
    public SystemUserRolePO() {
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年6月10日 下午5:23:35
     * 构造方法名 : SystemUserRolePO(Long userId, Long roleId) 
     * 描述 : 用户角色中间实体传入“用户ID、角色ID”参数
     * 参数 :	userId	用户ID
     *		roleId	角色ID
     */
    public SystemUserRolePO(Long userId, Long roleId) {
    	this.userId = userId;
    	this.roleId = roleId;
    }

}
