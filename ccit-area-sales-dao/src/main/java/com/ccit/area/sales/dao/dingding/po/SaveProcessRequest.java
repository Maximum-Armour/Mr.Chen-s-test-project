package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 创建模板接口请求参数
 */
@Schema(description = "创建模板接口请求参数")
@Data
@Builder
public class SaveProcessRequest {

    /**
     * 应用标识。
     */
    @Schema(description = "应用标识。", required = true, example = "123456789")
    private String agentid;

    /**
     * 审批流的唯一码。
     */
    @Schema(description = "审批流的唯一码。", required = true, example = "PROCESS_CODE_001")
    private String processCode;

    /**
     * 审批模板名称。
     */
    @Schema(description = "审批模板名称。", required = true, example = "销售审批模板")
    private String name;

    /**
     * 审批模板描述。
     */
    @Schema(description = "审批模板描述。", required = false, example = "用于销售订单的审批流程")
    private String description;

    /**
     * 表单组件列表。
     */
    @Schema(description = "表单组件列表。", required = true)
    private List<FormComponentPO> form_component_list;

    /**
     * 模拟模式（可选，true/false）。
     */
    @Schema(description = "模拟模式（可选，true/false）。", required = false, example = "false")
    private Boolean fakeMode;
}
