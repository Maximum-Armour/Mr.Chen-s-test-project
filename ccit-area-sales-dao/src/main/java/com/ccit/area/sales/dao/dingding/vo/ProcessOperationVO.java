package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

/**
 * 流程操作记录。
 */
@Schema(description = "流程操作记录")
@Data
public class ProcessOperationVO {

    /**
     * 订单编码。
     */
    @Schema(description = "订单编码。", required = true, example = "ORDER123456")
    private String orderNumber;

    /**
     * 上级模块。
     */
    @Schema(description = "上级模块。", example = "销售管理")
    private String parentName;

    /**
     * 流程。
     */
    @Schema(description = "流程。", example = "销售审批流程")
    private String technologicalProcess;

    /**
     * 操作者。
     */
    @Schema(description = "操作者。", example = "张三")
    private String operatorName;

    /**
     * 用户账号。
     */
    @Schema(description = "用户账号。", example = "zhangsan")
    private String userName;

    /**
     * 操作时间。
     */
    @Schema(description = "操作时间。", example = "2023-10-01T12:34:56Z")
    private String operatorTime;

    /**
     * 操作。
     */
    @Schema(description = "操作。", example = "提交审批")
    private String operation;

    /**
     * 数据库表名。
     */
    @Schema(description = "数据库表名。", example = "sales_orders")
    private String dataName;

    /**
     * 创建时间。
     */
    @Schema(description = "创建时间。", example = "2023-10-01T12:34:56Z")
    private Date gmtCreate;

    /**
     * 钉钉实例id。
     */
    @Schema(description = "钉钉实例id。", example = "INSTANCE123456")
    private String approvalId;

    /**
     * 审批模板编号。
     */
    @Schema(description = "审批模板编号。", example = "TEMPLATE123456")
    private String processCode;

    /**
     * 业务id。
     */
    @Schema(description = "业务id。", example = "1234567890")
    private Long businessId;
}
