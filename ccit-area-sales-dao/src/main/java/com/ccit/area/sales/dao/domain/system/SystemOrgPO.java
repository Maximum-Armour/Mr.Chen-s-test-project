package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 
 * 描述 : “组织”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:50:34
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemOrgPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_system_org")
public class SystemOrgPO extends BasePO<SystemOrgPO>  {

    private static final long serialVersionUID = 1L;

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
	 * 组织编码简称
	 */
	@TableField(value = "org_no_abbreviation")
	private String orgNoAbbreviation;

	/**
	 * 组织简称
	 */
	@TableField(value = "org_name_abbreviation")
	private String orgNameAbbreviation;

	/**
	 * ERP组织编码
	 */
	@TableField(value = "erp_org_no")
	private String erpOrgNo;

    /**
     * 组织类型（字典编码[orgType]）
     */
    @TableField(value = "org_type")
    private String orgType;
    
    /**
     * 组织排序
     */
    @TableField(value = "org_orders")
    private Integer orgOrders;

    /**
     * 组织序列号
     */
    @TableField(value = "org_seq")
    private String orgSeq;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

	/**
	 * 钉钉部门id
	 */
	@TableField(value = "dingding_dept_id")
	private String dingdingDeptId;

	/**
	 * 钉钉部门名称
	 */
	@TableField(value = "dingding_dept_name")
	private String dingdingDeptName;

    /**
     * 状态：0-正常；1-禁用；
     */
    private Integer status;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:46:32
     * 构造方法名 : SystemOrgPO() 
     * 描述 : 
     */
	public SystemOrgPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:46:41
	 * 构造方法名 : SystemOrgPO(Boolean deleted, Boolean isAutoFillUser)
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemOrgPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月9日 下午2:46:54
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.system
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemOrgPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemOrgPO> wrapper() {
		LambdaQueryWrapper<SystemOrgPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemOrgPO::getDeleted, 0);
		return wrapper;
	}

}
