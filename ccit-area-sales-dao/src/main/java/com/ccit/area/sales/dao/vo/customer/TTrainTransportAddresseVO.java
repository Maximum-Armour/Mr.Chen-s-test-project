package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

@Data
public class TTrainTransportAddresseVO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 客户名称
     */
    private String customerName;

    private String receiverPerson;
    /**
     * 联系电话
     */
    private String phoneNumber;

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
