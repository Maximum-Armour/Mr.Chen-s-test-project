package com.ccit.area.sales.common.dingding.constant;

/**
 * OA审批枚举类
 */
public enum UrlEnum {
    //提交钉钉url
    SUBMIT_PAGE("http://192.168.1.120:10010");

    private final String url;

    UrlEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
