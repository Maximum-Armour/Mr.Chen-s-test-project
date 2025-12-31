package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 更新流程中心状态请求参数
 */
@Schema(description = "更新流程中心状态请求参数")
@Data
@Builder
public class UpdateStatusTasks {

    /**
     * OA审批任务ID。
     */
    @Schema(description = "OA审批任务ID。", required = true, example = "12345")
    private Long taskId;

    /**
     * 更新为目标任务状态。
     */
    @Schema(description = "更新为目标任务状态。", required = true, example = "COMPLETED")
    private String status;

    /**
     * 当 status 为 COMPLETED 时，必须指定任务结果：
     * AGREE：同意
     * REFUSE：拒绝
     * 当 status 为 CANCELED 时，不需要传 result。
     */
    @Schema(description = "当 status 为 COMPLETED 时，必须指定任务结果：AGREE（同意）、REFUSE（拒绝）。当 status 为 CANCELED 时，不需要传 result。", required = false, example = "AGREE")
    private String result;
}
