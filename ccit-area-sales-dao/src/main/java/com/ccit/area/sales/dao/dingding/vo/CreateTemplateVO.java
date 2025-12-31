package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建模板接口返回参数。
 */
@Schema(description = "创建模板接口返回参数")
@Data
public class CreateTemplateVO {

    /**
     * 请求ID。
     */
    @Schema(description = "请求ID。", required = true, example = "requestId12345")
    private String requestId;

    /**
     * 返回码描述。
     */
    @Schema(description = "返回码描述。", required = true, example = "操作成功")
    private String errmsg;

    /**
     * 返回码。
     */
    @Schema(description = "返回码。", required = true, example = "0")
    private Integer errcode;

    /**
     * 创建接口结果。
     */
    @Schema(description = "创建接口结果。", required = true)
    private ProcessTopVO result;
}
