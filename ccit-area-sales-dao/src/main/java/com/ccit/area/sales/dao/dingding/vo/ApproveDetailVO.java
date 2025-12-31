package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审批详情。
 */
@Schema(description = "审批详情")
@Data
public class ApproveDetailVO {

    /**
     * 审批人名字。
     */
    @Schema(description = "审批人名字。", required = true, example = "张三")
    private String userName;

    /**
     * 审批人编号。
     */
    @Schema(description = "审批人编号。", required = true, example = "userCode12345")
    private String userCode;

    /**
     * 钉钉编号。
     */
    @Schema(description = "钉钉编号。", required = true, example = "dingdingCode12345")
    private String dingdingCode;

    /**
     * 审批实例code。
     */
    @Schema(description = "审批实例code。", required = true, example = "approverId12345")
    private String approverId;

    /**
     * 审批实例状态。
     * NEW：未启动
     * RUNNING：处理中
     * PAUSED：暂停
     * CANCELED：取消
     * COMPLETED：完成
     * TERMINATED：终止
     */
    @Schema(description = "审批实例状态。", required = true, allowableValues = {"NEW", "RUNNING", "PAUSED", "CANCELED", "COMPLETED", "TERMINATED"}, example = "COMPLETED")
    private String approverStatus;

    /**
     * 流程分类。
     */
    @Schema(description = "流程分类。", required = true, example = "销售审批")
    private String processClassification;

    /**
     * 流程实例名称。
     */
    @Schema(description = "流程实例名称。", required = true, example = "项目审批实例")
    private String processInstanceName;

    /**
     * 业务id。
     */
    @Schema(description = "业务id。", required = true, example = "123456789")
    private Long businessId;

    /**
     * 创建时间。
     */
    @Schema(description = "创建时间。", required = true, example = "2023-10-01T12:34:56Z")
    private String createTime;

    /**
     * 订单编码。
     */
    @Schema(description = "订单编码。", required = false, example = "orderNumber12345")
    private String orderNumber;

    /**
     * 业务类型。
     */
    @Schema(description = "业务类型。", required = true, example = "销售合同")
    private String businessName;
}
