package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 * 描述 : “挂牌分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月20日 下午4:09:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ListedPageListDTO
 */
@Data
@Schema(description = "【挂牌分页列表】接受参数实体类")
public class ListedPageListDTO {

	/**
     * 状态
     */
    @Schema(description = "状态")
	private String status;
	
    /**
     * 挂牌编码
     */
    @Schema(description = "挂牌编码")
	private String listedNo;
	
    /**
     * 挂牌模式
     */
    @Schema(description = "挂牌模式")
	private String listedMode;
	
    /**
     * 挂牌不含税单价
     */
    @Schema(description = "挂牌不含税单价")
	private BigDecimal listedPrice;
	
    /**
     * 挂牌含税单价
     */
    @Schema(description = "挂牌含税单价")
	private BigDecimal listedTaxPrice;
	
    /**
     * 对标价格
     */
    @Schema(description = "对标价格")
	private BigDecimal benchmarkPrice;
	
    /**
     * 单价模式
     */
    @Schema(description = "单价模式")
	private String priceModel;
	
    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
	private String useStatus;
	
    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
	private String distributionWay;
	
    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
	private String transportWay;
	
    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
	private String payWay;
    
    /**
     * 有效开始时间
     */
    @Schema(description = "有效开始时间")
	private String effectiveStartTime;
	
    /**
     * 有效结束时间
     */
    @Schema(description = "有效结束时间")
	private String effectiveEndTime;
	
    /**
     * 币种
     */
    @Schema(description = "币种")
	private String currency;
    
    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;
	
    /**
     * 单价单位
     */
    @Schema(description = "单价单位")
	private String unit;
	
    /**
     * 重量单位
     */
    @Schema(description = "重量单位")
	private String unitWeight;
	
    /**
     * 区域编码
     */
    @Schema(description = "区域编码")
	private String areaNo;
	
    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
	private String areaName;
	
    /**
     * 提货地编码
     */
    @Schema(description = "提货地编码")
	private String deliveryPlaceNo;
	
    /**
     * 提货地名称
     */
    @Schema(description = "提货地名称")
	private String deliveryPlaceName;
	
    /**
     * 提货地分类编码
     */
    @Schema(description = "提货地分类编码")
	private String deliveryPlaceClassNo;
	
    /**
     * 提货地分类名称
     */
    @Schema(description = "提货地分类名称")
	private String deliveryPlaceClassName;
	
    /**
     * 产品分类编码
     */
    @Schema(description = "产品分类编码")
	private String productClassNo;
	
    /**
     * 产品分类名称
     */
    @Schema(description = "产品分类名称")
	private String productClassName;
	
    /**
     * 牌号编码
     */
    @Schema(description = "牌号编码")
	private String skuNo;
	
    /**
     * 牌号名称
     */
    @Schema(description = "牌号名称")
	private String skuName;
	
    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
	private String materielNo;
	
    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
	private String materielName;

    /**
     * 创建者账号
     */
    @Schema(description = "创建者账号")
    private String userName;
    
    /**
     * 组织编码
     */
	@Schema(description = "组织编码")
    private String orgNo;

    /**
     * 组织名称
     */
	@Schema(description = "组织名称")
    private String orgName;

    /**
     * 部门编码
     */
	@Schema(description = "部门编码")
    private String deptNo;

    /**
     * 部门名称
     */
	@Schema(description = "部门名称")
    private String deptName;
	
	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private String gmtCreate;
	
}
