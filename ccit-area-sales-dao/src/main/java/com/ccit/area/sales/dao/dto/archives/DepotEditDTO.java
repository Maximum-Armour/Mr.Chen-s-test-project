package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “仓库档案修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:21:59
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : DepotEditDTO
 */
@Data
@Schema(description = "【仓库档案修改】接受参数实体类")
public class DepotEditDTO {
	
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
	@Length(max = 50, message = "仓库编码长度不能超过50个字符")
    @Schema(description = "仓库编码")
    private String depotNo;

    /**
     * 仓库名称
     */
	@Length(max = 200, message = "仓库名称长度不能超过200个字符")
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
    @Length(max = 1000, message = "仓库地址长度不能超过1000个字符")
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
    @Length(max = 200, message = "管理员长度不能超过200个字符")
    @Schema(description = "管理员")
    private String manager;

    /**
     * 管理员联系电话
     */
    @Length(max = 50, message = "管理员联系电话长度不能超过50个字符")
    @Schema(description = "管理员联系电话")
    private String managerTelephone;
	
}
