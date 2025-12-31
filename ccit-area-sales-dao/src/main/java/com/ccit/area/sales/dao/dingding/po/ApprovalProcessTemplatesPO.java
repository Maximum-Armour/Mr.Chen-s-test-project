package com.ccit.area.sales.dao.dingding.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

/**
 * 审批流程模板持久化对象（PO）。
 * 该类映射到数据库表 t_approval_process_templates，用于存储审批流程模板的相关信息。
 */
@Data
@TableName("t_approval_process_templates")
public class ApprovalProcessTemplatesPO {

    /**
     * 主键ID，自动生成。
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 业务名称。
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 审批人真实姓名。
     */
    @TableField("approval_real_name")
    private String approvalRealName;

    /**
     * 审批人用户名。
     */
    @TableField("approval_user_name")
    private String approvalUserName;

    /**
     * 父级ID，用于表示层级关系。
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 层级，表示当前记录在层级结构中的深度。
     */
    @TableField("level")
    private int level;

    /**
     * 类型，用于区分不同的审批流程类型。
     */
    @TableField("type")
    private String type;

    /**
     * 类型描述，对类型字段的详细解释。
     */
    @TableField("type_description")
    private String typeDescription;

    /**
     * 创建人姓名。
     */
    @TableField("created_name")
    private String createdName;

    /**
     * 创建时间。
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 更新人姓名。
     */
    @TableField("update_name")
    private String updateName;

    /**
     * 更新时间。
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 删除人姓名。
     */
    @TableField("deleted_name")
    private String deletedName;

    /**
     * 删除时间。
     */
    @TableField("deleted_time")
    private Date deletedTime;

    /**
     * 是否已删除，逻辑删除标志。
     */
    @TableField("is_deleted")
    private int isDeleted;

    /**
     * 公司编码，用于标识所属公司。
     */
    @TableField("company_no")
    private String companyNo;
}
