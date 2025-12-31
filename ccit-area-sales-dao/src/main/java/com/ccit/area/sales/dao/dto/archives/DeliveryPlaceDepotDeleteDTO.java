package com.ccit.area.sales.dao.dto.archives;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地仓库删除”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午2:09:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DeliveryPlaceDepotDeleteDTO
 */
@Data
@Schema(description = "【提货地仓库删除】接受参数实体类")
public class DeliveryPlaceDepotDeleteDTO {

	/**
	 * 提货地仓库ID数组
	 */
	@Schema(description = "提货地仓库ID数组")
	private List<Long> ids;
	
}
