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
public class Props {

    /**
     * 标签文本。
     */
    @Schema(description = "标签文本。", required = false, example = "姓名")
    private String label;

    /**
     * 输入框占位符文本。
     */
    @Schema(description = "输入框占位符文本。", required = false, example = "请输入姓名")
    private String placeholder;

    /**
     * 组件ID。
     */
    @Schema(description = "组件ID。", required = true, example = "component_001")
    private String componentId;

    /**
     * 是否必填项。
     */
    @Schema(description = "是否必填项。", required = true, example = "true")
    private Boolean required;

    /**
     * 业务别名。
     */
    @Schema(description = "业务别名。", required = false, example = "name_field")
    private String bizAlias;
}
