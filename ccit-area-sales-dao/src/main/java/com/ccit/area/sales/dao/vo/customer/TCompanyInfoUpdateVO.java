package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.util.Date;

@Data
public class TCompanyInfoUpdateVO {

    private Long id;

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

    // 黑名单
    private String blacklist;

    /**
     * 制单时间
     */
    private Date createTime;


}
