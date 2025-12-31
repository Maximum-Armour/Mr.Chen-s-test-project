package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
  * 描述 : “产品定价提交”DTO
  * 创建人 : tb
  * 创建时间 : 2024年11月12日 下午2:46:54
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.marketing
  * 类名 : ProductPriceSubmitDTO
 */
@Data
@Schema(description = "【产品定价提交】接受参数实体类")
public class ProductPriceSubmitDTO {

    /**
     * 定价ID数组
     */
    @Schema(description = "定价ID数组")
    private List<Long> ids;

    /**
     * 标志
     */
    @Schema(description = "标志")
    private String sign;
}
