package com.ccit.area.sales.dao.dingding.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 查询审核状态响应参数
 */
@Data
@Builder
public class QueryApprovalStatusVo {

    // 请求ID，用于标识特定的请求。
    private String requestId;

    // 分页结果封装。
    private TaskPage taskPage;


}
