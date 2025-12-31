package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “用户组织中间”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:51:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemUserOrgPO
 */
@Data
@Accessors(chain = true)
@TableName(value = "t_system_user_org")
public class SystemUserOrgPO {

    /**
     * 用户ID
     * 
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 组织ID
     */
    @TableField(value = "org_id")
    private Long orgId;

    @TableField(value = "display_status")
    private String displayStatus;

    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年10月20日 下午6:04:51
     * 构造方法名 : SystemUserOrgPO()
     * 描述 : 
     */
    public SystemUserOrgPO() {
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
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年10月20日 下午6:05:00
     * 构造方法名 : 用户组织中间实体传入“用户ID、组织ID”参数
     * 参数 :	userId	用户ID
     *		orgId	组织ID
     */
    public SystemUserOrgPO(Long userId, Long orgId) {
    	this.userId = userId;
    	this.orgId = orgId;
    }

}
