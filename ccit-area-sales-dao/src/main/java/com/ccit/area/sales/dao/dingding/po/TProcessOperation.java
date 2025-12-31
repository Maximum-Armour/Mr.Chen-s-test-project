package com.ccit.area.sales.dao.dingding.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 流程操作表
 *
 * @TableName t_process_operation
 */
@Data
@Builder
@TableName("t_process_operation")
public class TProcessOperation {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 上级模块
     */
    @TableField("parent_name")
    private String parentName;

    /**
     * 流程
     */
    @TableField("technological_process")
    private String technologicalProcess;

    /**
     * 操作者
     */
    @TableField("operator_name")
    private String operatorName;

    @TableField("user_name")
    private String userName;

    /**
     * 操作时间
     */
    @TableField("operator_time")
    private Date operatorTime;

    /**
     * 操作
     */
    @TableField("operation")
    private String operation;

    @TableField("data_name")
    private String dataName;

    /**
     * 组织编码
     */
    @TableField("org_no")
    private String orgNo;

    /**
     * 组织名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 部门编码
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建者名称
     */
    @TableField("create_by_name")
    private String createByName;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新者名称
     */
    @TableField("update_by_name")
    private String updateByName;

    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;

    /**
     * 删除标识：0-未删除；1-已删除；
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 删除者
     */
    @TableField("deleted_by")
    private String deletedBy;

    /**
     * 删除者名称
     */
    @TableField("deleted_by_name")
    private String deletedByName;

    /**
     * 钉钉审批实例id
     */
    @TableField("approval_id")
    private String approvalId;

    /**
     * 删除时间
     */
    @TableField("deleted_time")
    private Date deletedTime;

    @TableField("process_code")
    private String processCode;

    @TableField("business_id")
    private Long businessId;

    @TableField("order_number")
    private String orderNumber;

    /**
     * 公司编码
     */
    @TableField("company_no")
    private String companyNo;

    @TableField("level")
    private int level;
}