package com.ccit.area.sales.dao.dingding.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取企业 Token 响应参数。
 */
@Schema(description = "获取企业 Token 响应参数")
@Data
public class GetTokenResponse {

    /**
     * 返回码。
     */
    @Schema(description = "返回码。", required = true, example = "0")
    private Integer errcode;

    /**
     * 访问令牌。
     */
    @Schema(description = "访问令牌。", required = true, example = "your_access_token_here")
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 错误信息。
     */
    @Schema(description = "错误信息。", example = "ok")
    private String errmsg;

    /**
     * 访问令牌的有效期（秒）。
     */
    @Schema(description = "访问令牌的有效期（秒）。", required = true, example = "7200")
    @JsonProperty("expires_in")
    private Integer expiresIn;
}
