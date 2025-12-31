package com.ccit.area.sales.dao.vo.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 * 描述 : “产品定价明细分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月15日 下午2:09:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.marketing
 * 类名 : ProductPriceItemPageListVO
 */
@Data
@Schema(description = "【产品定价明细分页列表】返回结果实体类")
public class ProductPriceItemPageListVO {
	
	/**
     * 产品定价明细ID
     */
	@Schema(description = "产品定价明细ID")
    private Long id;
	
	/**
	 * 挂牌模式
	 */
	@Schema(description = "挂牌模式")
	private String listedMode = "GP";

    /**
     * 挂牌模式名称
     */
    @Schema(description = "挂牌模式名称")
    private String listedModeName;

    /**
     * 产品定价编码
     */
    @Schema(description = "产品定价编码")
	private String productPriceNo;
	
    /**
     * 产品定价明细编码
     */
    @Schema(description = "产品定价明细编码")
	private String productPriceItemNo;
	
    /**
     * 方案明细编码
     */
    @Schema(description = "方案明细编码")
	private String schemeItemNo;
    
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
    @TableField(value = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
    @TableField(value = "提货地名称")
    private String deliveryPlaceName;

    /**
     * 提货地分类编码
     */
    @TableField(value = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
    @TableField(value = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String distributionWay;

    /**
     * 配送方式名称
     */
    @Schema(description = "配送方式名称")
    private String distributionWayName;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String transportWay;

    /**
     * 运输方式名称
     */
    @Schema(description = "运输方式名称")
    private String transportWayName;
	
    /**
     * 不含税单价
     */
    @Schema(description = "不含税单价")
	private BigDecimal price;
	
    /**
     * 含税单价
     */
    @Schema(description = "含税单价")
	private BigDecimal taxPrice;
    
    /**
     * 仓库编码
     */
    @Schema(description = "仓库编码")
	private String depotNo;
	
    /**
     * 仓库名称
     */
    @Schema(description = "仓库名称")
	private String depotName;
	
    /**
     * 价格单位
     */
    @Schema(description = "价格单位")
	private String currencyUnit;

    /**
     * 价格单位名称
     */
    @Schema(description = "价格单位名称")
    private String currencyUnitName;
	
    /**
     * 对标价格
     */
    @Schema(description = "对标价格")
	private BigDecimal benchmarkPrice;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
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
     * 币种
     */
    @Schema(description = "币种")
	private String currency;

    /**
     * 币种名称
     */
    @Schema(description = "币种名称")
    private String currencyName;
	
    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
	private String tradeType;

    /**
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 支付方式名称
     */
    @Schema(description = "支付方式名称")
    private String payWayName;

    /**
     * 单价模式
     */
    @Schema(description = "单价模式")
    private String univalenceModel;

    /**
     * 单价模式名称
     */
    @Schema(description = "单价模式名称")
    private String univalenceModelName;

    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String businessType;

    /**
     * 业务类型名称
     */
    @Schema(description = "业务类型名称")
    private String businessTypeName;
    
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
    
}
