package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 *
  * 描述 : “竞价审核”DTO
  * 创建人 : tb
  * 创建时间 : 2024年9月12日 下午4:43:14
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : ApplyBiddingSubmitDTO
 */
@Data
@Schema(description = "【竞价审核】接受参数实体类")
public class ApplyBiddingSubmitDTO {

    /**
     * 竞价审核ID
     */
    @Schema(description = "竞价审核ID")
    private Long id;

    /**
     * 竞价审核状态
     */
    @Schema(description = "竞价审核状态")
    private String status;
}
