package com.ccit.area.sales.dao.vo.system;

import lombok.Data;

@Data
public class SystemOrgVO {

    private int userId;

    private int orgId;

    private String orgName;

    private String orgNo;

    //提交人部门展示状态id
    private String displayStatus;

    private String dingdingDeptId;
}
