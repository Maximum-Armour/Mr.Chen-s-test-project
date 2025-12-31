package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 审批结果。
 */
@Schema(description = "审批结果")
@Data
public class ApprovalResult {

    /**
     * 审批实例标题。
     */
    @Schema(description = "审批实例标题。", required = true, example = "项目审批")
    private String title;

    /**
     * 结束时间。
     */
    @Schema(description = "结束时间。", required = false, example = "2023-10-01T12:34:56Z")
    private String finishTime;

    /**
     * 发起人的 userId。
     */
    @Schema(description = "发起人的 userId。", required = true, example = "userId12345")
    private String originatorUserId;

    /**
     * 发起人的部门，-1 表示根部门。
     */
    @Schema(description = "发起人的部门，-1 表示根部门。", required = true, example = "-1")
    private String originatorDeptId;

    /**
     * 发起人的部门名称。
     */
    @Schema(description = "发起人的部门名称。", required = true, example = "销售部")
    private String originatorDeptName;

    /**
     * 审批状态。
     * RUNNING：审批中
     * TERMINATED：已撤销
     * COMPLETED：审批完成
     */
    @Schema(description = "审批状态。", required = true, allowableValues = {"RUNNING", "TERMINATED", "COMPLETED"}, example = "COMPLETED")
    private String status;

    /**
     * 审批人 userId 列表。
     * 使用接口发起的审批单返回该参数。
     * 在 OA 审批应用手动发起的审批单不返回该参数。
     */
    @Schema(description = "审批人 userId 列表。使用接口发起的审批单返回该参数。在 OA 审批应用手动发起的审批单不返回该参数。", required = false)
    private List<String> approverUserIds;

    /**
     * 抄送人 userId 列表。
     */
    @Schema(description = "抄送人 userId 列表。", required = false)
    private List<String> ccUserIds;

    /**
     * 审批结果。
     * agree：同意
     * refuse：拒绝
     * status 为 COMPLETED 且 result 为 agree 时，表示审批单完结并审批通过。
     */
    @Schema(description = "审批结果。status 为 COMPLETED 且 result 为 agree 时，表示审批单完结并审批通过。", required = false, allowableValues = {"agree", "refuse"}, example = "agree")
    private String result;

    /**
     * 审批实例业务编号。
     */
    @Schema(description = "审批实例业务编号。", required = false, example = "bizId12345")
    private String businessId;

    /**
     * 操作记录列表。
     */
    @Schema(description = "操作记录列表。", required = false)
    private List<OperationRecord> operationRecords;

    /**
     * 任务列表。
     */
    @Schema(description = "任务列表。", required = false)
    private List<ApprovalTask> tasks;

    /**
     * 审批实例业务动作。
     * MODIFY：表示该审批实例是基于原来的实例修改而来
     * REVOKE：表示该审批实例是由原来的实例撤销后重新发起的
     * NONE：表示正常发起
     */
    @Schema(description = "审批实例业务动作。", required = false, allowableValues = {"MODIFY", "REVOKE", "NONE"}, example = "NONE")
    private String bizAction;

    /**
     * 用户自定义业务参数透出。
     */
    @Schema(description = "用户自定义业务参数透出。", required = false, example = "{\"key\": \"value\"}")
    private String bizData;

    /**
     * 审批附属实例。
     */
    @Schema(description = "审批附属实例。", required = false)
    private List<String> attachedProcessInstanceIds;

    /**
     * 主流程实例标识。
     */
    @Schema(description = "主流程实例标识。", required = false, example = "mainInstanceId12345")
    private String mainProcessInstanceId;

    /**
     * 表单组件详情列表。
     */
    @Schema(description = "表单组件详情列表。", required = false)
    private List<FormComponentValue> formComponentValues;

    /**
     * 创建时间。
     */
    @Schema(description = "创建时间。", required = true, example = "2023-10-01T12:34:56Z")
    private String createTime;
}
