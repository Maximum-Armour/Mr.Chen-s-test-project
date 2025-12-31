package com.ccit.area.sales.dao.dto.archives;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 *
  * 描述 : “提货地档案删除”DTO
  * 创建人 : yn
  * 创建时间 : 2024年8月1日 上午9:31:52
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.archives
  * 类名 : DeliveryPlaceDeleteDTO
 */
@Data
@Schema(description = "【提货地档案新增】接受参数实体类")
public class DeliveryPlaceDepotAddDTO {

    /**
     * 提货地编码
     */
    @Length(max = 50, message = "提货地编码长度不能超过50个字符")
    @Schema(description = "提货地编码")
    private String deliveryPlaceNo;

    /**
     * 仓库编码（多个逗号隔开）
     */

    @Schema(description = "仓库编码（多个逗号隔开）")
    private List<String> depotNo;

    /**
     * 仓库编码（多个逗号隔开）
     */
    @Schema(description = "删除状态（0未删除）")
    private Integer deleted ;

    private Long id;
}
