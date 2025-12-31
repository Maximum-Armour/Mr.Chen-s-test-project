package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
  * 描述 : “产品竞价删除”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月9日 下午1:43:14
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SalesBiddingDeleteDTO
 */
@Data
@Schema(description = "【产品竞价删除】接受参数实体类")
public class SalesBiddingDeleteDTO {

    /**
     * 产品定价ID数组
     */
    @Schema(description = "产品竞价ID数组")
    private List<Long> ids;
}
