package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
  * 描述 : “竞价申请”实体类
  * 创建人 : tb
  * 创建时间 : 2024年9月12日 下午2:29:15
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.domain.bidding
  * 类名 : ApplyBiddingPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_apply_bidding")
public class ApplyBiddingPO extends BasePO<ApplyBiddingPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 产品编码
     */
    @TableField(value = "materiel_no")
    private String materielNo;

    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
    private String materielName;


    /**
     * 招标编号
     */
    @TableField(value = "tender_number")
    private String tenderNumber;

    /**
     * 客户账号
     */
    @TableField(value = "customer_id")
    private String customerId;

    /**
     * 客户
     */
    private String customer;

    /**
     * 客户IP
     */
    @TableField(value = "customer_ip")
    private String customerIp;

    /**
     * 竞价模式
     */
    @TableField(value = "bidding_mode")
    private String biddingMode;



    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;



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
     * 组织编码
     */
    @TableField(value = "org_no")
    private String orgNo;

    /**
     * 组织名称
     */
    @TableField(value = "org_name")
    private String orgName;

}
