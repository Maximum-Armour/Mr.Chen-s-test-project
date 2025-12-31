package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门基础响应参数。
 */
@Schema(description = "部门基础响应参数")
@Data
public class DeptBaseResponse {

    /**
     * 部门 ID。
     */
    @Schema(description = "部门 ID。", required = true, example = "123")
    private int deptId;

    /**
     * 部门名称。
     */
    @Schema(description = "部门名称。", required = true, example = "销售部")
    private String name;

    /**
     * 父部门 ID。
     */
    @Schema(description = "父部门 ID。", required = true, example = "10")
    private int parentId;

    /**
     * 是否同步创建一个关联此部门的企业群。
     */
    @Schema(description = "是否同步创建一个关联此部门的企业群。", required = true, example = "true")
    private boolean createDeptGroup;

    /**
     * 部门群已经创建后，有新人加入部门是否会自动加入该群。
     */
    @Schema(description = "部门群已经创建后，有新人加入部门是否会自动加入该群。", required = true, example = "true")
    private boolean autoAddUser;
}
