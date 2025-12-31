package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * 描述 : “产品定价”实体类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.marketing
 * 类名 : ProductPricePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_product_price")
public class ProductPricePO extends BasePO<ProductPricePO> {

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
     * 产品编码
     */
    @TableField(value = "materiel_no")
	private String materielNo;
	
    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
	private String materielName;
	
    /**
     * 币种
     */
    @TableField(value = "currency")
	private String currency;
	
    /**
     * 贸易类型
     */
    @TableField(value = "trade_type")
	private String tradeType;
	
    /**
     * 单价模式
     */
    @TableField(value = "univalence_model")
	private String univalenceModel;
    
    /**
     * 业务类型
     */
    @TableField(value = "business_type")
	private String businessType;
	
    /**
     * 有效开始时间
     */
    @TableField(value = "effective_start_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectiveStartTime;
	
    /**
     * 有效结束时间
     */
    @TableField(value = "effective_end_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectiveEndTime;
	
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
	 * 公司编码
	 */
	@TableField(value = "company_no")
	private String companyNo;

	/**
	 * 公司名称
	 */
	@TableField(value = "company_name")
	private String companyName;

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
     * 
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午03:46:35
     * 构造方法名 : ProductPricePO()
     * 描述 : 
     */
	public ProductPricePO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 构造方法名 : ProductPricePO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public ProductPricePO(Boolean deleted, Boolean isAutoFillUser) {
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
	 *  LambdaQueryWrapper<ProductPricePO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<ProductPricePO> wrapper() {
		LambdaQueryWrapper<ProductPricePO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ProductPricePO::getDeleted, 0);
		return wrapper;
	}

}
