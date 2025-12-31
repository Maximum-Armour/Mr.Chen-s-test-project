package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 新增模板请求参数
 */
@Schema(description = "新增模板请求参数")
@Data
@Builder
public class ProcessFeatureConfig {

    /**
     * 特性列表。
     */
    @Schema(description = "特性列表。", required = true)
    private List<Features> features;
}
