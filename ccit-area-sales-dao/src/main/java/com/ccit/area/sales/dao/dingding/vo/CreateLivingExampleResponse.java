package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 实例接口响应参数。
 */
@Schema(description = "实例接口响应参数")
@Data
public class CreateLivingExampleResponse {

    /**
     * 审批实例ID，用于标识具体的处理实例。
     */
    @Schema(description = "审批实例ID，用于标识具体的处理实例")
    private String instanceId;

    /**
     * 用户姓名。
     */
    @Schema(description = "用户姓名")
    private String realName;

    /**
     * 模板编号。
     */
    @Schema(description = "模板编号")
    private String processCode;

    /**
     * 订单编码。
     */
    @Schema(description = "订单编码")
    private String orderNumber;
}
