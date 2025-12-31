package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
  * 描述 : “竞价申请分页列表”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月12日 下午2:38:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : ApplyBiddingPageListDTO
 */
@Data
@Schema(description = "【竞价申请分页列表】接受参数实体类")
public class ApplyBiddingPageListDTO {

    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private String parentId;

    /**
     * 招标编号
     */
    private String tenderNumber;

    /**
     * 产品编码
     */
    private String materielNo;

    /**
     * 产品名称
     */
    private String materielName;

    /**
     * 竞价模式
     */
    @Schema(description = "竞价模式")
    private String biddingMode;

    /**
     * 客户
     */
    @Schema(description = "客户")
    private String customer;

    /**
     * 区域编码
     */
    private String areaNo;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * '提货地编码'
     */
    private String deliveryPlaceNo;
    /**
     * 提货地名称
     */
    private String deliveryPlaceName;
    /**
     * 提货地分类编码
     */
    private String deliveryPlaceClassNo;
    /**
     * 提货地分类名称
     */
    private String deliveryPlaceClassName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String deliveryMethod;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String shippingType;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String contacts;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private Date applicationTime;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;
}
