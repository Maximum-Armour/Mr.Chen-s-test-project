package com.ccit.area.sales.dao.domain.archives;

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
 * 描述 : “销售区域”实体类
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:34:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : SalesAreaPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_sales_area")
public class SalesAreaPO extends BasePO<SalesAreaPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 区域编码
     */
    @TableField(value = "area_no")
    private String areaNo;

    /**
     * 区域名称
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * 产品分类编码
     */
    @TableField(value = "product_class_no")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @TableField(value = "product_class_name")
    private String productClassName;

    /**
     * 贸易类型
     */
    @TableField(value = "trade_type")
    private String tradeType;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年8月12日 下午2:52:27
     * 构造方法名 : SalesAreaPO()
     * 描述 : 
     */
	public SalesAreaPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:52:37
	 * 构造方法名 : SalesAreaPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SalesAreaPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年8月12日 下午2:52:48
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SalesAreaPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SalesAreaPO> wrapper() {
		LambdaQueryWrapper<SalesAreaPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SalesAreaPO::getDeleted, 0);
		return wrapper;
	}

}
