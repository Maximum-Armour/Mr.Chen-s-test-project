package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地档案修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:55:58
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DeliveryPlaceEditDTO
 */
@Data
@Schema(description = "【提货地档案修改】接受参数实体类")
public class DeliveryPlaceEditDTO {
	
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
	@Length(max = 50, message = "提货地编码长度不能超过50个字符")
	@Schema(description = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 提货地名称
     */
	@Length(max = 200, message = "提货地名称长度不能超过200个字符")
	@Schema(description = "提货地名称")
    private String deliveryPlaceName;

	/**
     * 提货地分类编码
     */
	@Length(max = 50, message = "提货地分类编码长度不能超过50个字符")
	@Schema(description = "提货地分类编码")
    private String deliveryPlaceClassNo;

    /**
     * 提货地分类名称
     */
	@Length(max = 200, message = "提货地分类名称长度不能超过200个字符")
	@Schema(description = "提货地分类名称")
    private String deliveryPlaceClassName;

    /**
     * 详细地址
     */
	@Length(max = 1000, message = "详细地址长度不能超过1000个字符")
	@Schema(description = "详细地址")
    private String deliveryAddress;

    /**
     * 联系人
     */
	@Length(max = 200, message = "联系人长度不能超过200个字符")
	@Schema(description = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
	@Length(max = 50, message = "联系电话长度不能超过50个字符")
	@Schema(description = "联系电话")
    private String contactsTelephone;
	
	/**
	 * 仓库编码（多个逗号隔开）
	 */
	@Schema(description = "仓库编码（多个逗号隔开）")
	private String depotNos;
	
}
