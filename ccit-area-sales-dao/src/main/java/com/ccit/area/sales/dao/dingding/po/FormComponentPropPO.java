package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 表单组件属性配置
 */
@Schema(description = "表单组件属性配置")
@Data
@Builder
public class FormComponentPropPO {

    /**
     * 组件唯一标识。
     */
    @Schema(description = "组件唯一标识", required = true, example = "componentId123")
    private String id;

    /**
     * 标签文本。
     */
    @Schema(description = "标签文本", required = true, example = "姓名")
    private String label;

    /**
     * 是否必填。
     */
    @Schema(description = "是否必填", required = true, example = "true")
    private Boolean required;
}
