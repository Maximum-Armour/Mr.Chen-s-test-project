package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 创建实例钉钉回调请求参数
 */
@Schema(description = "创建实例钉钉回调请求参数")
@Data
public class CreateBackLogCallback {

    /**
     * 审批结果。
     */
    @Schema(description = "审批结果", required = true)
    private String outResult;

    /**
     * 流程实例ID。
     */
    @Schema(description = "流程实例ID", required = true)
    private String processInstanceId;

    /**
     * 活动节点标识符。
     */
    @Schema(description = "活动节点标识符", required = true)
    private String activityId;

    /**
     * 企业ID。
     */
    @Schema(description = "企业ID", required = true)
    private String corpId;

    /**
     * 附加数据。
     */
    @Schema(description = "附加数据", required = false)
    private List<Object> data;

    /**
     * 备注。
     */
    @Schema(description = "备注", required = false)
    private String remark;

    /**
     * 审批标题。
     */
    @Schema(description = "审批标题", required = true)
    private String title;

    /**
     * OA审批任务ID。
     */
    @Schema(description = "OA审批任务ID", required = true)
    private Long taskId;

    /**
     * 审批人ID。
     */
    @Schema(description = "审批人ID", required = true)
    private String operator;
}
