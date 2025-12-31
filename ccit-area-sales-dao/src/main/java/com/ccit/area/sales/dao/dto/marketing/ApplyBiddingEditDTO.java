package com.ccit.area.sales.dao.dto.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
  * 描述 : “竞价申请修改”DTO
  * 创建人 : tb
  * 创建时间 : 2024年10月16日 下午1:42:13
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : ApplyBiddingEditDTO
 */
@Data
@Schema(description = "【竞价申请修改】接受参数实体类")
public class ApplyBiddingEditDTO {

    /**
     * 竞价审核ID
     */
    @Schema(description = "竞价审核ID")
    private Long id;

    /**
     * 成交价（元）
     */
    @Schema(description = "成交价格")
    private BigDecimal transactionPrice;

    /**
     * 成交数量（吨）
     */
    @Schema(description = "成交数量")
    private BigDecimal transactionQuantity;
}
