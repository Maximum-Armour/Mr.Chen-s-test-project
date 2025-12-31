package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “参数配置分页列表”DTO
  * 创建人 : tb
  * 创建时间 : 2024年11月27日 下午1:38:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.dto.bidding
  * 类名 : SystemConfigPageListDTO
 */
@Data
@Schema(description = "【参数配置分页列表】接受参数实体类")
public class SystemConfigPageListDTO {

    /**
     * 参数配置ID
     */
    @Schema(description = "参数配置ID")
    private Long id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @Schema(description = "参数键值")
    private String configValue;

    /**
     * 系统内置
     */
    @Schema(description = "系统内置")
    private String configType;
}
