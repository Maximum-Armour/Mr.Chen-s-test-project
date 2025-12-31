package com.ccit.area.sales.dao.dingding.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_dingding_approval_template")
public class DingdingApprovalTemplatePO {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公司编号
     */
    @TableField(value = "company_no")
    private String companyNo;

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 模板编号
     */
    @TableField(value = "process_code")
    private String processCode;

    /**
     * 创建人名字
     */
    @TableField(value = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改人名字
     */
    @TableField(value = "update_name")
    private String updateName;

    /**
     * 最后修改时间
     */
    @TableField(value = "revise_time")
    private LocalDateTime reviseTime;

    /**
     * 删除人名字
     */
    @TableField(value = "deleted_name")
    private String deletedName;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_time")
    private LocalDateTime deletedTime;

    /**
     * 删除标志: 1 为删除 0 未删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
