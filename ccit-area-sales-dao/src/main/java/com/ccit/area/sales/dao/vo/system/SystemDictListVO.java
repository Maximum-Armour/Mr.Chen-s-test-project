package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “字典列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 上午11:12:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemDictListVO
 */
@Data
@Schema(description = "【字典列表】返回结果实体类")
public class SystemDictListVO {

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
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;
	
}
