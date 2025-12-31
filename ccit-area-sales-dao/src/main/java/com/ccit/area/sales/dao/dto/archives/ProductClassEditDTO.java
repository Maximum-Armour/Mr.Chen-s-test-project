package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品分类修改”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:38:59
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : ProductClassEditDTO
 */
@Data
@Schema(description = "【产品分类修改】接受参数实体类")
public class ProductClassEditDTO {

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
	@Length(max = 50, message = "产品分类编码长度不能超过50个字符")
	@Schema(description = "产品分类编码")
    private String productClassNo;

    /**
     * 产品分类名称
     */
	@Length(max = 200, message = "产品分类名称长度不能超过200个字符")
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
     * 主副产品
     */
	@Length(max = 10, message = "主副产品长度不能超过10个字符")
	@Schema(description = "主副产品")
    private String mainOrByproduct;

    /**
     * 是否危化品
     */
	@Length(max = 5, message = "是否危化品长度不能超过5个字符")
	@Schema(description = "是否危化品")
    private String isWhp;

}
