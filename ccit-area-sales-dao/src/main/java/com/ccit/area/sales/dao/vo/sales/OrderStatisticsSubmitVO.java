package com.ccit.area.sales.dao.vo.sales;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatisticsSubmitVO {

    /**
     * 接单编码
     */
    private String takeOrderNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 创建者名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 明细
     */
    private String item;
}
