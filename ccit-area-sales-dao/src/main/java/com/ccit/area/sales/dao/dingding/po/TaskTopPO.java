package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 创建待办事项
 */
@Schema(description = "创建待办事项")
@Data
@Builder
public class TaskTopPO {

    /**
     * 钉钉用户 userId。
     */
    @Schema(description = "钉钉用户 userId。", required = true, example = "user12345")
    private String userId;

    /**
     * 待办事项跳转 URL。
     */
    @Schema(description = "待办事项跳转 URL。", required = true, example = "https://example.com/task-detail")
    private String url;

    /**
     * 待办事项执行人的 userid。
     */
    @Schema(description = "待办事项执行人的 userid。", required = false, example = "executor67890")
    private String customData;
}
