package com.ccit.area.sales.dao.dto.sales;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *
  * 描述 : “订单审核”DTO
  * 创建人 : cf
  * 创建时间 : 2024年9月25日 下午14:29:30
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.sales
  * 类名 : OrderSummaryDTO
 */
@Data
@Schema(description = "【订单汇总】接受参数实体类")
public class OrderSummaryDTO {


    /**
     * 订单汇总ID数组
     */

    private List<Long> ids;
    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
    private List<String> materielNo;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 订单行编码
     */
    @Schema(description = "订单行编码")
    private List<String> orderLineNo;
}
