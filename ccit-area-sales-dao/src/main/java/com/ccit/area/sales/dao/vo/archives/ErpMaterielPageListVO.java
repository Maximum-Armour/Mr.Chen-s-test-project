package com.ccit.area.sales.dao.vo.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “ERP产品档案列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午5:02:22
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.archives
 * 类名 : ErpMaterielPageListVO
 */
@Data
@Schema(description = "【ERP产品档案列表】返回结果实体类")
public class ErpMaterielPageListVO {
	
	/**
     * ERP产品档案ID
     */
	@Schema(description = "ERP产品档案ID")
    private Long id;
	
    /**
     * ERP产品属性
     */
	@Schema(description = "ERP产品属性")
    private String erpMaterielShowdesc;

    /**
     * ERP产品编码
     */
	@Schema(description = "ERP产品编码")
    private String erpMaterielNo;

    /**
     * ERP产品名称
     */
	@Schema(description = "ERP产品名称")
    private String erpMaterielName;

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
     * 单位
     */
	@Schema(description = "单位")
    private String unit;

}
