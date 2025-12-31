package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 创建实例请求参数
 */
@Schema(description = "创建实例请求参数")
@Data
@Builder
public class ApprovalRequest {

    /**
     * 审批发起人的userId，必填。
     */
    @Schema(description = "审批发起人的userId", required = true)
    private String originatorUserId;

    /**
     * 审批流的唯一码，必填。
     * 可以在审批模板编辑页面的URL中获取。
     */
    @Schema(description = "审批流的唯一码", required = true)
    private String processCode;

    /**
     * 审批发起人所在的部门ID，非必填。
     * 若approvers已传值，则deptId不需填写；若approvers未传值，则deptId需必填，根部门ID填-1。
     */
    @Schema(description = "审批发起人所在的部门ID", required = false)
    private Long deptId;

    /**
     * 应用标识AgentId，非必填。
     * 获取方式详见企业内部应用和第三方企业应用的基础概念。
     */
    @Schema(description = "应用标识AgentId", required = false)
    private Long microappAgentId;

    /**
     * 直接指定的审批人列表，非必填，最大列表长度为20。
     * 指定审批单的执行流程，会覆盖审批单在OA后台设置的默认流程。
     */
    @Schema(description = "直接指定的审批人列表", required = false)
    private List<Approvers> approvers;

    /**
     * 抄送人 userId 列表，非必填，最大列表长度为50。
     */
    @Schema(description = "抄送人 userId 列表", required = false)
    private List<String> ccList;

    /**
     * 抄送时间点，非必填，取值有 START（开始时抄送）、FINISH（结束时抄送）、START_FINISH（开始和结束时都抄送）。
     */
    @Schema(description = "抄送时间点", required = false)
    private String ccPosition;

    /**
     * 使用审批流模板时，流程预测结果中节点规则上必填的自选操作人列表，非必填，最大列表长度为20。
     * 使用OA后台设置的默认流程，并且流程中有审批人自选节点时，该参数必填。
     */
    @Schema(description = "自选操作人列表", required = false)
    private List<TargetSelectActioners> targetSelectActioners;

    /**
     * 自选节点的规则key，非必填。
     * 获取方式详见企业内部应用和第三方企业应用的基础概念。
     */
    @Schema(description = "自选节点的规则key", required = false)
    private String actionerKey;

    /**
     * 操作人 userId 列表，非必填。
     */
    @Schema(description = "操作人 userId 列表", required = false)
    private List<String> actionerUserIds;

    /**
     * 表单数据内容，控件列表，必填，最大列表长度为150。
     */
    @Schema(description = "表单数据内容", required = true)
    private List<FormComponentValues> formComponentValues;
}

