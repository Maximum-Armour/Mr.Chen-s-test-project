package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 企业基本信息表
 *
 * @TableName t_company_info
 */
@TableName(value = "t_company_info")
@Data
public class TCompanyInfoPO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业全称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 营业执照号
     */
    @TableField("business_license_number")
    private String businessLicenseNumber;

    /**
     * 到期日期
     */
    @TableField("expiration_date")
    private Date expirationDate;

    /**
     * 是否终端用户
     */
    @TableField("is_ultimate_consignee")
    private String isUltimateConsignee;

    /**
     * 企业性质
     */
    @TableField("corporate_nature")
    private String corporateNature;

    /**
     * 企业性质类型
     */
    @TableField("corporate_nature_type")
    private String corporateNatureType;

    /**
     * 合作单位
     */
    @TableField("cooperative_units")
    private String cooperativeUnits;

    /**
     * 其他合作单位
     */
    @TableField("other_cooperative_units")
    private String otherCooperativeUnits;

    /**
     * 注册地址
     */
    @TableField("registration_address")
    private String registrationAddress;

    /**
     * 注册资本
     */
    @TableField("registration_funds")
    private String registrationFunds;

    /**
     * 成立日期
     */
    @TableField("establishment_date")
    private Date establishmentDate;

    /**
     * 经营地点
     */
    @TableField("business_place")
    private String businessPlace;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 固定电话
     */
    @TableField("Landline")
    private String landline;

    /**
     * 员工人数
     */
    @TableField("employee_count")
    private Integer employeeCount;

    /**
     * 法定代表人
     */
    @TableField("legal_representative")
    private String legalRepresentative;

    /**
     * 法人身份证号
     */
    @TableField("legal_person_id_card")
    private String legalPersonIdCard;

    /**
     * 手机
     */
    @TableField("phone")
    private String phone;

    /**
     * 法人固话
     */
    @TableField("legal_person_landline")
    private String legalPersonLandline;

    /**
     * 纳税人识别号
     */
    @TableField("taxpayer_code")
    private String taxpayerCode;

    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 分行名称
     */
    @TableField("branch_name")
    private String branchName;

    /**
     * 开户行地址
     */
    @TableField("account_bank_address")
    private String accountBankAddress;

    /**
     * 账户名
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 账号
     */
    @TableField("bank_account")
    private String bankAccount;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 纳税人手机
     */
    @TableField("taxpayer_phone")
    private String taxpayerPhone;

    /**
     * 纳税人固话
     */
    @TableField("taxpayer_landline")
    private String taxpayerLandline;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 资源锁定状态
     */
    @TableField("resource_locking_status")
    private String resourceLockingStatus;
    /**
     * 制单时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     *  推送预约系统状态
     */
    @TableField("push_reservation_system_status")
    private String pushReservationSystemStatus;

    /**
     * 总资产
     */
    @TableField("total_assets")
    private String totalAssets;

    /**
     * 流动资产
     */
    @TableField("current_assets")
    private String currentAssets;

    /**
     * 上一年收入
     */
    @TableField("last_year_income")
    private String lastYearIncome;

    /**
     * 近三年平均销售
     */
    @TableField("average_sales")
    private String averageSales;

    // 黑名单
    @TableField("blacklist")
    private String blacklist;

    /**
     * 删除标识 默认为0
     */
    @TableField("is_deleted")
    private Integer isDeleted;
}