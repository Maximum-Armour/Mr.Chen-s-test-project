package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 根部门列表返回结果。
 */
@Schema(description = "根部门列表返回结果")
@Data
public class RootDeptListsVO {

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
     * 部门列表。
     */
    @Schema(description = "部门列表。")
    private List<RootDeptListVO> departmentLists;
}
