package com.ccit.area.sales.dao.vo.customer;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 企业基本信息表
 * @TableName t_company_info
 */
@Data
public class TCompanyInfosVO {

    private Long id;

    // 公司名称
    private String companyName;

    // 营业执照号码
    private String businessLicenseNumber;

    // 有效期截止日期
    private Timestamp expirationDate;

    // 是否为终端用户
    private String isUltimateConsignee;

    // 企业性质（如国有企业、民营企业等）
    private String corporateNature;

    // 企业性质类型（如上市公司、非上市公众公司等）
    private String corporateNatureType;

    // 合作单位
    private String cooperativeUnits;

    // 其他合作单位
    private String otherCooperativeUnits;

    // 注册地址
    private String registrationAddress;

    // 注册资金（单位：万元）
    private String registrationFunds;

    // 成立时间
    private Timestamp establishmentDate;

    // 经营地点
    private String businessPlace;

    // 法人手机号码
    private String phoneNumber;

    // 固定电话
    private String landline;

    // 员工人数
    private Integer employeeCount;

    // 法定代表人姓名
    private String legalRepresentative;

    // 法定代表人身份证号
    private String legalPersonIdCard;

    // 法人手机
    private String phone;

    // 法人固定电话
    private String legalPersonLandline;

    // 纳税识别号
    private String taxpayerCode;

    // 开户银行全称
    private String bankName;

    // 分行名称
    private String branchName;

    // 银行开户行地址
    private String accountBankAddress;

    // 账户名
    private String accountName;

    // 银行账号
    private String bankAccount;

    // 地址
    private String address;

    // 纳税人手机号
    private String taxpayerPhone;

    // 纳税人固定电话
    private String taxpayerLandline;

    // 状态（如正常、异常等）
    private String status;

    // 资源锁定状态
    private String resourceLockingStatus;

    // 创建时间
    private Timestamp createTime;

    // 推送预留系统状态
    private String pushReservationSystemStatus;

    // 总资产（单位：万元）
    private String totalAssets;

    // 流动资产（单位：万元）
    private String currentAssets;

    // 上年收入（单位：万元）
    private String lastYearIncome;

    // 近三年平均销售额（单位：万元）
    private String averageSales;

    // 黑名单
    private String blacklist;
}
