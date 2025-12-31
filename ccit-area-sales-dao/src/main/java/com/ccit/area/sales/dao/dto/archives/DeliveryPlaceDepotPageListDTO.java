package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “提货地仓库分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午2:02:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DeliveryPlaceDepotPageListDTO
 */
@Data
@Schema(description = "【提货地仓库分页列表】接受参数实体类")
public class DeliveryPlaceDepotPageListDTO {
	
	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;
	
    /**
     * 仓库编码
     */
	@Schema(description = "仓库编码")
    private String depotNo;

    /**
     * 仓库名称
     */
	@Schema(description = "仓库名称")
    private String depotName;
	
	/**
     * 是否港口
     */
	@Schema(description = "是否港口")
    private String isPort;
	
    /**
     * 是否中转库
     */
	@Schema(description = "是否中转库")
    private String isTransit;

    /**
     * 管理员
     */
	@Schema(description = "管理员")
    private String manager;

    /**
     * 管理员联系电话
     */
	@Schema(description = "管理员联系电话")
    private String managerTelephone;
	
	/**
     * 提货地ID
     */
	@Schema(description = "提货地ID")
    private String id;
	
}
