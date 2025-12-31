package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 描述 : “产品分类树列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月19日 上午10:57:14
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : ProductClassTreeVO
 */
@Data
@Schema(description = "【产品分类树列表】返回结果实体类")
public class ProductClassTreeVO {

	/**
     * 产品分类ID
     */
	@Schema(description = "产品分类ID")
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
     * 产品分类排序
     */
	@Schema(description = "产品分类排序")
    private Integer productClassOrders;

    /**
     * 主副产品
     */
	@Schema(description = "主副产品")
    private String mainOrByproduct;
	
    /**
     * 主副产品名称
     */
	@Schema(description = "主副产品名称")
    private String mainOrByproductName;

    /**
     * 是否危化品
     */
	@Schema(description = "是否危化品")
    private String isWhp;
	
    /**
     * 是否危化品名称
     */
	@Schema(description = "是否危化品名称")
    private String isWhpName;
	
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
     * 子菜单集合
     */
    @Schema(description = "子菜单集合")
    private List<ProductClassTreeVO> children = new ArrayList<>();
}
