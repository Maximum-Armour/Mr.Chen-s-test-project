package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 特性配置
 */
@Schema(description = "特性配置")
@Data
@Builder
public class Features {

    /**
     * 特性名称。
     */
    @Schema(description = "特性名称", required = true, example = "AutoApproval")
    private String name;

    /**
     * PC端URL。
     */
    @Schema(description = "PC端URL", required = false, example = "https://example.com/pc")
    private String pcUrl;

    /**
     * 移动端URL。
     */
    @Schema(description = "移动端URL", required = false, example = "https://example.com/mobile")
    private String mobileUrl;

    /**
     * 运行类型。
     */
    @Schema(description = "运行类型", required = true, example = "WEB")
    private String runType;

    /**
     * 回调配置。
     */
    @Schema(description = "回调配置", required = true)
    private Callback callback;

    /**
     * 配置字符串。
     */
    @Schema(description = "配置字符串", required = false, example = "{\"key\":\"value\"}")
    private String config;
}
