package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “挂牌修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月21日 下午3:34:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ListedEditDTO
 */
@Data
@Schema(description = "【挂牌修改】接受参数实体类")
public class ListedEditDTO {
	
	/**
	 * 挂牌ID
	 */
	@Schema(description = "挂牌ID")
	private Long id;
	
	/**
	 * 挂牌模型
	 */
	@Schema(description = "挂牌模型")
	private String listedMode;

}
