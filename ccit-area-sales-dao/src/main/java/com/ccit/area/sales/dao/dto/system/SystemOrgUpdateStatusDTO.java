package com.ccit.area.sales.dao.dto.system;

import lombok.Data;

@Data
public class SystemOrgUpdateStatusDTO {

    //组织id
    private Integer orgId;
    //userid
    private Integer userId;
    //用户名字
    private String realName;
    //用户账号
    private String userName;
    //展示状态值
    private String displayStatus;
}
