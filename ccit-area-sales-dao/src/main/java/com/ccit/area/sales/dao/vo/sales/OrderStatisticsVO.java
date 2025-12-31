package com.ccit.area.sales.dao.vo.sales;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStatisticsVO {
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
     * 工作流编码
     */
    private String workflowid;

    /**
     * 审核标志
     */
    private String shbz;

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
}
