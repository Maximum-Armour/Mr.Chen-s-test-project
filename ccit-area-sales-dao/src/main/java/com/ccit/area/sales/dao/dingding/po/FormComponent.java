package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 表单组件
 */
@Schema(description = "表单组件")
@Data
@Builder
public class FormComponent {

    /**
     * 组件类型，支持的控件参考本文FormComponent参数补充说明，单一表单最大组件个数不超过200。
     * 示例值: TextField, TextareaField, etc.
     */
    @Schema(description = "组件类型，支持的控件参考本文FormComponent参数补充说明，单一表单最大组件个数不超过200。示例值: TextField, TextareaField, etc.", required = true, example = "TextField")
    private String componentType;

    /**
     * 组件属性。
     */
    @Schema(description = "组件属性", required = true)
    private Props props;
}
