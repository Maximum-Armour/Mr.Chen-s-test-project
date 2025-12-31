package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品分类详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:55:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : ProductClassDetailsVO
 */
@Data
@Schema(description = "【产品分类详情】返回结果实体类")
public class ProductClassDetailsVO {

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
     * 主副产品
     */
	@Schema(description = "主副产品")
    private String mainOrByproduct;

    /**
     * 是否危化品
     */
	@Schema(description = "是否危化品")
    private String isWhp;
	
}
