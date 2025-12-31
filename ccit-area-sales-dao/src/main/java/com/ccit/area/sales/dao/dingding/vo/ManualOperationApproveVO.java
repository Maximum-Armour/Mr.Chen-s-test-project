package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 手动操作审批响应参数。
 */
@Schema(description = "手动操作审批响应参数")
@Data
public class ManualOperationApproveVO {

    /**
     * 是否同意审批任务。
     * true：同意
     * false：不同意
     */
    @Schema(description = "是否同意审批任务。true：同意；false：不同意。", required = true, example = "true")
    private Boolean result;

    /**
     * 接口调用是否成功。
     * true：成功
     * false：失败
     */
    @Schema(description = "接口调用是否成功。true：成功；false：失败。", required = true, example = "true")
    private Boolean success;
}
