package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “销售区域分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午3:21:44
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SalesAreaPageListDTO
 */
@Data
@Schema(description = "【销售区域分页列表】接受参数实体类")
public class SalesAreaPageListDTO {

	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

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
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
	
}
