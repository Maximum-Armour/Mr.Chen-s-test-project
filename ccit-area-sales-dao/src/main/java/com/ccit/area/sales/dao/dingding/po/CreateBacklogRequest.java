package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 创建待办事项请求参数
 */
@Schema(description = "创建待办事项请求参数")
@Data
@Builder
public class CreateBacklogRequest {

    /**
     * OA审批流程实例ID。
     */
    @Schema(description = "OA审批流程实例ID", required = true)
    private String processInstanceId;

    /**
     * 节点ID，可选字段。
     */
    @Schema(description = "节点ID，可选字段", required = false)
    private String activityId;

    /**
     * 待办事项列表，这是必填项。
     */
    @Schema(description = "待办事项列表，这是必填项", required = true)
    private List<TaskTopPO> tasks;
}
