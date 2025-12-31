package com.ccit.area.sales.dao.dto.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 
 * 描述 : “产品定价修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月14日 上午9:29:23
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ProductPriceEditDTO
 */
@Data
@Schema(description = "【产品定价修改】接受参数实体类")
public class   ProductPriceEditDTO {

	/**
     * 产品定价ID
     */
	@Schema(description = "产品定价ID")
    private Long id;
	
	/**
     * 状态
     */
	@Length(max = 10, message = "状态长度不能超过10个字符")
    @Schema(description = "状态")
	private String status;
	
    /**
     * 产品定价编码
     */
	@Length(max = 50, message = "产品定价编码长度不能超过50个字符")
    @Schema(description = "产品定价编码")
	private String productPriceNo;
	
    /**
     * 产品分类编码
     */
	@Length(max = 50, message = "产品分类编码长度不能超过50个字符")
    @Schema(description = "产品分类编码")
	private String productClassNo;
	
    /**
     * 产品分类名称
     */
	@Length(max = 100, message = "产品分类名称长度不能超过50个字符")
    @Schema(description = "产品分类名称")
	private String productClassName;
	
    /**
     * 牌号编码
     */
	@Length(max = 50, message = "牌号编码长度不能超过50个字符")
    @Schema(description = "牌号编码")
	private String skuNo;
	
    /**
     * 牌号名称
     */
	@Length(max = 200, message = "牌号名称长度不能超过200个字符")
    @Schema(description = "牌号名称")
	private String skuName;
	
    /**
     * 产品编码
     */
	@Length(max = 50, message = "产品编码长度不能超过50个字符")
    @Schema(description = "产品编码")
	private String materielNo;
	
    /**
     * 产品名称
     */
	@Length(max = 100, message = "产品名称长度不能超过100个字符")
    @Schema(description = "产品名称")
	private String materielName;
	
    /**
     * 币种
     */
	@Length(max = 50, message = "币种长度不能超过50个字符")
    @Schema(description = "币种")
	private String currency;
	
    /**
     * 贸易类型
     */
	@Length(max = 50, message = "贸易类型长度不能超过50个字符")
    @Schema(description = "贸易类型")
	private String tradeType;
	
    /**
     * 单价模式
     */
	@Length(max = 50, message = "单价模式长度不能超过50个字符")
    @Schema(description = "单价模式")
	private String univalenceModel;
	
    /**
     * 业务类型
     */
	@Length(max = 50, message = "业务类型长度不能超过50个字符")
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
	 * 组织编码
	 */
	@Length(max = 50, message = "组织编码长度不能超过50个字符")
	@Schema(description = "组织编码")
	private String orgNo;

	/**
	 * 产品定价明细数组
	 */
	@Schema(description = "产品定价明细数组")
	private List<ProductPriceItemListDTO> itemList;
	
}
