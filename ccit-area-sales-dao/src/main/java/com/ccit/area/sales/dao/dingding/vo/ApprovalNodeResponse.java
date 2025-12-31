package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 审批节点响应。
 */
@Schema(description = "审批节点响应")
@Data
public class ApprovalNodeResponse {

    /**
     * 节点位置。
     */
    @Schema(description = "节点位置。", required = true, example = "1")
    private int node;

    /**
     * 节点操作人名称。
     */
    @Schema(description = "节点操作人名称。", required = true, example = "张三")
    private String nodeName;

    /**
     * 节点操作人类型。
     */
    @Schema(description = "节点操作人类型。", required = true, example = "USER")
    private String nodeType;

    /**
     * 节点审批人用户名。
     */
    @Schema(description = "节点审批人用户名。", required = true, example = "user12345")
    private String userName;

    /**
     * 节点审批人真实姓名。
     */
    @Schema(description = "节点审批人真实姓名。", required = true, example = "张三")
    private String realName;
}
