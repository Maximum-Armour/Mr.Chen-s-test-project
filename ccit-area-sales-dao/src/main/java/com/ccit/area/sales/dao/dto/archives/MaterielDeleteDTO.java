package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品档案删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月16日 上午11:16:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : MaterielDeleteDTO
 */
@Data
@Schema(description = "【产品档案删除】接受参数实体类")
public class MaterielDeleteDTO {

	/**
	 * 产品档案ID数组
	 */
	@Schema(description = "产品档案ID数组")
	private List<Long> ids;
	
}
