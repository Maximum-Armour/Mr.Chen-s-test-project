package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取审批实例响应参数。
 */
@Schema(description = "获取审批实例响应参数")
@Data
public class GetApprovalLivingExample {

    /**
     * 返回结果。
     */
    @Schema(description = "返回结果。", required = true)
    private ApprovalResult result;

    /**
     * 调用是否成功。
     * true：成功
     * false：失败
     */
    @Schema(description = "调用是否成功。true：成功；false：失败。", required = true, example = "true")
    private boolean success;
}
