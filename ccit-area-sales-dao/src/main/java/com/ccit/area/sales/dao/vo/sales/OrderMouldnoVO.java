package com.ccit.area.sales.dao.vo.sales;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "【客户意向详情】返回结果实体类")
public class OrderMouldnoVO {
    private  String productCategoryName;
    private String orderLineNo;
    private String orderType;
    private String flowProtocol;
    private String flowReportingCycle;
    private String cashTransferCycle;
    private String acceptancePaymentCycle;
    private String modeOfTransport;
    private Integer orderValidityDays;
    private Integer delayDays;
    private String newTerms;
    /**
     * 订单生效日期
     */
    private Date orderStartTime;
    /**
     * 订单截至日期
     */
    private Date orderEndTime;
}
