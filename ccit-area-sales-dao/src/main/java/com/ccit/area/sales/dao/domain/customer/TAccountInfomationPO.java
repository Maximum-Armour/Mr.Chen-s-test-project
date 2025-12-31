package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 企业注册账户信息表
 * @TableName t_account_infomation
 */
@TableName(value ="t_account_infomation")
@Data
public class TAccountInfomationPO {

    @TableId(type = IdType.AUTO)
    private Long id; // 主键

    @TableField("registration_code")
    private String registrationCode; // 注册编码

    @TableField("login_account")
    private String loginAccount; // 登录账号

    @TableField("phone_number")
    private String phoneNumber; // 手机号码

    @TableField("enterprise_email")
    private String enterpriseEmail; // 企业邮箱

    @TableField("account_created_time")
    private LocalDateTime accountCreatedTime; // 账户开户时间

    @TableField("organization_code")
    private String organizationCode; // 客户组织编码

    @TableField("organization_name")
    private String organizationName; // 客户组织名称

    @TableField("login_name")
    private String loginName; // 登录名

    @TableField("account_created_name")
    private String accountCreatedName; // 账户开通人

    @TableField("last_updater_code")
    private String lastUpdaterCode; // 最后更新人编码

    @TableField("last_updater_name")
    private String lastUpdaterName; // 最后更新人的名字

    @TableField("last_update_time")
    private Date lastUpdateTime; // 最后更新时间

    @TableField("status")
    private String status; // 状态

    @TableField("resource_locking_status")
    private String resourceLockingStatus; // 资源锁定状态

    @TableField("created_time")
    private Date createdTime; // 制单时间

    @TableField("is_deleted")
    private Integer isDeleted; // 删除标识：0-未删除；1-已删除
}
