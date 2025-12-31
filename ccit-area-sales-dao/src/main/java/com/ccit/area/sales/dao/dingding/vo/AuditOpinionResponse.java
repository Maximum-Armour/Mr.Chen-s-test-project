package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审核意见响应。
 */
@Schema(description = "审核意见响应")
@Data
public class AuditOpinionResponse {

    /**
     * 审核意见。
     */
    @Schema(description = "审核意见。", required = true, example = "同意，无异议。")
    private String auditOpinion;

    /**
     * 审核人。
     */
    @Schema(description = "审核人。", required = true, example = "张三")
    private String auditor;

    /**
     * 审核时间。
     */
    @Schema(description = "审核时间。", required = true, example = "2023-10-01T12:34:56Z")
    private String auditTime;
}
