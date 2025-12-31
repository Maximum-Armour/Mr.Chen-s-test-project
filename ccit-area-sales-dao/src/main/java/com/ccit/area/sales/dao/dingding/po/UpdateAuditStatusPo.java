package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 更新审核状态po类
 */
@Schema(description = "更新审核状态po类")
@Data
@Builder
public class UpdateAuditStatusPo {

    /**
     * OA审批任务列表。
     */
    @Schema(description = "OA审批任务列表。", required = true)
    private List<UpdateProcessInstanceRequests> updateProcessInstanceRequests;
}
