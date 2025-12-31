package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;

@Data
public class TCarTransportAddressesDTO {

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 接货人
     */
    private String receiverPerson;

    private String transportMethod;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 省/自治区
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 发货详细地址
     */
    private String address;

    /**
     * 状态
     */
    private String status;
}
