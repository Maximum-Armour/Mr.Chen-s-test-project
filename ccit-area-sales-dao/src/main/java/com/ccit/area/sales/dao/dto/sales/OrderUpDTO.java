package com.ccit.area.sales.dao.dto.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "【订单】接受参数实体类")
public class OrderUpDTO {

    private Long id;
    private BigDecimal transactionPrice;
    private BigDecimal turnoverQuantity;

    private String status;

}
