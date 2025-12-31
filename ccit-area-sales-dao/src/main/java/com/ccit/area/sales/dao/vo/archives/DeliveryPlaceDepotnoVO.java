package com.ccit.area.sales.dao.vo.archives;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "【提货地仓库分页列表】返回结果实体类")
public class DeliveryPlaceDepotnoVO {
    /**
     * 仓库编码
     */
    @Schema(description = "仓库编码")
    private String depotNo;
}
