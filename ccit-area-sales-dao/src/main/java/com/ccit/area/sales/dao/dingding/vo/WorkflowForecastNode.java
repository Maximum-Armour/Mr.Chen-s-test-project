package com.ccit.area.sales.dao.dingding.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 工作流节点流。
 */
@Data
public class WorkflowForecastNode {
    /**
     * 节点 id。
     */
    private String activityId;
    
    /**
     * 节点出线 id。
     */
    private String outId;
}