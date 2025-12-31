package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “字典详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:29:59
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemDictDetailsVO
 */
@Data
@Schema(description = "【字典详情】返回结果实体类")
public class SystemDictDetailsVO {
	
	/**
     * 字典ID
     */
	@Schema(description = "字典ID")
    private Long id;
	
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

    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;

}
