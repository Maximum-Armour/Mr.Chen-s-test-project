package com.ccit.area.sales.dao.dto.archives;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “ERP产品档案分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月30日 上午9:02:07
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.archives
 * 类名 : ErpMaterielPageListDTO
 */
@Data
@Schema(description = "【ERP产品档案分页列表】接受参数实体类")
public class ErpMaterielPageListDTO {

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
	
}
