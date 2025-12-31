package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “销售区域删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:55:12
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SalesAreaDeleteDTO
 */
@Data
@Schema(description = "【销售区域删除】接受参数实体类")
public class SalesAreaDeleteDTO {

	/**
	 * 销售区域ID数组
	 */
	@Schema(description = "销售区域ID数组")
	private List<Long> ids;
	
}
