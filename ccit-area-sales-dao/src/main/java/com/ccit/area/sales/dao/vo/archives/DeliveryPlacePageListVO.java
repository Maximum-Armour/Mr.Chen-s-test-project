package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地档案分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午10:37:15
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DeliveryPlacePageListVO
 */
@Data
@Schema(description = "【提货地档案分页列表】返回结果实体类")
public class DeliveryPlacePageListVO {
	
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
     * 状态名称
     */
    @Schema(description = "状态名称")
    private String statusName;

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
