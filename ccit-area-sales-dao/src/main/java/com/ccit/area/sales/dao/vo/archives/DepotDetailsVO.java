package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “仓库档案详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:23:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DepotDetailsVO
 */
@Data
@Schema(description = "【仓库档案详情】返回结果实体类")
public class DepotDetailsVO {

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
     * 仓库排序
     */
    @Schema(description = "仓库排序")
    private String depotOrders;

    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private Long parentId;
    
    /**
     * 上级名称
     */
	@Schema(description = "上级名称")
    private String parentName;

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
     * 仓库地址
     */
    @Schema(description = "仓库地址")
    private String depotAddress;

    /**
     * 省份编码
     */
    @Schema(description = "省份编码")
    private String provinceNo;

    /**
     * 省份名称
     */
    @Schema(description = "省份名称")
    private String provinceName;

    /**
     * 市区编码
     */
    @Schema(description = "市区编码")
    private String cityNo;

    /**
     * 市区名称
     */
    @Schema(description = "市区名称")
    private String cityName;

    /**
     * 区县编码
     */
    @Schema(description = "区县编码")
    private String countyNo;

    /**
     * 区县名称
     */
    @Schema(description = "区县名称")
    private String countyName;

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
	
}
