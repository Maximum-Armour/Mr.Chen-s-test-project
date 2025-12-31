package com.ccit.area.sales.dao.domain.system;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “参数配置”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:45:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemConfigPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_system_config")
public class SystemConfigPO extends BasePO<SystemConfigPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 参数名称
     */
    @TableField(value = "config_name")
    private String configName;

    /**
     * 参数键名
     */
    @TableField(value = "config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @TableField(value = "config_value")
    private String configValue;

    /**
     * 系统内置：Y-是；N-否；
     */
    @TableField(value = "config_type")
    private String configType;

    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:22:56
     * 构造方法名 : SystemConfigPO()
     * 描述 : 
     */
	public SystemConfigPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:23:06
	 * 构造方法名 : SystemConfigPO(Boolean deleted, Boolean isAutoFillUser) 
	 * 描述 : 处理逻辑删除
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SystemConfigPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月9日 下午2:23:21
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.system
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SystemConfigPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SystemConfigPO> wrapper() {
		LambdaQueryWrapper<SystemConfigPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemConfigPO::getDeleted, 0);
		return wrapper;
	}
    
}
