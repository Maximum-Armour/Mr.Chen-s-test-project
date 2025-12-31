package com.ccit.area.sales.dao.vo.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TTrainTransportAddressesVO {
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
     * 收货单位国税号
     */
    private String customerTaxId;

    /**
     * 专用线
     */
    private String dedicated;

    /**
     * 小票邮寄地址
     */
    private String smallTicketAddress;

    /**
     * 铁路到站
     */
    private String railwayStation;

    /**
     * 地址状态
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
