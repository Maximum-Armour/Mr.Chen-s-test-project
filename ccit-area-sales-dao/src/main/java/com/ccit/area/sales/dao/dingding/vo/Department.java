package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 部门信息。
 */
@Schema(description = "部门信息")
@Data
@Builder
public class Department {

    /**
     * 部门ID。
     */
    @Schema(description = "部门ID。", required = true, example = "123")
    private Integer deptId;

    /**
     * 部门名称。
     */
    @Schema(description = "部门名称。", required = true, example = "销售部")
    private String name;

    /**
     * 节点状态。
     */
    @Schema(description = "节点状态。", required = true, example = "true")
    private Boolean nodeStatus;
}
