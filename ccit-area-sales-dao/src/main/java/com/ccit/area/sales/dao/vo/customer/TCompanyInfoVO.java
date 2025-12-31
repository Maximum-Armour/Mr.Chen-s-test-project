package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 企业基本信息表
 * @TableName t_company_info
 */
@Data
public class TCompanyInfoVO {

    private Integer id;

    /**
     * 企业全称
     */
    private String companyName;

    /**
     * 营业执照号
     */
    private String businessLicenseNumber;

    /**
     * 企业性质
     */
    private String corporateNature;

    /**
     * 注册地址
     */
    private String registrationAddress;

    /**
     * 注册资本
     */
    private String registrationFunds;

    /**
     * 状态
     */
    private String status;

    /**
     * 资源锁定状态
     */
    private String resourceLockingStatus;
    /**
     * 制单时间
     */
    private Timestamp createTime;
    /**
     *  推送预约系统状态
     */
    private String pushReservationSystemStatus;


}
