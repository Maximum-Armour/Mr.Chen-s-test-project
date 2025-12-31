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
 * 描述 : “提货地分类”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:20:33
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : DeliveryPlaceClassPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_delivery_place_class")
public class DeliveryPlaceClassPO extends BasePO<DeliveryPlaceClassPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 产品分类编码
     */
    @TableField(value = "delivery_place_class_no")
    private String deliveryPlaceClassNo;

    /**
     * 产品分类名称
     */
    @TableField(value = "delivery_place_class_name")
    private String deliveryPlaceClassName;
    
    /**
     * 提货地分类排序
     */
    @TableField(value = "delivery_place_class_orders")
    private String deliveryPlaceClassOrders;

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
     * 创建时间 : 2024年8月1日 上午9:34:44
     * 构造方法名 : DeliveryPlaceClassPO()
     * 描述 :
     */
	public DeliveryPlaceClassPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:34:55
	 * 构造方法名 : DeliveryPlaceClassPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public DeliveryPlaceClassPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年8月1日 上午9:35:02
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<DeliveryPlaceClassPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<DeliveryPlaceClassPO> wrapper() {
		LambdaQueryWrapper<DeliveryPlaceClassPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(DeliveryPlaceClassPO::getDeleted, 0);
		return wrapper;
	}

}
