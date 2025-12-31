package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “仓库档案删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:28:47
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DepotDeleteDTO
 */
@Data
@Schema(description = "【仓库档案删除】接受参数实体类")
public class DepotDeleteDTO {
	
	/**
	 * 仓库ID数组
	 */
	@Schema(description = "仓库ID数组")
	private List<Long> ids;

}
