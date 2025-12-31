package com.ccit.area.sales.vo;

import lombok.Data;

@Data
public class ResultData {

    //业务id
    private Long businessId;
    //实例Id
    private String approvalId;
    //业务返回码
    private String resultCode;
    //业务响应信息
    private String resultMessages;

}
