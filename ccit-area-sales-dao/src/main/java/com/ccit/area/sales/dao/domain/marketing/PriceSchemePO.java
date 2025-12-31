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
 * 描述 : “定价方案”实体类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:05:53
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.marketing
 * 类名 : PriceSchemePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_price_scheme")
public class PriceSchemePO extends BasePO<PriceSchemePO> {

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
     * 方案名称
     */
    @TableField(value = "scheme_name")
    private String schemeName;

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
     * 
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:48:04
     * 构造方法名 : PriceSchemePO()
     * 描述 : 
     */
	public PriceSchemePO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:48:19
	 * 构造方法名 : PriceSchemePO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public PriceSchemePO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年8月9日 下午2:48:30
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.marketing
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<PriceSchemePO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<PriceSchemePO> wrapper() {
		LambdaQueryWrapper<PriceSchemePO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PriceSchemePO::getDeleted, 0);
		return wrapper;
	}

}
