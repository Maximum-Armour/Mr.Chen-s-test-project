package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 返回结果详情。
 */
@Schema(description = "返回结果详情")
@Data
public class QueryApproverNodeResult {

    /**
     * 是否预测成功，成功返回true。
     */
    @Schema(description = "是否预测成功，成功返回true。", required = true, example = "true")
    private Boolean isForecastSuccess;

    /**
     * 表单的唯一码。
     */
    @Schema(description = "表单的唯一码。", required = true, example = "PROCESS123456")
    private String processCode;

    /**
     * 用户id。
     */
    @Schema(description = "用户id。", required = true, example = "USER123456")
    private String userId;

    /**
     * 流程ID，暂无使用场景。
     */
    @Schema(description = "流程ID，暂无使用场景。", example = "1234567890")
    private Long processId;

    /**
     * 是否静态流程。
     * true：是
     * false：否
     */
    @Schema(description = "是否静态流程。true：是；false：否。", required = true, example = "true")
    private Boolean isStaticWorkflow;

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
