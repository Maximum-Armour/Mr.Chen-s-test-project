package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “附件删除”DTO
  * 创建人 : tb
  * 创建时间 : 2024年10月31日 上午9:12:13
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SystemFilePageListDTO
 */
@Data
@Schema(description = "【附件删除】接受参数实体类")
public class SystemFileDeleteDTO {

    /**
     * 附件ID
     */
    @Schema(description = "附件ID")
    private Long id;

}
