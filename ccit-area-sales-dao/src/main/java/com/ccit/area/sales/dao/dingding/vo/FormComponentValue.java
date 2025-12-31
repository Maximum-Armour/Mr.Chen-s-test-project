package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 表单组件值信息。
 */
@Schema(description = "表单组件值信息")
@Data
public class FormComponentValue {

    /**
     * 组件 ID。
     */
    @Schema(description = "组件 ID。", required = true, example = "comp12345")
    private String id;

    /**
     * 组件名称。
     */
    @Schema(description = "组件名称。", required = true, example = "姓名")
    private String name;

    /**
     * 标签值。
     */
    @Schema(description = "标签值。", example = "张三")
    private String value;

    /**
     * 标签扩展值。
     */
    @Schema(description = "标签扩展值。", example = "额外信息")
    private String extValue;

    /**
     * 组件类型。
     */
    @Schema(description = "组件类型。", required = true, example = "text")
    private String componentType;

    /**
     * 组件别名。
     */
    @Schema(description = "组件别名。", example = "aliasName")
    private String bizAlias;

    /**
     * 创建时间。
     */
    @Schema(description = "创建时间。", example = "2023-10-01T12:34:56Z")
    private String createTime;
}
