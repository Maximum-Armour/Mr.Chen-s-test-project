package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 * 描述 : “字典”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:11:58
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemDictPO
 */
@Data
@TableName(value = "t_system_dict")
@EqualsAndHashCode(callSuper = true)
public class SystemDictPO extends BasePO<SystemDictPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典值
     */
    @TableField(value = "dict_value")
    private String dictValue;

    /**
     * 字典排序
     */
    @TableField(value = "dict_orders")
    private Integer dictOrders;

    /**
     * 字典序列号
     */
    @TableField(value = "dict_seq")
    private String dictSeq;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
    private Integer status;

	@TableField("is_deleted")
	private int isDeleted;

    /**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:30:58
	 * 构造方法名 : SystemDictPO()
	 * 描述 : 
	 */
	public SystemDictPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:31:48
	 * 构造方法名 : SystemDictPO(Boolean deleted, Boolean isAutoFillUser) 
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemDictPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 *  LambdaQueryWrapper<SystemDictPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemDictPO> wrapper() {
		LambdaQueryWrapper<SystemDictPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemDictPO::getDeleted, 0);
		return wrapper;
	}

}
