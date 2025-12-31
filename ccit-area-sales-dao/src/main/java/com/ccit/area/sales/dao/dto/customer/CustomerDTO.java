package com.ccit.area.sales.dao.dto.customer;

import lombok.Data;
/**

 *

 * 描述 : “客户表”实体类

 * 创建人 : chenbaozhaung

 * 创建时间 : 2025年02月20日 下午04:24:09

 * 版本 : 1.0

 * 类名 : CustomerDTO
 */
@Data
public class CustomerDTO {

    private Long id;
    /**

     * 客户编码
     */
    private String customerNo;

    /**

     * 客户名称
     */
    private String customerName;

    /**

     * 登录账号
     */
    private String loginAccount;

    /**

     * 营业执照号
     */
    private String businessLicenseNo;

    /**

     * 营业执照到期时间
     */
    private String businessLicenseDate;

    /**

     * 企业性质
     */
    private String enterpriseNature;

    /**

     * 注册地址
     */
    private String registeredAddress;

    /**

     * 法定代表人
     */
    private String fname;

    /**

     * 法人身份证号
     */
    private String idnumber;

    /**

     * 法人手机号
     */
    private String fphone;

    /**
     * 开户行
     */
    private String bankAccount;

    /**

     * 银行账号
     */
    private String accountNumber;

    /**

     * 公司编码
     */
    private String companyNo;

    /**

     * 公司名称
     */
    private String companyName;

    /**

     * 状态：0-正常；1-禁用；
     */
    private Integer status;
    /**
     * 排序条件
     */
    private String field;
    /**
     * 排序字段
     */
    private String sortType;
}
