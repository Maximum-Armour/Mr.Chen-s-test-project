package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 审批记录响应VO
 */
@Schema(description = "审批记录响应VO")
@Data
@Builder
public class ApprovalLivingResponseVO {

    /**
     * 审批人（用户ID）。
     */
    @Schema(description = "审批人（用户ID）。", required = true, example = "userId12345")
    private String approver;

    /**
     * 审批时间。
     */
    @Schema(description = "审批时间。", required = true, example = "2023-10-01T12:34:56Z")
    private String approvalTime;

    /**
     * 审批状态。
     */
    @Schema(description = "审批状态。", required = true, example = "APPROVED")
    private String approvalStatus;

    /**
     * 审批人名称。
     */
    @Schema(description = "审批人名称。", required = true, example = "张三")
    private String approvalName;

    /**
     * 节点编号。
     */
    @Schema(description = "节点编号。", required = true, example = "1")
    private int node;

    /**
     * 审批意见。
     */
    @Schema(description = "审批意见。", required = false, example = "同意该申请")
    private String remark;

    /**
     * 任务ID。
     */
    @Schema(description = "任务ID。", required = true, example = "123456789")
    private Long taskId;
}
