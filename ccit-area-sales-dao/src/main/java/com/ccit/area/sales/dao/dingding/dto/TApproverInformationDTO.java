package com.ccit.area.sales.dao.dingding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "审批人信息DTO")
@Data
public class TApproverInformationDTO {
    /**
     * 主键
     */
    @Schema(description = "主键", required = true)
    private Integer id;

    /**
     * 审批人名字
     */
    @Schema(description = "审批人名字", required = true)
    private String userName;

    /**
     * 审批人编号
     */
    @Schema(description = "审批人编号", required = true)
    private String userCode;

    /**
     * 审批人部门名称
     */
    @Schema(description = "审批人部门名称", required = true)
    private String deptName;

    /**
     * 审批人部门编号
     */
    @Schema(description = "审批人部门编号", required = true)
    private String deptCode;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", required = true)
    private String createTime;

    /**
     * 最后修改人名字
     */
    @Schema(description = "最后修改人名字", required = true)
    private String reviseName;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间", required = true)
    private String reviseTime;

    /**
     * 删除人名字
     */
    @Schema(description = "删除人名字", required = false)
    private String deletedName;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间", required = false)
    private String deletedTime;
}

