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
public class TemplateConfig {

    /**
     * 是否隐藏。
     */
    @Schema(description = "是否隐藏。", required = false, example = "false")
    private Boolean hidden;

    /**
     * 移动端创建实例的 URL。
     */
    @Schema(description = "移动端创建实例的 URL。", required = false, example = "https://example.com/mobile-create-instance")
    private String createInstanceMobileUrl;

    /**
     * PC 端创建实例的 URL。
     */
    @Schema(description = "PC 端创建实例的 URL。", required = false, example = "https://example.com/pc-create-instance")
    private String createInstancePcUrl;

    /**
     * 模板编辑 URL。
     */
    @Schema(description = "模板编辑 URL。", required = false, example = "https://example.com/template-edit")
    private String templateEditUrl;

    /**
     * 是否禁用发送卡片。
     */
    @Schema(description = "是否禁用发送卡片。", required = false, example = "false")
    private Boolean disableSendCard;
}
