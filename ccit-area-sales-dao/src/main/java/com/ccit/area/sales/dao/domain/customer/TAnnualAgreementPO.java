package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 年度协议表
 * @TableName t_annual_agreement
 */
@TableName(value ="t_annual_agreement")
@Data
public class TAnnualAgreementPO implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 年度
     */
    @TableField(value = "annual")
    private Integer annual;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 审核状态
     */
    @TableField(value = "review_status")
    private String reviewStatus;

    /**
     * 审核拒绝原因
     */
    @TableField(value = "review_reject_reason")
    private String reviewRejectReason;

    /**
     * 附件id
     */
    @TableField(value = "attachment_id")
    private Integer attachmentId;
}