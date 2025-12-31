package com.ccit.area.sales.dao.vo.system;

import lombok.Data;

@Data
public class SystemUserDetailVO {
    private int userId;
    //提交人
    private String realName;
    //提交人用户名
    private String userName;
    //提交人userId
    private String dingdingUserId;
    //提交人钉钉deptID
    private String dingdingDeptId;
    //分组名称
    private String groupCode;
    //部门id
    private int deptId;
    //部门名称
    private String orgName;
    //部门编号
    private String orgNo;
    //提交人部门展示状态id
    private String displayStatus;
}
