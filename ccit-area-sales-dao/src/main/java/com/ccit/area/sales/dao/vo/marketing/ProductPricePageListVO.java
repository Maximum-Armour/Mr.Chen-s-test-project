package com.ccit.area.sales.dao.vo.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * 描述 : “产品定价分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月14日 上午10:00:55
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.marketing
 * 类名 : ProductPricePageListVO
 */
@Data
@Schema(description = "【产品定价分页列表】返回结果实体类")
public class ProductPricePageListVO {

	/**
     * 产品定价ID
     */
	@Schema(description = "产品定价ID")
    private Long id;
	
	/**
     * 状态
     */
    @Schema(description = "状态")
	private String status;
	
    /**
     * 产品定价编码
     */
    @Schema(description = "产品定价编码")
	private String productPriceNo;
	
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
     * 有效开始时间
     */
    @Schema(description = "有效开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectiveStartTime;
	
    /**
     * 有效结束时间
     */
    @Schema(description = "有效结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectiveEndTime;

    /**
     * 工作流编码
     */
    @Schema(description = "工作流编码")
    private String workflowid;

    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String companyNo;
}
