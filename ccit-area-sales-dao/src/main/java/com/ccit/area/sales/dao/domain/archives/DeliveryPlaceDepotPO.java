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
 * 描述 : “提货地仓库中间表”实体对象
 * 创建人 : yn
 * 创建时间 : 2024年08月07日 下午01:43:37
 * 版本 : 1.0
 * 包名 : com.chinacoal.microservice.model
 * 类名 : TDeliveryPlaceDepot
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_delivery_place_depot")
public class DeliveryPlaceDepotPO extends BasePO<DeliveryPlaceDepotPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 提货地编码
     */
    @TableField(value = "delivery_place_no")
    private String deliveryPlaceNo;

    /**
     * 仓库编码
     */
    @TableField(value = "depot_no")
    private String depotNo;

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
     * 创建时间 : 2024年8月7日 下午2:12:38
     * 构造方法名 : DeliveryPlaceDepotPO()
     * 描述 : 
     */
	public DeliveryPlaceDepotPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:12:46
	 * 构造方法名 : DeliveryPlaceDepotPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public DeliveryPlaceDepotPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年8月7日 下午2:13:02
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<DeliveryPlaceDepotPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<DeliveryPlaceDepotPO> wrapper() {
		LambdaQueryWrapper<DeliveryPlaceDepotPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(DeliveryPlaceDepotPO::getDeleted, 0);
		return wrapper;
	}

}
