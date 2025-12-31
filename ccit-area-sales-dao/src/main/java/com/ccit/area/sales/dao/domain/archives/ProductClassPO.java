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
 * 描述 : “产品分类”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 上午10:58:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : ProductClassPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_product_class")
public class ProductClassPO extends BasePO<ProductClassPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

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
     * 产品分类排序
     */
    @TableField(value = "product_class_orders")
    private Integer productClassOrders;

    /**
     * 产品分类序列号
     */
    @TableField(value = "product_class_seq")
    private String productClassSeq;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 主副产品
     */
    @TableField(value = "main_or_byproduct")
    private String mainOrByproduct;

    /**
     * 是否危化品
     */
    @TableField(value = "is_whp")
    private String isWhp;

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
     * 创建时间 : 2024年7月17日 下午2:38:15
     * 构造方法名 : ProductClassPO()
     * 描述 : 
     */
	public ProductClassPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:38:24
	 * 构造方法名 : ProductClassPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public ProductClassPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月17日 下午2:38:41
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<ProductClassPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<ProductClassPO> wrapper() {
		LambdaQueryWrapper<ProductClassPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ProductClassPO::getDeleted, 0);
		return wrapper;
	}

}
