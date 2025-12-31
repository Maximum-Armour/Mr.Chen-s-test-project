package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地分类分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:50:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DeliveryPlaceClassPageListDTO
 */
@Data
@Schema(description = "【提货地分类分页列表】接受参数实体类")
public class DeliveryPlaceClassPageListDTO {

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
	
}
