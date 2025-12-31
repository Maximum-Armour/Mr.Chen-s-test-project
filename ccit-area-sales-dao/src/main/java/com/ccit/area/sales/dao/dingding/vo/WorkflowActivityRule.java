package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 工作流节点规则。
 */
@Schema(description = "工作流节点规则")
@Data
public class WorkflowActivityRule {

    /**
     * 节点ID，暂无使用场景。
     */
    @Schema(description = "节点ID，暂无使用场景。", example = "ACTIVITY001")
    private String activityId;

    /**
     * 流程中前一个节点的 id。
     */
    @Schema(description = "流程中前一个节点的 id。", example = "PREV001")
    private String prevActivityId;

    /**
     * 节点名称。
     */
    @Schema(description = "节点名称。", example = "初审节点")
    private String activityName;

    /**
     * 规则类型，取值：target_select：自选审批人节点；target_approval：指定审批人节点。
     */
    @Schema(description = "规则类型，取值：target_select：自选审批人节点；target_approval：指定审批人节点。", example = "target_approval")
    private String activityType;

    /**
     * 是否为自选审批节点。
     * activityType 值为 target_select 时，该字段值为 true。
     */
    @Schema(description = "是否为自选审批节点。activityType 值为 target_select 时，该字段值为 true。", example = "true")
    private Boolean isTargetSelect;

    /**
     * 节点操作人信息。
     */
    @Schema(description = "节点操作人信息。")
    private WorkflowActor workflowActor;
}
