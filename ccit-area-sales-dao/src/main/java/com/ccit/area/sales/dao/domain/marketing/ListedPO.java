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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 描述 : “挂牌”实体类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午03:46:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.marketing
 * 类名 : ListedPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_listed")
public class ListedPO extends BasePO<ListedPO> {

	private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
	private String status;
	
    /**
     * 挂牌编码
     */
    @TableField(value = "listed_no")
	private String listedNo;
	
    /**
     * 挂牌模式
     */
    @TableField(value = "listed_mode")
	private String listedMode;
	
    /**
     * 挂牌不含税单价
     */
    @TableField(value = "listed_price")
	private BigDecimal listedPrice;
	
    /**
     * 挂牌含税单价
     */
    @TableField(value = "listed_tax_price")
	private BigDecimal listedTaxPrice;
	
    /**
     * 对标价格
     */
    @TableField(value = "benchmark_price")
	private BigDecimal benchmarkPrice;
	
    /**
     * 单价模式
     */
    @TableField(value = "price_model")
	private String priceModel;
	
    /**
     * 上下架状态
     */
    @TableField(value = "use_status")
	private String useStatus;
	
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
     * 币种
     */
	private String currency;
	
    /**
     * 贸易类型
     */
    @TableField(value = "trade_type")
	private String tradeType;
	
    /**
     * 单价单位
     */
	private String unit;
	
    /**
     * 重量单位
     */
    @TableField(value = "unit_weight")
	private String unitWeight;
	
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
     * 备注
     */
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
	 * 挂牌状态
	 */
	@TableField(value = "listed_status")
	private String listedStatus;
	
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
     * 创建时间 : 2024年08月13日 下午03:46:35
     * 构造方法名 : ListedPO()
     * 描述 : 
     */
	public ListedPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午03:46:35
	 * 构造方法名 : ListedPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public ListedPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 *  LambdaQueryWrapper<ListedPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<ListedPO> wrapper() {
		LambdaQueryWrapper<ListedPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ListedPO::getDeleted, 0);
		return wrapper;
	}

}
