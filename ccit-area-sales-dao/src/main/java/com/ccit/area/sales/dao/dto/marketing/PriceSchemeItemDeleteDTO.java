package com.ccit.area.sales.dao.dto.marketing;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案明细删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 上午9:15:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemeItemDeleteDTO
 */
@Data
@Schema(description = "【定价方案明细删除】接受参数实体类")
public class PriceSchemeItemDeleteDTO {

	/**
	 * 方案明细ID数组
	 */
	@Schema(description = "方案明细ID数组")
	private List<Long> ids;
	
}
