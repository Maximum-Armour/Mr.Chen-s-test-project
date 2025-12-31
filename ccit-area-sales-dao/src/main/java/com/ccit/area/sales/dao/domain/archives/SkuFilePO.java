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
 * 描述 : “牌号档案”实体对象
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:02:58
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : SkuFilePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_sku_file")
public class SkuFilePO extends BasePO<SkuFilePO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 牌号编码
     */
    @TableField(value = "sku_no")
    private String skuNo;

    /**
     * 牌号名称
     */
    @TableField(value = "sku_name")
    private String skuName;

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
     * 主副产品
     */
    @TableField(value = "main_or_byproduct")
    private String mainOrByproduct;

    /**
     * 执行标准
     */
    @TableField(value = "executive_standard")
    private String executiveStandard;

    /**
     * 标准号
     */
    @TableField(value = "standard_number")
    private String standardNumber;

    /**
     * 工作流编码
     */
    private String workflowid;

    /**
     * 审核标志
     */
    private String shbz;

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
     * 部门编码
     */
    @TableField(value = "dept_no")
    private String deptNo;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月17日 下午1:55:00
     * 构造方法名 : SkuFilePO()
     * 描述 : 
     */
	public SkuFilePO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:55:08
	 * 构造方法名 : SkuFilePO(Boolean deleted, Boolean isAutoFillUser) 
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public SkuFilePO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月17日 下午1:55:24
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<SkuFilePO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<SkuFilePO> wrapper() {
		LambdaQueryWrapper<SkuFilePO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SkuFilePO::getDeleted, 0);
		return wrapper;
	}

}
