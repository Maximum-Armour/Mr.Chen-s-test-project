package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 创建实例请求参数
 */
@Schema(description = "创建实例请求参数")
@Data
public class TargetSelectActioners {

    /**
     * 操作人 userId 列表。
     */
    @Schema(description = "操作人 userId 列表。")
    private List<String> userId;

    /**
     * 自选节点的规则 key。
     */
    @Schema(description = "自选节点的规则 key。")
    private String actionerKey;
}
