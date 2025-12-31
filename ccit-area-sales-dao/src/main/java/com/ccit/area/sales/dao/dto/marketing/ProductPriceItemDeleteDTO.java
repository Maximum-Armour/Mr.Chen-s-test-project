package com.ccit.area.sales.dao.dto.marketing;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品定价明细删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月14日 上午9:00:54
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ProductPriceItemDeleteDTO
 */
@Data
@Schema(description = "【产品定价明细删除】接受参数实体类")
public class ProductPriceItemDeleteDTO {

	/**
	 * 产品定价明细ID数组
	 */
	@Schema(description = "产品定价明细ID数组")
	private List<Long> ids;
	
}
