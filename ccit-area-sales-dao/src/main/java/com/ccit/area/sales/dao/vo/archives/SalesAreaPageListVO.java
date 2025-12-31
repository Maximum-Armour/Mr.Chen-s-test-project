package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 描述 : “销售区域分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午3:20:13
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : SalesAreaPageListVO
 */
@Data
@Schema(description = "【销售区域分页列表】返回结果实体类")
public class SalesAreaPageListVO {

	/**
     * 销售区域ID
     */
	@Schema(description = "销售区域ID")
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
     * 区域编码
     */
    @Schema(description = "区域编码")
    private String areaNo;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    private String areaName;

    /**
     * 产品分类编码
     */
    @Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @Schema(description = "产品分类名称")
    private String productClassName;

    /**
     * 贸易类型
     */
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 贸易类型名称
     */
    @Schema(description = "贸易类型名称")
    private String tradeTypeName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

//    /**
//     * 子菜单集合
//     */
//    @Schema(description = "子菜单集合")
//    private List<SalesAreaPageListVO> children = new ArrayList<>();
}
