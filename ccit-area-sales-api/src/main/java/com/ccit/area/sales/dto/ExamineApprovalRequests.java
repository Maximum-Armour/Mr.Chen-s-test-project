package com.ccit.area.sales.dto;

import lombok.Data;

@Data
public class ExamineApprovalRequests {
    //数据库表名
    private String databaseName;
    //业务类型
    private String businessName;
    //业务id
    private RequestsData data;
    //发起人userName;
    private String userName;
    //公司编码
    private String companyNo;
}
