package com.ccit.area.sales.dao.dto.marketing;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “挂牌删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月14日 上午9:01:01
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.marketing
 * 类名 : ListedDeleteDTO
 */
@Data
@Schema(description = "【挂牌删除】接受参数实体类")
public class ListedDeleteDTO {

	/**
	 * 挂牌ID数组
	 */
	@Schema(description = "挂牌ID数组")
	private List<Long> ids;
	
}
