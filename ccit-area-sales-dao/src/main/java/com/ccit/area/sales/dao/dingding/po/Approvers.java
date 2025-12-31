package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 审批人信息
 */
@Schema(description = "审批人信息")
@Data
@Builder
public class Approvers {

    /**
     * 审批类型，非必填，取值有 AND（会签）、OR（或签）、NONE（单人审批）。
     */
    @Schema(description = "审批类型，取值有 AND（会签）、OR（或签）、NONE（单人审批）", required = false)
    private String actionType;

    /**
     * 审批人 userId 列表，非必填，例如：["user001","user002"]。
     */
    @Schema(description = "审批人 userId 列表", required = false)
    private List<String> userIds;
}

