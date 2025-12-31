package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “牌号档案删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午1:53:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SkuFileDeleteDTO
 */
@Data
@Schema(description = "【牌号档案删除】接受参数实体类")
public class SkuFileDeleteDTO {

	/**
	 * 牌号ID数组
	 */
	@Schema(description = "牌号ID数组")
	private List<Long> ids;
	
}
