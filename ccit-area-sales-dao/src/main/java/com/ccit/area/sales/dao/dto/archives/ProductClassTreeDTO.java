package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品分类树列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月19日 上午10:56:30
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : ProductClassTreeDTO
 */
@Data
@Schema(description = "【产品分类树列表】接受参数实体类")
public class ProductClassTreeDTO {
	
	/**
	 * 不等于ID
	 */
	@Schema(description = "不等于ID")
	private String notId;
	
    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private String parentId;

	/**
	 * 产品分类名称
	 */
	@Schema(description = "产品分类名称")
	private String productClassName;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private String status;

	/**
	 * 最大层级
	 */
	@Schema(description = "最大层级")
	private String maxHierarchical;

	/**
	 * 区别
	 */
	@Schema(description = "区别")
	private String difference;
}
