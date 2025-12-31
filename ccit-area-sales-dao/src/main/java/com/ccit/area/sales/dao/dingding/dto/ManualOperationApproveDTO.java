package com.ccit.area.sales.dao.dingding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "手工操作审批DTO")
@Data
public class ManualOperationApproveDTO {

    @Schema(description = "审批实例ID", required = true)
    private String processInstanceId;

    @Schema(description = "审批意见", required = false)
    private String remark;

    @Schema(description = "审批操作", required = true)
    private String result;

    @Schema(description = "审批人userID", required = true)
    private String userId;

    @Schema(description = "节点id", required = true)
    private Long taskId;
}
