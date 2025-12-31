package com.ccit.area.sales.dao.vo.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TCarTransportAddressesVO {
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 接货人
     */
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
     * 收货单位
     */
    private String receivingUnit;

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

    /**
     * 描述
     */
    private String description;

    /**
     * 制单人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;
}
