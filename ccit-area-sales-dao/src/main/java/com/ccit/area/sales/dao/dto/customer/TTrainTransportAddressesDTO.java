package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;

@Data
public class TTrainTransportAddressesDTO {
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 联系电话
     */
    private String phoneNumber;

    private String receiverPerson;
    /**
     * 运输方式
     */
    private String transportMethod;
    /**
     * 专用线
     */
    private String dedicated;
    /**
     * 铁路到站
     */
    private String railwayStation;

    /**
     * 地址状态
     */
    private String status;
}
