package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审批人信息详情。
 */
@Schema(description = "审批人信息详情")
@Data
public class TApproverInformationVO {

    /**
     * 审批人名字。
     */
    @Schema(description = "审批人名字。", example = "张三")
    private String userName;

    /**
     * 审批人编号。
     */
    @Schema(description = "审批人编号。", example = "USER001")
    private String userCode;

    /**
     * 钉钉编号。
     */
    @Schema(description = "钉钉编号。", example = "DING001")
    private String dingdingCode;

    /**
     * 审批实例code。
     */
    @Schema(description = "审批实例code。", example = "APPROVAL123456")
    private String approverId;

    /**
     * 审批实例状态。
     */
    @Schema(description = "审批实例状态。", example = "待审批")
    private String approverStatus;

    /**
     * 审批任务id。
     */
    @Schema(description = "审批任务id。", example = "1234567890")
    private Long taskId;

    /**
     * 审批任务状态。
     */
    @Schema(description = "审批任务状态。", example = "1")
    private int taskIdStatus;

    /**
     * 流程分类。
     */
    @Schema(description = "流程分类。", example = "销售审批")
    private String processClassification;

    /**
     * 流程实例名称。
     */
    @Schema(description = "流程实例名称。", example = "销售合同审批")
    private String processInstanceName;

    /**
     * 业务id。
     */
    @Schema(description = "业务id。", example = "1234567890")
    private Long businessId;

    /**
     * 创建时间。
     */
    @Schema(description = "创建时间。", example = "2023-10-01T12:34:56Z")
    private String createTime;

    /**
     * 订单编码。
     */
    @Schema(description = "订单编码。", example = "ORDER20231001")
    private String orderNumber;

    /**
     * 业务类型。
     */
    @Schema(description = "业务类型。", example = "销售合同")
    private String businessName;
}
