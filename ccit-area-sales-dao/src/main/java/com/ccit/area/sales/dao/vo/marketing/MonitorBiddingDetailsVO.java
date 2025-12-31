package com.ccit.area.sales.dao.vo.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
  * 描述 : “竞价监控详情”VO
  * 创建人 : tb
  * 创建时间 : 2024年10月9日 上午10:20:45
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : MonitorBiddingDetailsVO
 */
@Data
@Schema(description = "【竞价监控详情】返回结果实体类")
public class MonitorBiddingDetailsVO {

    /**
     * 产品竞价ID
     */
    @Schema(description = "产品竞价ID")
    private Long id;

    /**
     * 竞价状态
     */
    @Schema(description = "竞价状态")
    private String status;

    /**
     * 招标编号
     */
    @Schema(description = "招标编号")
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
    private String biddingModeName;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private String quantity;

    /**
     * 竞价底价
     */
    @Schema(description = "竞价底价")
    private BigDecimal lowPrice;

    /**
     * 最低加价幅度
     */
    @Schema(description = "最低加价幅度")
    private BigDecimal minMarkup;

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
    private String deliveryMethodName;

    /**
     * 运输方式
     */
    @Schema(description = "运输方式")
    private String shippingTypeName;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date startingTime;

    /**
     * 竞价时长
     */
    @Schema(description = "竞价时长")
    private BigDecimal biddingDuration;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 说明
     */
    @Schema(description = "说明")
    private String productDescription;

    /**
     * 客户详情
     */
    @Schema(description = "客户详情")
    private List<MonitorBiddingPageListVO> listVO;

    /**
     * 坐标
     */
    @Schema(description = "坐标")
    private List<List<String>> data;

    /**
     * 当前时间
     */
    @Schema(description = "当前时间")
    private String localhostTime;

    private String minBidders;
}
