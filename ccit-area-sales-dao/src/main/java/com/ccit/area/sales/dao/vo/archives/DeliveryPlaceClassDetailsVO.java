package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地分类详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:41:12
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DeliveryPlaceClassDetailsVO
 */
@Data
@Schema(description = "【提货地分类详情】返回结果实体类")
public class DeliveryPlaceClassDetailsVO {

	/**
     * 提货地分类ID
     */
	@Schema(description = "提货地分类ID")
    private Long id;

	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

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
     * 提货地分类排序
     */
    @Schema(description = "提货地分类排序")
    private String deliveryPlaceClassOrders;
	
}
