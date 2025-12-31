package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 操作记录。
 */
@Schema(description = "操作记录")
@Data
public class OperationRecord {

    /**
     * 操作人 userId。
     */
    @Schema(description = "操作人 userId。", required = true, example = "userId123")
    private String userId;

    /**
     * 操作时间。
     */
    @Schema(description = "操作时间。", example = "2023-10-01T12:34:56Z")
    private String date;

    /**
     * 操作类型。
     * EXECUTE_TASK_NORMAL：正常执行任务
     * EXECUTE_TASK_AGENT：代理人执行任务
     * APPEND_TASK_BEFORE：前加签任务
     * APPEND_TASK_AFTER：后加签任务
     * REDIRECT_TASK：转交任务
     * START_PROCESS_INSTANCE：发起流程实例
     * TERMINATE_PROCESS_INSTANCE：终止(撤销)流程实例
     * FINISH_PROCESS_INSTANCE：结束流程实例
     * ADD_REMARK：添加评论
     * REDIRECT_PROCESS：审批退回
     * PROCESS_CC：抄送
     */
    @Schema(description = "操作类型。", required = true, example = "EXECUTE_TASK_NORMAL", allowableValues = {
            "EXECUTE_TASK_NORMAL", "EXECUTE_TASK_AGENT", "APPEND_TASK_BEFORE", "APPEND_TASK_AFTER", "REDIRECT_TASK",
            "START_PROCESS_INSTANCE", "TERMINATE_PROCESS_INSTANCE", "FINISH_PROCESS_INSTANCE", "ADD_REMARK",
            "REDIRECT_PROCESS", "PROCESS_CC"
    })
    private String type;

    /**
     * 操作结果。
     * AGREE：同意
     * REFUSE：拒绝
     * NONE：未处理
     */
    @Schema(description = "操作结果。", required = true, example = "AGREE", allowableValues = {"AGREE", "REFUSE", "NONE"})
    private String result;

    /**
     * 评论内容。
     * 审批操作附带评论时才返回该字段。
     */
    @Schema(description = "评论内容。审批操作附带评论时才返回该字段。", example = "这是一个评论。")
    private String remark;

    /**
     * 评论附件列表。
     */
    @Schema(description = "评论附件列表。")
    private List<Attachment> attachments;

    /**
     * 抄送人 userId 列表。
     */
    @Schema(description = "抄送人 userId 列表。", example = "[\"userId1\", \"userId2\"]")
    private List<String> ccUserIds;

    /**
     * 任务节点 ID。
     */
    @Schema(description = "任务节点 ID。", example = "activityId123")
    private String activityId;

    /**
     * 任务节点名称。
     */
    @Schema(description = "任务节点名称。", example = "任务节点名称")
    private String showName;

    /**
     * 单个图片链接。
     */
    @Schema(description = "单个图片链接。", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> images;
}
