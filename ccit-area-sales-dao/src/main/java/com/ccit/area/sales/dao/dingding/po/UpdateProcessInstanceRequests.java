package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 更新审核状态请求参数
 */
@Schema(description = "更新审核状态请求参数")
@Data
@Builder
public class UpdateProcessInstanceRequests {

    /**
     * 实例ID。
     */
    @Schema(description = "实例ID。", required = true, example = "processInstanceId12345")
    private String processInstanceId;

    /**
     * 实例状态。
     */
    @Schema(description = "实例状态。", required = true, example = "COMPLETED")
    private String status;

    /**
     * 实例结果。
     */
    @Schema(description = "实例结果。", required = false, example = "APPROVED")
    private String result;

    /**
     * 抄送人 userId 列表。
     */
    @Schema(description = "抄送人 userId 列表。", required = false, example = "[\"user1\", \"user2\"]")
    private List<String> notifiers;
}
