package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “参数配置分页列表”VO
  * 创建人 : tb
  * 创建时间 :2024年11月27日 上午10:26:55
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.bidding
  * 类名 : SystemConfigPageListVO
 */
@Data
@Schema(description = "【参数配置分页列表】返回结果实体类")
public class SystemConfigPageListVO {

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
