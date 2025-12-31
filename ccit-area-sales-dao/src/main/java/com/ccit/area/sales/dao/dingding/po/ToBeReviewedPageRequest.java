package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 待审核页面请求参数
 */
@Schema(description = "待审核页面请求参数")
@Data
public class ToBeReviewedPageRequest {

    /**
     * 用户编码。
     */
    @Schema(description = "用户编码。", required = true, example = "user12345")
    private String userCode;

    /**
     * 审核状态。
     */
    @Schema(description = "审核状态。", required = true, example = "PENDING")
    private String approverStatus;
}
