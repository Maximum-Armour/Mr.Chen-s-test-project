package com.ccit.area.sales.dao.dto.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "【订单行表】接受参数实体类")
public class OrderLinesDTO {
    /**
     * 订单汇总ID数组
     */

    private Long id;
    /**
     * 产品编码
     */
    @Schema(description = "订单编码")
    private String orderNo;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 订单行编码
     */
    @Schema(description = "订单行编码")
    private String orderLineNo;
}
