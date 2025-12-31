package com.ccit.area.sales.dao.vo.marketing;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderBiddingDataVO {
    //客户账号
    private String customerId;
    //客户公司
    private String customer;
    //最新报价
    private BigDecimal latestQuotation;
    //数量
    private BigDecimal quantity;
    //报价时间
    private Date quotationTime;
}
