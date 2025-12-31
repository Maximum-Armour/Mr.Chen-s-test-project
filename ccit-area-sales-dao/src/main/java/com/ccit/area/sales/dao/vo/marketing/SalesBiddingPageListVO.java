package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
  * 描述 : “产品竞价分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年9月6日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : SalesBiddingPageListVO
 */
@Data
@Schema(description = "【产品竞价分页列表】返回结果实体类")
public class SalesBiddingPageListVO {

    /**
     * 产品竞价ID
     */
    @Schema(description = "产品竞价ID")
    private Long id;

    /**
     * 竞价编码
     */
    @Schema(description = "竞价编码")
    private String biddingNo;

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
     * 提货仓库名称
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
     * 竞价底价（元）
     */
    @Schema(description = "竞价底价（元）")
    private BigDecimal lowPrice;

    /**
     * 报名截止时间
     */
    @Schema(description = "报名截止时间")
    private Date signUpEndTime;

    /**
     * 起拍时间
     */
    @Schema(description = "起拍时间")
    private Date startingTime;
    /**
     * 竞价结束时间
     */
    @Schema(description = "结束时间")
    private Date endTime;
    /**
     * 竞价时长
     */
    @Schema(description = "竞价时长")
    private BigDecimal biddingDuration;

    /**
     * 竞价状态
     */
    @Schema(description = "竞价状态")
    private String status;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private String enableStatus;

    /**
     * 工作流编码
     */
    @Schema(description = "工作流编码")
    private String workflowid;

    /**
     * 延时时长
     */
    @Schema(description = "延时时长")
    private BigDecimal delayDuration;

    /**
     * 延时次数
     */
    @Schema(description = "延时次数")
    private String delayFrequency;

    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String companyNo;

    /**
     * 招标编码
     */
    @Schema(description = "招标编码")
    private String tenderNumber;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private String quantity;

    /**
     * 最低加价幅度
     */
    @Schema(description = "最低加价幅度")
    private BigDecimal minMarkup;

    /**
     * 距离结束时长
     */
    @Schema(description = "距离结束时长")
    private BigDecimal distanceEndDuration;

    /**
     * 结果确认状态
     */
    private String orderBiddingNo;
}
