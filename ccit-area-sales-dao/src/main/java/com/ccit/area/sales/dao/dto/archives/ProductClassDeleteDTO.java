package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品分类删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:34:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : ProductClassDeleteDTO
 */
@Data
@Schema(description = "【产品分类删除】接受参数实体类")
public class ProductClassDeleteDTO {

	/**
	 * 产品分类ID数组
	 */
	@Schema(description = "产品分类ID数组")
	private List<Long> ids;
	
}
