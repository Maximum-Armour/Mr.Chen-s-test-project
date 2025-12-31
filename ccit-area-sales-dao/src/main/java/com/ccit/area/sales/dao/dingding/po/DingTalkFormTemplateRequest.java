package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 新增模板请求参数
 */
@Schema(description = "新增模板请求参数")
@Data
@Builder
public class DingTalkFormTemplateRequest {

    /**
     * 流程编码。
     */
    @Schema(description = "流程编码", required = true, example = "PC12345")
    private String processCode;

    /**
     * 模板名称。
     */
    @Schema(description = "模板名称", required = true, example = "销售审批模板")
    private String name;

    /**
     * 模板描述。
     */
    @Schema(description = "模板描述", required = false, example = "这是一个销售审批模板")
    private String description;

    /**
     * 表单组件列表。
     */
    @Schema(description = "表单组件列表", required = true)
    private List<FormComponent> formComponents;

    /**
     * 流程特性配置。
     */
    @Schema(description = "流程特性配置", required = true)
    private ProcessFeatureConfig processFeatureConfig;

    /**
     * 模板配置。
     */
    @Schema(description = "模板配置", required = true)
    private TemplateConfig templateConfig;
}
