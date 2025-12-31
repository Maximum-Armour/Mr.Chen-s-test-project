package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审批任务。
 */
@Schema(description = "审批任务")
@Data
public class ApprovalTask {

    /**
     * 任务 ID。
     */
    @Schema(description = "任务 ID。", required = true, example = "123456789")
    private Long taskId;

    /**
     * 任务处理人。
     */
    @Schema(description = "任务处理人。", required = true, example = "userId12345")
    private String userId;

    /**
     * 任务状态。
     * NEW：未启动
     * RUNNING：处理中
     * PAUSED：暂停
     * CANCELED：取消
     * COMPLETED：完成
     * TERMINATED：终止
     */
    @Schema(description = "任务状态。", required = true, allowableValues = {"NEW", "RUNNING", "PAUSED", "CANCELED", "COMPLETED", "TERMINATED"}, example = "RUNNING")
    private String status;

    /**
     * 结果。
     * AGREE：同意
     * REFUSE：拒绝
     * REDIRECTED：转交
     */
    @Schema(description = "结果。", required = false, allowableValues = {"AGREE", "REFUSE", "REDIRECTED"}, example = "AGREE")
    private String result;

    /**
     * 开始时间。
     */
    @Schema(description = "开始时间。", required = true, example = "2023-10-01T12:34:56Z")
    private String createTime;

    /**
     * 结束时间。
     */
    @Schema(description = "结束时间。", required = false, example = "2023-10-01T14:34:56Z")
    private String finishTime;

    /**
     * 移动端任务 URL。
     */
    @Schema(description = "移动端任务 URL。", required = false, example = "https://mobile.example.com/task/12345")
    private String mobileUrl;

    /**
     * PC 端任务 URL。
     */
    @Schema(description = "PC 端任务 URL。", required = false, example = "https://pc.example.com/task/12345")
    private String pcUrl;

    /**
     * 实例 ID。
     */
    @Schema(description = "实例 ID。", required = true, example = "instanceId12345")
    private String processInstanceId;

    /**
     * 任务节点 ID。
     */
    @Schema(description = "任务节点 ID。", required = true, example = "activityId12345")
    private String activityId;
}
