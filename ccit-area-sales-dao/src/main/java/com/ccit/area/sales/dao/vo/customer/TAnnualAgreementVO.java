package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.util.Date;

@Data
public class TAnnualAgreementVO {
    /**
     *
     */
    private Long id;

    /**
     * 年度
     */
    private Integer annual;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 审核状态
     */
    private String reviewStatus;

    /**
     * 审核拒绝原因
     */
    private String reviewRejectReason;

    /**
     * 附件id
     */
    private Integer attachmentId;
}
