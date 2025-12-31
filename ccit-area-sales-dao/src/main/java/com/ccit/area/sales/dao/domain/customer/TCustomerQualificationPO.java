package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 客户资质
 * @TableName t_customer_qualification
 */
@TableName(value ="t_customer_qualification")
@Data
public class TCustomerQualificationPO {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户全称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 客户所持有的有效资质类型
     */
    @TableField(value = "qualification_type")
    private String qualificationType;

    /**
     * 客户资质的有效期截止日期
     */
    @TableField(value = "expiration_date")
    private Date expirationDate;

    /**
     * 负责审核的用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 附件id
     */
    @TableField(value = "attachment_id")
    private Integer attachmentId;

    /**
     * 记录创建的时间戳
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 当前记录的审核状态
     */
    @TableField(value = "audit_status")
    private String auditStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}