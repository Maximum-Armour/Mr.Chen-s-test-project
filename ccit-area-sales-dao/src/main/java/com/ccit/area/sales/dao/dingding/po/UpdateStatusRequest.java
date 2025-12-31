package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 更新流程中心状态请求参数
 */
@Schema(description = "更新流程中心状态请求参数")
@Data
@Builder
public class UpdateStatusRequest {

    /**
     * OA审批流程实例ID。
     */
    @Schema(description = "OA审批流程实例ID。", required = true, example = "processInstanceId12345")
    private String processInstanceId;

    /**
     * OA审批任务列表。
     */
    @Schema(description = "OA审批任务列表。", required = true)
    private List<UpdateStatusTasks> tasks;
}
