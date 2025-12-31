package com.ccit.area.sales.dao.vo.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 企业注册账户信息表
 * @TableName t_account_infomation
 */
@TableName(value ="t_account_infomation")
@Data
public class TAccountInfomationsVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 注册编码
     */
    private String registrationCode;

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
     * 账户开户时间
     */
    private Date accountCreatedTime;

    /**
     * 客户组织编码
     */
    private String organizationCode;

    /**
     * 客户组织名称
     */
    private String organizationName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 账户开通人
     */
    private String accountCreatedName;

    /**
     * 最后更新人编码
     */
    private String lastUpdaterCode;

    /**
     * 最后更新人的名字
     */
    private String lastUpdaterName;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

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