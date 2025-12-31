package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户汽运配送地址表
 * @TableName t_car_transport_addresses
 */
@TableName(value ="t_car_transport_addresses")
@Data
public class TCarTransportAddressesPO implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 接货人
     */
    @TableField(value = "receiver_person")
    private String receiverPerson;

    /**
     * 联系电话
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 运输方式
     */
    @TableField(value = "transport_method")
    private String transportMethod;

    /**
     * 收货单位
     */
    @TableField(value = "receiving_unit")
    private String receivingUnit;

    /**
     * 省/自治区
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区/县
     */
    @TableField(value = "district")
    private String district;

    /**
     * 发货详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 制单人
     */
    @TableField(value = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "is_deleted")
    private int isDeleted;
}