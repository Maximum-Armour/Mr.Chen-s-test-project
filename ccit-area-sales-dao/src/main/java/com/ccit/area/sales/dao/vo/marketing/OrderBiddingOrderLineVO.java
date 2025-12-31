package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
  * 描述 : “竞价结果订单行列表”VO
  * 创建人 : tb
  * 创建时间 :2024年11月1日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : OrderBiddingOrderLineVO
 */
@Data
@Schema(description = "【竞价结果订单行列表】返回结果实体类")
public class OrderBiddingOrderLineVO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 挂牌编码
     */
    @Schema(description = "挂牌编码")
    private String biddingNo;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customer;

    /**
     * 订单提交价格
     */
    @Schema(description = "订单提交价格")
    private String latestQuotation;

    /**
     * 成交价格
     */
    @Schema(description = "成交价格")
    private String transactionPrice;

    /**
     * 订单提交数量
     */
    @Schema(description = "订单提交数量")
    private String quantity;

    /**
     * 成交数量
     */
    @Schema(description = "成交数量")
    private String transactionQuantity;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date gmtModified;

    /**
     * 订单类型
     */
    @Schema(description = "订单类型")
    private String orderType;


    /**
     * 订单编码
     */
    @Schema(description = "订单编码")
    private String orderNo;

    /**
     * 订单行编码
     */
    @Schema(description = "订单行编码")
    private String orderLineNo;
}
