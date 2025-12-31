package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 节点操作人选择范围。
 */
@Schema(description = "节点操作人选择范围")
@Data
public class ActorSelectionRange {

    /**
     * 审批指定成员列表。
     */
    @Schema(description = "审批指定成员列表。", required = false)
    private List<ApprovalMember> approvals;

    /**
     * 审批指定角色列表。
     */
    @Schema(description = "审批指定角色列表。", required = false)
    private List<Role> labels;
}
