package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “仓库档案分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 下午4:30:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DepotPageListVO
 */
@Data
@Schema(description = "【仓库档案分页列表】返回结果实体类")
public class DepotPageListVO {

	/**
     * 仓库ID
     */
	@Schema(description = "仓库ID")
    private Long id;
	
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
     * 是否港口名称
     */
    @Schema(description = "是否港口名称")
    private String isPortName;
	
    /**
     * 是否中转库
     */
	@Schema(description = "是否中转库")
    private String isTransit;

    /**
     * 是否中转库名称
     */
    @Schema(description = "是否中转库名称")
    private String isTransitName;

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
     * 状态名称
     */
    @Schema(description = "状态名称")
    private String statusName;
}
