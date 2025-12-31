package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “组织详情”VO
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午2:53:03
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemOrgDetailsVO
 */
@Data
@Schema(description = "【组织详情】返回结果实体类")
public class SystemOrgDetailsVO {

	/**
     * 组织ID
     */
	@Schema(description = "组织ID")
    private Long id;

    /**
     * 组织编码
     */
    @Schema(description = "组织编码")
    private String orgNo;

    /**
     * 组织名称
     */
	@Schema(description = "组织名称")
    private String orgName;
	
    /**
     * 组织类型
     */
	@Schema(description = "组织类型")
    private String orgType;

    /**
     * 组织排序
     */
	@Schema(description = "组织排序")
    private Integer orgOrders;

    /**
     * 上级ID
     */
	@Schema(description = "上级ID")
    private Long parentId;

    /**
     * 状态：0-正常；1-禁用；
     */
	@Schema(description = "状态：0-正常；1-禁用；")
    private Integer status;

    /**
     * 组织编码简称
     */
    @Schema(description = "组织编码简称")
    private String orgNoAbbreviation;

    /**
     * 组织简称
     */
    @Schema(description = "组织简称")
    private String orgNameAbbreviation;

    /**
     * ERP组织编码
     */
    @Schema(description = "ERP组织编码")
    private String erpOrgNo;

    /**
     * 钉钉部门id
     */
    @Schema(description = "钉钉部门id")
    private String dingdingDeptId;

    /**
     * 钉钉部门名称
     */
    @Schema(description = "钉钉部门名称")
    private String dingdingDeptName;
}
