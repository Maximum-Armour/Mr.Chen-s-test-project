package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地分类新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:36:55
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DeliveryPlaceClassAddDTO
 */
@Data
@Schema(description = "【提货地分类新增】接受参数实体类")
public class DeliveryPlaceClassAddDTO {

	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

    /**
     * 产品分类编码
     */
    @Length(max = 50, message = "提货地分类编码长度不能超过50个字符")
    @Schema(description = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 产品分类名称
     */
    @Length(max = 200, message = "提货地分类称长度不能超过200个字符")
    @Schema(description = "提货地分类名称")
    private String deliveryPlaceClassName;
    
    /**
     * 提货地分类排序
     */
    @Schema(description = "提货地分类排序")
    private String deliveryPlaceClassOrders;


    private String orgNo;
	
}
