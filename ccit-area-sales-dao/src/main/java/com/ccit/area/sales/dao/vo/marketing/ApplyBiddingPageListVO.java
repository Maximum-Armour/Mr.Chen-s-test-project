package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
  * 描述 : “竞价审核分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年9月12日 下午3:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : ApplyBiddingPageListVO
 */
@Data
@Schema(description = "【竞价审核分页列表】返回结果实体类")
public class ApplyBiddingPageListVO {

    /**
     * 竞价审核ID
     */
    @Schema(description = "竞价审核ID")
    private Long id;

    /**
     * 招标编号
     */
    private String tenderNumber;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerId;

    /**
     * 客户
     */
    @Schema(description = "客户")
    private String customer;

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
     * 竞价模式名称
     */
    @Schema(description = "竞价模式名称")
    private String biddingModeName;


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
     * 仓库编码
     */
    private String depotNo;
    /**
     * 仓库名称
     */
    private String depotName;

    /**
     * 配送方式
     */
    @Schema(description = "配送方式")
    private String deliveryMethod;

    /**
     * 配送方式名称
     */
    @Schema(description = "配送方式名称")
    private String deliveryMethodName;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String shippingType;

    /**
     * 运输方式名称
     */
    @Schema(description = "运输方式名称")
    private String shippingTypeName;

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
     * 审核状态
     */
    @Schema(description = "审核状态")
    private String status;
}
