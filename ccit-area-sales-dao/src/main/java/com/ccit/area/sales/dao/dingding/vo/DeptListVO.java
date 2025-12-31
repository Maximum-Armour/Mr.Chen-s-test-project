package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 部门列表响应参数。
 */
@Schema(description = "部门列表响应参数")
@Data
public class DeptListVO {

    /**
     * 请求 ID。
     */
    @Schema(description = "请求 ID。", required = true, example = "requestId12345")
    private String requestId;

    /**
     * 返回码。
     */
    @Schema(description = "返回码。", required = true, example = "0")
    private int errcode;

    /**
     * 调用失败时返回的错误信息。
     */
    @Schema(description = "调用失败时返回的错误信息。", example = "操作成功")
    private String errmsg;

    /**
     * 部门列表。
     */
    @Schema(description = "部门列表。", required = true)
    private List<DeptBaseResponse> result;
}
