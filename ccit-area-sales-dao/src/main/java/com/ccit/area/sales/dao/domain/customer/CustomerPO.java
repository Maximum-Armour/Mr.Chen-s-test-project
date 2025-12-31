package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**

 *

 * 描述 : “客户表”实体类

 * 创建人 : chenbaozhaung

 * 创建时间 : 2025年02月20日 下午04:24:09

 * 版本 : 1.0

 * 类名 : CustomerPO
 */@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_customer")
public class CustomerPO extends BasePO<CustomerPO> {

    /**
     * 客户编码
     */
    @TableField(value = "customer_no")
    private String customerNo;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**

     * 登录账号
     */
    @TableField(value = "login_account")
    private String loginAccount;

    /**

     * 营业执照号
     */
    @TableField(value = "business_license_no")
    private String businessLicenseNo;

    /**

     * 营业执照到期时间
     */
    @TableField(value = "business_license_date")
    private Date businessLicenseDate;

    /**

     * 企业性质
     */
    @TableField(value = "enterprise_nature")
    private String enterpriseNature;

    /**

     * 注册地址
     */
    @TableField(value = "registered_address")
    private String registeredAddress;

    /**

     * 法定代表人
     */
    @TableField(value = "fname")
    private String fname;

    /**

     * 法人身份证号
     */
    @TableField(value = "idnumber")
    private String idnumber;

    /**

     * 法人手机号
     */
    @TableField(value = "fphone")
    private String fphone;

    /**
     * 开户行
     */
    @TableField(value = "bank_account")
    private String bankAccount;

    /**

     * 银行账号
     */
    @TableField(value = "account_number")
    private String accountNumber;

    /**

     * 公司编码
     */
    @TableField(value = "company_no")
    private String companyNo;

    /**

     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**

     * 状态：0-正常；1-禁用；
     */
    @TableField(value = "status")
    private Integer status;

}