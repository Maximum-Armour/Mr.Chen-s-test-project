package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
  * 描述 : “产品竞价提交”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月10日 下午3:43:14
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SalesBiddingSubmitDTO
 */
@Data
@Schema(description = "【产品竞价提交】接受参数实体类")
public class SalesBiddingSubmitDTO {

    /**
     * 产品竞价ID数组
     */
    @Schema(description = "产品竞价ID数组")
    private List<Long> ids;

    /**
     * 竞价审核状态
     */
    @Schema(description = "竞价审核状态")
    private String status;
}
