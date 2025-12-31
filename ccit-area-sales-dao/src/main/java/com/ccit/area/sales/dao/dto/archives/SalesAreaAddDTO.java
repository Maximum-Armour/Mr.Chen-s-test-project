package com.ccit.area.sales.dao.dto.archives;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “产品档案新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午3:06:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : SalesAreaAddDTO
 */
@Data
@Schema(description = "【产品档案新增】接受参数实体类")
public class SalesAreaAddDTO {

	/**
     * 状态
     */
	@Length(max = 10, message = "状态长度不能超过10个字符")
	@Schema(description = "状态")
    private String status;

    /**
     * 区域编码
     */
	@Length(max = 50, message = "区域编码长度不能超过50个字符")
    @Schema(description = "区域编码")
    private String areaNo;

    /**
     * 区域名称
     */
	@Length(max = 200, message = "区域名称长度不能超过200个字符")
    @Schema(description = "区域名称")
    private String areaName;

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
     * 贸易类型
     */
	@Length(max = 50, message = "贸易类型长度不能超过50个字符")
    @Schema(description = "贸易类型")
    private String tradeType;

    /**
     * 备注
     */
    @Length(max = 500, message = "备注长度不能超过50个字符")
    @Schema(description = "备注")
    private String remark;

    /**
     * 组织编码
     */
    @Length(max = 50, message = "组织编码不能超过50个字符")
    @Schema(description = "组织编码")
    private String orgNo;
}
