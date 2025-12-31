package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 新增模板请求参数
 */
@Schema(description = "新增模板请求参数")
@Data
@Builder
public class Callback {

    /**
     * 应用的唯一标识。
     */
    @Schema(description = "应用的唯一标识", required = true)
    private String appUuid;

    /**
     * 应用的API密钥。
     */
    @Schema(description = "应用的API密钥", required = true)
    private String apiKey;

    /**
     * 版本号。
     */
    @Schema(description = "版本号", required = true)
    private String version;
}
