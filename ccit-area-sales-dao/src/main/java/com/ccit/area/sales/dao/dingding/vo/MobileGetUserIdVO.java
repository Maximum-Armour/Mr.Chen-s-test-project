package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取用户ID响应参数。
 */
@Schema(description = "获取用户ID响应参数")
@Data
public class MobileGetUserIdVO {

    /**
     * 请求ID。
     */
    @Schema(description = "请求ID。", required = true, example = "requestId123")
    private String requestId;

    /**
     * 返回码。
     */
    @Schema(description = "返回码。", required = true, example = "0")
    private int errcode;

    /**
     * 返回码描述。
     */
    @Schema(description = "返回码描述。", example = "ok")
    private String errmsg;

    /**
     * 结果对象。
     */
    @Schema(description = "结果对象。", required = true)
    private MobileGetUserIdResult result;
}
