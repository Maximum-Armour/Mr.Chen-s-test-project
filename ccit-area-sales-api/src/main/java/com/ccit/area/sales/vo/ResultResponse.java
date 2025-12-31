package com.ccit.area.sales.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResultResponse {

    //流程状态码
    private String code;
    //流程响应信息
    private String messages;
    //业务返回类型
    private List<ResultData> data;
}

