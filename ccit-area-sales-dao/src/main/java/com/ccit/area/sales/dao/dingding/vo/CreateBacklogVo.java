package com.ccit.area.sales.dao.dingding.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 创建待办审批响应参数。
 */
@Schema(description = "创建待办审批响应参数")
@Data
public class CreateBacklogVo {

    /**
     * 审批节点信息列表。
     */
    @Schema(description = "审批节点信息列表。", required = true)
    private List<QueryApproverNodeResponse> result;

    /**
     * 操作是否成功。
     */
    @Schema(description = "操作是否成功。", required = true, example = "true")
    private Boolean success;
}
