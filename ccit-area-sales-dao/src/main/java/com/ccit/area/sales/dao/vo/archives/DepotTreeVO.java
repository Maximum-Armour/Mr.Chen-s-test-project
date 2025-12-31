package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 描述 : “仓库档案树列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月1日 上午9:12:03
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : DepotTreeVO
 */
@Data
@Schema(description = "【仓库档案树列表】返回结果实体类")
public class DepotTreeVO {

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
     * 状态名称
     */
	@Schema(description = "状态名称")
    private String statusName;
	
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
     * 是否有叶子节点
     */
	@Schema(description = "是否有叶子节点")
    private Boolean leaf = true;
	
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
     * 子菜单集合
     */
    @Schema(description = "子菜单集合")
    private List<DepotTreeVO> children = new ArrayList<>();
}
