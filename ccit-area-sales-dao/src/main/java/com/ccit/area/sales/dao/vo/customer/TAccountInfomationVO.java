package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.util.Date;

@Data
public class TAccountInfomationVO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户组织名称
     */
    private String organizationName;

    /**
     * 登录账号
     */
    private String loginAccount;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 企业邮箱
     */
    private String enterpriseEmail;

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
    private Date createdTime;
}
