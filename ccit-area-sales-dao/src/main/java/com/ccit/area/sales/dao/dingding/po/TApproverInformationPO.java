package com.ccit.area.sales.dao.dingding.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * OA审批人信息表
 *
 * @TableName t_approver_information
 */
@TableName(value = "t_approver_information")
@Data
public class TApproverInformationPO implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节点层级
     */
    @TableField(value = "level")
    private int level;

    /**
     * 业务名称
     */
    @TableField(value = "business_name")
    private String businessName;

    /**
     * 审批人类型
     *
     * @return
     */
    @TableField(value = "approvel_type")
    private String approvelType;
    /**
     * '审批状态'
     */
    @TableField(value = "status")
    private String status;

    /**
     * 审批人名字
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 审批人编号
     */
    @TableField(value = "user_code")
    private String userCode;

    /**
     * 用户钉钉id
     */
    @TableField(value = "dingding_code")
    private String dingdingCode;

    /**
     * 备注编号
     */
    @TableField(value = "remark_code")
    private String remarkCode;

    /**
     * 审批实例code
     */
    @TableField(value = "approver_id")
    private String approverId;

    /**
     * 事件唯一Id
     */
    @TableField(value = "event_id")
    private String eventId;


    /**
     * 审批任务id
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 审批任务id状态
     */
    @TableField(value = "task_id_status")
    private int taskIdStatus;

    /**
     * 审批实例状态
     */
    @TableField("approver_status")
    private String approverStatus;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最后修改人名字
     */
    @TableField(value = "revise_name")
    private String reviseName;

    /**
     * 最后修改时间
     */
    @TableField(value = "revise_time")
    private Date reviseTime;

    /**
     * 删除人名字
     */
    @TableField(value = "deleted_name")
    private String deletedName;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

}