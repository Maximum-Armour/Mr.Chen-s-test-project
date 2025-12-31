package com.ccit.area.sales.dao.dto.sales;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatisticsDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 接单编码
     */
    private String takeOrderNo;

    /**
     * 状态
     */
    private String status;

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
     * 排序条件
     */
    private String field;
    /**
     * 排序字段
     */
    private String sortType;
}
