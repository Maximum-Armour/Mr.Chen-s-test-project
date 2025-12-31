package com.ccit.area.sales.dao.vo.customer;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**

 *

 * 描述 : “客户表”实体类

 * 创建人 : chenbaozhaung

 * 创建时间 : 2025年02月20日 下午04:24:09

 * 版本 : 1.0

 * 类名 : CustomerVO
 */
@Data
public class CustomerVO {

    /**
     * id
     */
    @ExcelIgnore
    private Long id;
    /**

     * 公司名称
     */
    @ExcelProperty("公司名称")
    private String companyName;

    /**

     * 公司编码
     */
    @ExcelProperty("公司编码")
    private String companyNo;
    /**

     * 客户编码
     */
    @ExcelProperty("客户编码")
    private String customerNo;

    /**

     * 客户名称
     */
    @ExcelProperty("客户名称")
    private String customerName;

    /**

     * 登录账号
     */
    @ExcelProperty("登录账号")
    private String loginAccount;

    /**

     * 营业执照号
     */
    @ExcelProperty("营业执照号")
    private String businessLicenseNo;

    /**

     * 营业执照到期时间
     */
    @ExcelProperty("营业执照到期时间")
    private Date businessLicenseDate;

    /**

     * 企业性质
     */
    @ExcelProperty("企业性质")
    private String enterpriseNature;

    /**

     * 注册地址
     */
    @ExcelProperty("注册地址")
    private String registeredAddress;

    /**

     * 法定代表人
     */
    @ExcelProperty("法定代表人")
    private String fname;

    /**

     * 法人身份证号
     */
    @ExcelProperty("法人身份证号")
    private String idnumber;

    /**

     * 法人手机号
     */
    @ExcelProperty("法人手机号")
    private String fphone;

    /**
     * 开户行
     */
    @ExcelProperty("开户行")
    private String bankAccount;

    /**

     * 银行账号
     */
    @ExcelProperty("银行账号")
    private String accountNumber;

    /**

     * 状态：0-正常；1-禁用；
     */
    @ExcelIgnore
    private Integer status;

}
