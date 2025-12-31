package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “参数配置详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:25:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemConfigDetailsVO
 */
@Data
@Schema(description = "【参数配置详情】返回结果实体类")
public class SystemConfigDetailsVO {

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
     * 系统内置：Y-是；N-否；
     */
	@Schema(description = "系统内置：Y-是；N-否；")
    private String configType;
	
}
