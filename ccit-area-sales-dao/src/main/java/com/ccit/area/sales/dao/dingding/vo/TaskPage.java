package com.ccit.area.sales.dao.dingding.vo;

import lombok.Data;

import java.util.List;

/**
 * 查询审核状态响应参数
 */
@Data
public class TaskPage {
    // 是否还有下一页。
    private boolean hasMore;

    // 任务列表。
    private List<Task> list;

}
