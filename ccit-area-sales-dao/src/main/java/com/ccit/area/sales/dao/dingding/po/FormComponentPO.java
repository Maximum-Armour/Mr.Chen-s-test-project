package com.ccit.area.sales.dao.dingding.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 创建模板接口请求参数
 */
@Schema(description = "创建模板接口请求参数")
@Data
@Builder
public class FormComponentPO {

    /**
     * 组件名称。
     */
    @Schema(description = "组件名称", required = true, example = "姓名")
    private String component_name;

    /**
     * 组件属性。
     */
    @Schema(description = "组件属性", required = true)
    private FormComponentPropPO props;
}
