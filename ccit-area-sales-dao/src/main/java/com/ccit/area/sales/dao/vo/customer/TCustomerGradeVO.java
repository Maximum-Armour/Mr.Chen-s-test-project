package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.util.Date;

@Data
public class TCustomerGradeVO {
    private Integer id;
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户级别
     */
    private String customerGrade;

    /**
     * 年度
     */
    private String annual;

    /**
     * 制单时间
     */
    private Date createdAt;

    /**
     * 制单人
     */
    private String createdBy;
}
