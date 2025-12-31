package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 审批指定成员。
 */
@Schema(description = "审批指定成员")
@Data
@Builder
public class ApprovalMember {

    /**
     * 员工 userId。
     */
    @Schema(description = "员工 userId。", required = true, example = "userId12345")
    private String workNo;

    /**
     * 员工姓名。
     */
    @Schema(description = "员工姓名。", required = true, example = "张三")
    private String userName;
}
