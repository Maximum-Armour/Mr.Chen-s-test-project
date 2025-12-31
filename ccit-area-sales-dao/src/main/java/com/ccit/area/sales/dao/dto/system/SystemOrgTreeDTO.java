package com.ccit.area.sales.dao.dto.system;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “组织树列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年9月25日 下午2:56:17
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemOrgTreeDTO
 */
@Data
@Schema(description = "【组织树列表】接受参数实体类")
public class SystemOrgTreeDTO {

    /**
     * 组织名称
     */
    @Schema(description = "组织名称")
    private String orgName;

    /**
     * 状态：0-正常；1-禁用；
     */
    @Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;

}
