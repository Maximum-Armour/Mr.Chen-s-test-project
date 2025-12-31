package com.ccit.area.sales.dao.dingding.dto;

import lombok.Data;

@Data
public class ApprovalProcessTemplatesDTO {

    /**
     * 业务名称。
     */

    private String businessName;

    /**
     * 审批人真实姓名。
     */
    private String approvalRealName;

    /**
     * 审批人用户名。
     */
    private String approvalUserName;

    /**
     * 父级ID，用于表示层级关系。
     */
    private Integer parentId;

    /**
     * 层级，表示当前记录在层级结构中的深度。
     */
    private int level;

    /**
     * 类型，用于区分不同的审批流程类型。
     */
    private String type;

    /**
     * 类型描述，对类型字段的详细解释。
     */
    private String typeDescription;


    /**
     * 公司编码，用于标识所属公司。
     */
    private String companyNo;

}
