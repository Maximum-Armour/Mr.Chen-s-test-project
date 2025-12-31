package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “仓库档案树列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 下午1:54:34
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DepotTreeDTO
 */
@Data
@Schema(description = "【仓库档案树列表】接受参数实体类")
public class DepotTreeDTO {

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
	 * 仓库名称
	 */
	@Schema(description = "仓库名称")
	private String depotName;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private String status;

	/**
	 * 区别
	 */
	@Schema(description = "区别")
	private String difference;
}
