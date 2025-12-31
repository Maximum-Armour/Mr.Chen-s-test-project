package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “定价方案详情”VO
  * 创建人 : tb
  * 创建时间 : 2024年12月24日 下午3:05:10
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.marketing
  * 类名 : PriceSchemeSelectVO
 */
@Data
@Schema(description = "【定价方案详情】返回结果实体类")
public class PriceSchemeSelectVO {

    /**
     * 方案ID
     */
    @Schema(description = "方案ID")
    private Long id;

    /**
     * 方案编码
     */
    @Schema(description = "方案编码")
    private String schemeNo;

    /**
     * 方案名称
     */
    @Schema(description = "方案名称")
    private String schemeName;


    /**
     * 产品分类名称
     */
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 明细
     */
    @Schema(description = "明细")
    private String item;
}
