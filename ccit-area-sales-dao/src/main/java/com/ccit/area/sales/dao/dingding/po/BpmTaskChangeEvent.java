package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BpmTaskChangeEvent {

    /**
     * 事件类型。
     */
    @Schema(description = "事件类型", required = true)
    private String eventType;

    /**
     * 事件ID。
     */
    @Schema(description = "事件ID", required = true)
    private String eventId;

    /**
     * 业务分类ID。
     */
    @Schema(description = "业务分类ID", required = true)
    private String bizCategoryId;

    /**
     * 审批实例id。
     */
    @Schema(description = "审批实例id", required = true)
    private String processInstanceId;

    /**
     * 审批实例对应的企业corpId。
     */
    @Schema(description = "审批实例对应的企业corpId", required = true)
    private String corpId;

    /**
     * 创建审批实例时间。时间戳，单位毫秒。
     */
    @Schema(description = "创建审批实例时间，时间戳，单位毫秒", required = true)
    private long createTime;

    /**
     * 结束审批实例时间。时间戳，单位毫秒。
     */
    @Schema(description = "结束审批实例时间，时间戳，单位毫秒", required = false)
    private long finishTime;

    /**
     * 实例标题。
     */
    @Schema(description = "实例标题", required = true)
    private String title;

    /**
     * 审批类型。
     * finish：审批正常结束（同意或拒绝）
     * terminate：审批终止（发起人撤销审批单）
     */
    @Schema(description = "审批类型，取值有 finish（审批正常结束）和 terminate（审批终止）", required = true)
    private String type;

    /**
     * 发起审批实例的员工userId。
     */
    @Schema(description = "发起审批实例的员工userId", required = true)
    private String staffId;

    /**
     * 审批实例url，可在钉钉内跳转到审批页面。
     */
    @Schema(description = "审批实例url，可在钉钉内跳转到审批页面", required = true)
    private String url;

    /**
     * 正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值。
     */
    @Schema(description = "正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值", required = false)
    private String result;

    /**
     * 审批模板的唯一码。
     */
    @Schema(description = "审批模板的唯一码", required = true)
    private String processCode;
}
