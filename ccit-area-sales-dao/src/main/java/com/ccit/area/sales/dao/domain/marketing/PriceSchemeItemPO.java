package com.ccit.area.sales.dao.domain.marketing;

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
 * 描述 : “定价方案明细”实体类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:07:14
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.marketing
 * 类名 : PriceSchemeItemPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_price_scheme_item")
public class PriceSchemeItemPO extends BasePO<PriceSchemeItemPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 方案编码
     */
    @TableField(value = "scheme_no")
    private String schemeNo;

    /**
     * 方案明细编码
     */
    @TableField(value = "scheme_item_no")
    private String schemeItemNo;

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
     * 提货地编码
     */
    @TableField(value = "delivery_place_no")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @TableField(value = "delivery_place_name")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @TableField(value = "delivery_place_class_no")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @TableField(value = "delivery_place_class_name")
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @TableField(value = "distribution_way")
    private String distributionWay;

    /**
     * 运输方式
     */
    @TableField(value = "transport_way")
    private String transportWay;

    /**
     * 支付方式
     */
    @TableField(value = "pay_way")
    private String payWay;

    /**
     * 币种
     */
    private String currency;

    /**
     * 单位
     */
    private String unit;

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
     * 创建时间 : 2024年8月12日 上午9:10:49
     * 构造方法名 : PriceSchemeItemPO()
     * 描述 : 
     */
	public PriceSchemeItemPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 上午9:12:08
	 * 构造方法名 : PriceSchemeItemPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public PriceSchemeItemPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年8月12日 上午9:12:17
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.marketing
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<PriceSchemeItemPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<PriceSchemeItemPO> wrapper() {
		LambdaQueryWrapper<PriceSchemeItemPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PriceSchemeItemPO::getDeleted, 0);
		return wrapper;
	}

}
