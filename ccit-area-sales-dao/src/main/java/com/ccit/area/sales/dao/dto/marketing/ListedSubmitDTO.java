package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
  * 描述 : “产品挂牌提交”DTO
  * 创建人 : tb
  * 创建时间 : 2024年10月31日 下午3:43:14
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : ListedSubmitDTO
 */
@Data
@Schema(description = "【产品挂牌提交】接受参数实体类")
public class ListedSubmitDTO {

    /**
     * 产品挂牌ID数组
     */
    @Schema(description = "产品挂牌ID数组")
    private List<Long> ids;

    /**
     * 挂牌状态
     */
    @Schema(description = "挂牌状态")
    private String listedStatus;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态")
    private String useStatus;

    /**
     * 区别
     */
    @Schema(description = "区别")
    private String difference;
}
