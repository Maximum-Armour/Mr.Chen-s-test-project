package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 兔子审批请求参数
 */
@Schema(description = "兔子审批请求参数")
@Data
public class RabbitApprovalRequest {

    /**
     * 主键ID。
     */
    @Schema(description = "主键ID。")
    private Long id;

    /**
     * 数据库表名。
     */
    @Schema(description = "数据库表名。")
    private String databaseName;

    /**
     * 审批状态类型。
     */
    @Schema(description = "审批状态类型。")
    private String approvalStatus;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 状态。
     */
    @Schema(description = "状态。")
    private String status;

    /**
     * 单号。
     */
    @Schema(description = "单号。")
    private String workflowId;
}
