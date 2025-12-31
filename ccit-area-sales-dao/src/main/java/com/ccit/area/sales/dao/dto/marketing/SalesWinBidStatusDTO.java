package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class SalesWinBidStatusDTO {

    /**
     * 产品竞价ID数组
     */
    @Schema(description = "产品竞价ID数组")
    private List<Long> ids;

    /**
     * 竞价编号
     */
    private String tenderNumber;

    /**
     * 竞价审核状态
     */
    @Schema(description = "竞价审核状态")
    private String status;

}
