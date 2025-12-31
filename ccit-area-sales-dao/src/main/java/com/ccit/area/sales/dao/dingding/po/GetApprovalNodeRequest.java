package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 获取审批节点请求参数
 */
@Schema(description = "获取审批节点请求参数")
@Data
@Builder
public class GetApprovalNodeRequest {

    /**
     * 审批模板的process_code，从审批模板编辑页面的URL中获取。
     */
    @Schema(description = "审批模板的process_code，从审批模板编辑页面的URL中获取。", required = true, example = "PROC123456")
    private String processCode;

    /**
     * 即将发起审批单的员工所在部门ID。
     */
    @Schema(description = "即将发起审批单的员工所在部门ID。", required = true, example = "12345")
    private String deptId;

    /**
     * 即将发起审批单的员工userId值。
     */
    @Schema(description = "即将发起审批单的员工userId值。", required = true, example = "USER12345")
    private String userId;

    /**
     * 表单组件值列表。
     */
    @Schema(description = "表单组件值列表。", required = false)
    private List<FormComponentValues> formComponentValues;
}
