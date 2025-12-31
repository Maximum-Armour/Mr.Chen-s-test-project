package com.ccit.area.sales.dao.dingding.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 创建模板接口返回参数
 */
@Data
public class ProcessTopVO {
    /**
     * 实例编号
     */
    @JsonProperty(value = "process_code")
    private String processCode;

}
