package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 节点操作人信息。
 */
@Schema(description = "节点操作人信息")
@Data
public class WorkflowActor {

    /**
     * 节点操作人 key。
     */
    @Schema(description = "节点操作人 key。", example = "ACTOR001")
    private String actorKey;

    /**
     * 节点操作人类型，取值：
     * approver：审批人
     * notifier：抄送人
     * audit：办理人
     */
    @Schema(description = "节点操作人类型，取值：approver（审批人）、notifier（抄送人）、audit（办理人）。", example = "approver")
    private String actorType;

    /**
     * 节点操作人选择范围类型，取值：
     * allStaff：全公司
     * approvals：指定成员
     * labels：角色
     */
    @Schema(description = "节点操作人选择范围类型，取值：allStaff（全公司）、approvals（指定成员）、labels（角色）。", example = "approvals")
    private String actorSelectionType;

    /**
     * 节点操作人选择范围。
     */
    @Schema(description = "节点操作人选择范围。")
    private ActorSelectionRange actorSelectionRange;

    /**
     * 节点审批类型，取值：
     * MANUAL：人工审批
     * AUTO_AGREE：自动通过
     * AUTO_REFUSE：自动拒绝
     */
    @Schema(description = "节点审批类型，取值：MANUAL（人工审批）、AUTO_AGREE（自动通过）、AUTO_REFUSE（自动拒绝）。", example = "MANUAL")
    private String approvalType;

    /**
     * 节点审批方式，取值：
     * ONE_BY_ONE：依次审批
     * AND：会签审批
     * OR：或签审批
     */
    @Schema(description = "节点审批方式，取值：ONE_BY_ONE（依次审批）、AND（会签审批）、OR（或签审批）。", example = "ONE_BY_ONE")
    private String approvalMethod;

    /**
     * 节点激活类型，取值：
     * ALL：并行
     * ONE_BY_ONE：串行
     */
    @Schema(description = "节点激活类型，取值：ALL（并行）、ONE_BY_ONE（串行）。", example = "ONE_BY_ONE")
    private String actorActivateType;

    /**
     * 该审批人节点在发起审批时是否必填。
     */
    @Schema(description = "该审批人节点在发起审批时是否必填。", example = "true")
    private Boolean required;
}
