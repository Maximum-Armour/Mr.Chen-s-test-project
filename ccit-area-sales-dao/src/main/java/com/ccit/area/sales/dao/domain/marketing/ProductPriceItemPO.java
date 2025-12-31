package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 描述 : “产品定价明细”实体类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.marketing
 * 类名 : ProductPriceItemPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_product_price_item")
public class ProductPriceItemPO extends BasePO<ProductPriceItemPO> {

	private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    @TableField(value = "status")
	private String status;
	
    /**
     * 产品定价编码
     */
    @TableField(value = "product_price_no")
	private String productPriceNo;
	
    /**
     * 产品定价明细编码
     */
    @TableField(value = "product_price_item_no")
	private String productPriceItemNo;
	
    /**
     * 方案明细编码
     */
    @TableField(value = "scheme_item_no")
	private String schemeItemNo;
	
    /**
     * 不含税单价
     */
    @TableField(value = "price", updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal price;
	
    /**
     * 含税单价
     */
    @TableField(value = "tax_price")
	private BigDecimal taxPrice;
	
    /**
     * 仓库编码
     */
    @TableField(value = "depot_no")
	private String depotNo;
	
    /**
     * 仓库名称
     */
    @TableField(value = "depot_name")
	private String depotName;
    
    /**
     * 对标价格
     */
    @TableField(value = "benchmark_price", updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal benchmarkPrice;
    
    /**
     * 价格单位
     */
    @TableField(value = "currency_unit")
	private String currencyUnit;

	/**
	 * 支付方式
	 */
	@TableField(value = "pay_way")
	private String payWay;
    
    /**
     * 备注
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String remark;
	
    /**
     * 工作流编码
     */
    @TableField(value = "workflowid")
	private String workflowid;
	
    /**
     * 审核标志
     */
    @TableField(value = "shbz")
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
     * 创建时间 : 2024年08月13日 下午03:46:35
     * 构造方法名 : ProductPriceItemPO()
     * 描述 : 
     */
	public ProductPriceItemPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 构造方法名 : ProductPriceItemPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public ProductPriceItemPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.marketing
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<ProductPriceItemPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<ProductPriceItemPO> wrapper() {
		LambdaQueryWrapper<ProductPriceItemPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ProductPriceItemPO::getDeleted, 0);
		return wrapper;
	}

}
