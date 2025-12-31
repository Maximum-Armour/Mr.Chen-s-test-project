package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地档案详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午10:02:53
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DeliveryPlaceDetailsVO
 */
@Data
@Schema(description = "【提货地档案详情】返回结果实体类")
public class DeliveryPlaceDetailsVO {

	/**
     * 提货地ID
     */
	@Schema(description = "提货地ID")
    private Long id;

	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;

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
     * 详细地址
     */
	@Schema(description = "详细地址")
    private String deliveryAddress;

    /**
     * 联系人
     */
	@Schema(description = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
	@Schema(description = "联系电话")
    private String contactsTelephone;
	
}
