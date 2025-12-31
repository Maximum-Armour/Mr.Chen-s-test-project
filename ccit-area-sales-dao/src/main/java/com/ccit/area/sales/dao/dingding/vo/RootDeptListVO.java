package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 部门列表详情。
 */
@Schema(description = "部门列表详情")
@Data
public class RootDeptListVO {

    /**
     * 响应码。
     */
    @Schema(description = "响应码。", required = true, example = "200")
    private String code;

    /**
     * 消息。
     */
    @Schema(description = "消息。", example = "请求成功")
    private String messages;

    /**
     * 上级节点名称。
     */
    @Schema(description = "上级节点名称。", example = "总部")
    private String parentNode;

    /**
     * 部门列表。
     */
    @Schema(description = "部门列表。")
    private List<Department> departmentList;
}
