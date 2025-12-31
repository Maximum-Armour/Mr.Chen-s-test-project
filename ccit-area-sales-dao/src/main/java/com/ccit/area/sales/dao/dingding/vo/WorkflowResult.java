package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 工作流结果信息。
 */
@Schema(description = "工作流结果信息")
@Data
public class WorkflowResult {

    /**
     * 是否预测成功。
     */
    @Schema(description = "是否预测成功。", example = "true")
    private boolean isForecastSuccess;

    /**
     * 表单的唯一码。
     */
    @Schema(description = "表单的唯一码。", example = "FORM_CODE_001")
    private String processCode;

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID。", example = "USER001")
    private String userId;

    /**
     * 流程ID，目前暂无使用场景。
     */
    @Schema(description = "流程ID，目前暂无使用场景。", example = "1234567890")
    private Long processId;

    /**
     * 是否为静态流程。
     */
    @Schema(description = "是否为静态流程。", example = "false")
    private boolean isStaticWorkflow;

    /**
     * 工作流节点规则列表。
     */
    @Schema(description = "工作流节点规则列表。")
    private List<WorkflowActivityRule> workflowActivityRules;

    /**
     * 工作流节点流列表。
     */
    @Schema(description = "工作流节点流列表。")
    private List<WorkflowForecastNode> workflowForecastNodes;
}
