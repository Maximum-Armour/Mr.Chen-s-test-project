package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.*;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_price_bidding")
public class PriceBiddingPO extends BasePO<PriceBiddingPO> {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 招标编号
     */
    @TableField(value = "tender_number")
    private String tenderNumber;

    /**
     * 客户账号
     */
    @TableField(value = "customer_id")
    private String customerId;

    /**
     * 客户
     */
    @TableField(value = "customer")
    private String customer;

    /**
     * 客户IP
     */
    @TableField(value = "customer_ip")
    private String customerIp;

    /**
     * 产品编码
     */
    @TableField(value = "materiel_no")
    private String materielNo;

    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
    private String materielName;

    /**
     * 初始报价（元）
     */
    @TableField(value = "initial_quotation")
    private BigDecimal initialQuotation;

    /**
     * 最新报价(元)
     */
    @TableField(value = "latest_quotation")
    private BigDecimal latestQuotation;

    /**
     * 报价时间
     */
    @TableField(value = "quotation_time")
    private Date quotationTime;

    /**
     * 数量(吨)
     */
    @TableField(value = "quantity")
    private BigDecimal quantity;

    /**
     * 幅度
     */
    @TableField(value = "mark_up")
    private BigDecimal markUp;

    /**
     * 中标状态
     */
    @TableField(value = "win_bid_status")
    private String winBidStatus;

    /**
     * 公司编码
     */
    @TableField(value = "company_no")
    private String companyNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 组织编码
     */
    @TableField(value = "org_no")
    private String orgNo;

    /**
     * 组织名称
     */
    @TableField(value = "org_name")
    private String orgName;

}
