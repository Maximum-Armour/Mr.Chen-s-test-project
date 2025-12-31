package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “牌号档案详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:08:00
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : SkuFileDetailsVO
 */
@Data
@Schema(description = "【牌号档案详情】返回结果实体类")
public class SkuFileDetailsVO {

	/**
     * 牌号ID
     */
	@Schema(description = "牌号ID")
    private Long id;
	
	/**
     * 状态
     */
	@Schema(description = "状态")
    private String status;
	
	/**
     * 牌号编码
     */
	@Schema(description = "牌号编码")
    private String skuNo;

    /**
     * 牌号名称
     */
	@Schema(description = "牌号名称")
    private String skuName;

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
     * 主副产品
     */
	@Schema(description = "主副产品")
    private String mainOrByproduct;

    /**
     * 执行标准
     */
	@Schema(description = "执行标准")
    private String executiveStandard;

    /**
     * 标准号
     */
	@Schema(description = "标准号")
    private String standardNumber;
	
}
