package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 创建实例请求参数
 */
@Schema(description = "创建实例请求参数")
@Data
@Builder
public class Details {
    /**
     * 控件id，非必填。
     */
    @Schema(description = "控件id，非必填", required = false, example = "controlId123")
    private String id;

    /**
     * 控件别名，非必填。
     */
    @Schema(description = "控件别名，非必填", required = false, example = "aliasName")
    private String bizAlias;

    /**
     * 控件名称，非必填。
     */
    @Schema(description = "控件名称，非必填", required = false, example = "Control Name")
    private String name;

    /**
     * 控件值，非必填。
     */
    @Schema(description = "控件值，非必填", required = false, example = "Control Value")
    private String value;

    /**
     * 控件扩展值，非必填。
     */
    @Schema(description = "控件扩展值，非必填", required = false, example = "extValue123")
    private String extValue;

    /**
     * 控件类型，非必填。
     * 取值同 FormComponentValue 中的 componentType。
     */
    @Schema(description = "控件类型，非必填。取值同 FormComponentValue 中的 componentType", required = false, example = "TEXT")
    private String componentType;

    /**
     * 子控件列表，非必填，最大列表长度为150。
     */
    @Schema(description = "子控件列表，非必填，最大列表长度为150", required = false)
    private List<Details> details;
}
