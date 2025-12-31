package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 审批指定角色。
 */
@Schema(description = "审批指定角色")
@Data
@Builder
public class Role {

    /**
     * 角色 id。
     */
    @Schema(description = "角色 id。", required = true, example = "ROLE123456")
    private String labels;

    /**
     * 角色名字。
     */
    @Schema(description = "角色名字。", required = true, example = "管理员")
    private String labelNames;

    /**
     * 是否允许多选，还是仅允许选一人。
     */
    @Schema(description = "是否允许多选，还是仅允许选一人。true：允许多选；false：仅允许选一人。", required = true, example = "true")
    private Boolean allowedMulti;
}
