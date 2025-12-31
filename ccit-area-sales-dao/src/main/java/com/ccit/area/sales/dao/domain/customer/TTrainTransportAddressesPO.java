package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户铁运配送地址表
 * @TableName t_train_transport_addresses
 */
@TableName(value ="t_train_transport_addresses")
@Data
public class TTrainTransportAddressesPO implements Serializable {
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
     * 收货单位国税号
     */
    @TableField(value = "customer_tax_id")
    private String customerTaxId;

    /**
     * 专用线
     */
    @TableField(value = "dedicated")
    private String dedicated;

    /**
     * 小票邮寄地址
     */
    @TableField(value = "small_ticket_address")
    private String smallTicketAddress;

    /**
     * 铁路到站
     */
    @TableField(value = "railway_station")
    private String railwayStation;

    /**
     * 地址状态
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