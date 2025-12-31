package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 查询审核状态响应参数。
 */
@Schema(description = "查询审核状态响应参数")
@Data
@Builder
public class Task {

    /**
     * OA审批任务ID。
     */
    @Schema(description = "OA审批任务ID。", required = true, example = "1234567890")
    private Long taskId;

    /**
     * 待办组ID。
     */
    @Schema(description = "待办组ID。", example = "GROUP001")
    private String activityId;

    /**
     * OA审批任务发起人的用户userId。
     */
    @Schema(description = "OA审批任务发起人的用户userId。", example = "USER001")
    private String userId;

    /**
     * 任务状态。
     */
    @Schema(description = "任务状态。", example = "RUNNING")
    private String status;

    /**
     * 任务处理结果。
     */
    @Schema(description = "任务处理结果。", example = "APPROVED")
    private String result;

    /**
     * OA审批任务创建时间（时间戳）。
     */
    @Schema(description = "OA审批任务创建时间（时间戳）。", example = "1696156800000")
    private Long createTime;

    /**
     * OA审批任务完成时间。
     */
    @Schema(description = "OA审批任务完成时间。", example = "2023-10-01T12:34:56Z")
    private String finishTime;

    /**
     * 流程实例ID。
     */
    @Schema(description = "流程实例ID。", example = "INSTANCE123456")
    private String processInstanceId;
}
