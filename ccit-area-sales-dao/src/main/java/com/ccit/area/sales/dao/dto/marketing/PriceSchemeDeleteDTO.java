package com.ccit.area.sales.dao.dto.marketing;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “定价方案删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月9日 下午2:46:54
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : PriceSchemeDeleteDTO
 */
@Data
@Schema(description = "【定价方案删除】接受参数实体类")
public class PriceSchemeDeleteDTO {

	/**
	 * 方案ID数组
	 */
	@Schema(description = "方案ID数组")
	private List<Long> ids;
	
}
