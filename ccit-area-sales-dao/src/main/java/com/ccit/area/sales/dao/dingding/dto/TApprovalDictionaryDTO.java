package com.ccit.area.sales.dao.dingding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "审批字典DTO")
@Data
public class TApprovalDictionaryDTO {

    @Schema(description = "主键ID", required = true)
    private Long id;

    /**
     * 发起人名字
     */
    @Schema(description = "发起人名字", required = true)
    private String userName;

    /**
     * 发起人编号
     */
    @Schema(description = "发起人编号", required = true)
    private String userCode;

    /**
     * 发起人部门编号
     */
    @Schema(description = "发起人部门编号", required = true)
    private String deptCode;

    /**
     * 凭证类型
     */
    @Schema(description = "凭证类型", required = true)
    private String business;

    /**
     * 凭证类型编码
     */
    @Schema(description = "凭证类型编码", required = true)
    private String businessCode;

    /**
     * 字典值
     */
    @Schema(description = "字典值", required = true)
    private String dictionary;

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

