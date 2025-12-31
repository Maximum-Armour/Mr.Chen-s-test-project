package com.ccit.area.sales.dao.vo.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
  * 描述 : “产品定价详情”VO
  * 创建人 : tb
  * 创建时间 : 2024年12月24日 上午9:45:45
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.marketing
  * 类名 : ProductPriceSelectVO
 */
@Data
@Schema(description = "【产品定价详情】返回结果实体类")
public class ProductPriceSelectVO {

    /**
     * 产品定价ID
     */
    @Schema(description = "产品定价ID")
    private Long id;

    /**
     * 产品定价编码
     */
    @Schema(description = "产品定价编码")
    private String productPriceNo;

    /**
     * 产品分类名称
     */
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 牌号名称
     */
    @Schema(description = "牌号名称")
    private String skuName;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    private String materielName;

    /**
     * 币种名称
     */
    @Schema(description = "币种名称")
    private String currencyName;

    /**
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 单价模式名称
     */
    @Schema(description = "单价模式名称")
    private String univalenceModelName;

    /**
     * 业务类型名称
     */
    @Schema(description = "业务类型名称")
    private String businessTypeName;

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
     * 明细
     */
    @Schema(description = "明细")
    private String item;
}
