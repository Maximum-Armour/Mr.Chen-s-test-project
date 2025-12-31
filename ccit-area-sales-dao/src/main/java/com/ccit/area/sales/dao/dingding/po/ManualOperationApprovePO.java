package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManualOperationApprovePO {

    /**
     * 审批实例ID。
     */
    @Schema(description = "审批实例ID。", required = true, example = "INSTANCE12345")
    private String processInstanceId;

    /**
     * 审批意见，可为空。
     */
    @Schema(description = "审批意见，可为空。", required = false, example = "同意审批")
    private String remark;

    /**
     * 审批操作，同意或拒绝。
     */
    @Schema(description = "审批操作，同意或拒绝。", required = true, example = "agree")
    private String result;

    /**
     * 操作人userId。
     */
    @Schema(description = "操作人userId。", required = true, example = "USER12345")
    private String actionerUserId;

    /**
     * 任务ID。
     */
    @Schema(description = "任务ID。", required = true, example = "12345")
    private Long taskId;

    /**
     * 文件对象。
     */
    @Schema(description = "文件对象。", required = false)
    private ManualOperationfile file;
}
