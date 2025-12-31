package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “字典子节点”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 上午10:09:12
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemDictChildrenVO
 */
@Data
@Schema(description = "【字典子节点】返回结果实体类")
public class SystemDictChildrenVO {

	/**
     * 字典ID
     */
	@Schema(description = "字典ID")
    private Long id;
	
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
