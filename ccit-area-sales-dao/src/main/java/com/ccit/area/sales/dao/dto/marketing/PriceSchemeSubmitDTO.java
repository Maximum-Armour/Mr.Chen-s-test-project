package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
  * 描述 : “定价方案提交”DTO
  * 创建人 : tb
  * 创建时间 : 2024年11月7日 下午2:46:54
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.marketing
  * 类名 : PriceSchemeSubmitDTO
 */
@Data
@Schema(description = "【定价方案提交】接受参数实体类")
public class PriceSchemeSubmitDTO {

    /**
     * 方案ID数组
     */
    @Schema(description = "方案ID数组")
    private List<Long> ids;

    /**
     * 标志
     */
    @Schema(description = "标志")
    private String sign;
}
