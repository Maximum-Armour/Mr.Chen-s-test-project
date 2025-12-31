package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 兔子审批请求参数
 */
@Schema(description = "兔子审批请求参数")
@Data
public class RabbitApprovalRequests {

    /**
     * 主键ID。
     */
    @Schema(description = "主键ID。", required = true, example = "123456789")
    private Long id;

    /**
     * 数据库表名。
     */
    @Schema(description = "数据库表名。", required = true, example = "sales_orders")
    private String databaseName;

    /**
     * 审批状态类型。
     */
    @Schema(description = "审批状态类型。", required = true, example = "PENDING")
    private String approvalStatus;

    /**
     * 状态。
     */
    @Schema(description = "状态。", required = true, example = "IN_PROGRESS")
    private String status;
}
