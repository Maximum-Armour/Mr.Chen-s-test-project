package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “字典树列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:28:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemDictTreeDTO
 */
@Data
@Schema(description = "【字典树列表】接受参数实体类")
public class SystemDictTreeDTO {
	
	/**
     * 字典编码
     */
	@Schema(description = "字典编码")
    private String dictCode;

	/**
     * 字典名称
     */
	@Schema(description = "字典名称")
    private String dictName;
	
    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;
	
}
