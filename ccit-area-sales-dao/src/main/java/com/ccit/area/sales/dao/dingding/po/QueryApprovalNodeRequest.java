package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 查看流程节点请求参数
 */
@Schema(description = "查看流程节点请求参数")
@Data
@Builder
public class QueryApprovalNodeRequest {

    /**
     * 流程编码。
     */
    @Schema(description = "流程编码。", required = true, example = "PROCESS_CODE_001")
    private String processCode;

    /**
     * 部门ID。
     */
    @Schema(description = "部门ID。", required = false, example = "DEPT_ID_001")
    private String deptId;

    /**
     * 用户ID。
     */
    @Schema(description = "用户ID。", required = false, example = "USER_ID_001")
    private String userId;

    /**
     * 表单组件值列表。
     */
    @Schema(description = "表单组件值列表。", required = false)
    private List<FormComponentValues> formComponentValues;
}
