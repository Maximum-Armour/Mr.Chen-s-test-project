package com.ccit.area.sales.dao.domain.system;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 描述 : “角色”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:33:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemRolePO
 */
@Data
@TableName(value = "t_system_role")
@EqualsAndHashCode(callSuper = true)
public class SystemRolePO extends BasePO<SystemRolePO> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：0-正常；1-禁用；
     */
    private Integer status;

	/**
	 * 组织编码
	 */
	@TableField(value = "org_no")
	private String orgNo;

	/**
	 * 组织名称
	 */
	@TableField(value = "org_name")
	private String orgName;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 下午5:33:58
     * 构造方法名 : SystemRolePO() 
     * 描述 : 
     */
	public SystemRolePO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:34:05
	 * 构造方法名 : SystemRolePO(Boolean deleted, Boolean isAutoFillUser)
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemRolePO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月8日 下午5:34:22
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.system
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemRolePO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemRolePO> wrapper() {
		LambdaQueryWrapper<SystemRolePO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemRolePO::getDeleted, 0);
		return wrapper;
	}
	
}
