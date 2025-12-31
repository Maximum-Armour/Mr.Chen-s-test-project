package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项信息。
 */
@Schema(description = "字典项信息")
@Data
public class Dictionary {

    /**
     * 字段名称。
     */
    @Schema(description = "字段名称。", required = true, example = "status")
    private String fieldName;

    /**
     * 字段值。
     */
    @Schema(description = "字段值。", required = true, example = "active")
    private String fieldValue;

    /**
     * 字段类型。
     */
    @Schema(description = "字段类型。", required = true, example = "String")
    private String fieldType;
}
