package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “挂牌子节点新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月20日 下午3:01:03
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ListedNoneAddDTO
 */
@Data
@Schema(description = "【挂牌子节点新增】接受参数实体类")
public class ListedNoneAddDTO {

	/**
	 * 产品定价明细ID
	 */
	@Schema(description = "产品定价明细ID")
	private Long id;
	
	/**
	 * 挂牌模型
	 */
	@Schema(description = "挂牌模型")
	private String listedMode;
	
}
