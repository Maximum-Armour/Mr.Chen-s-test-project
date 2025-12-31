package com.ccit.area.sales.dao.dto.customer;


import lombok.Data;

@Data
public class TCustomerQualificationDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 客户全称
     */
    private String customerName;

    /**
     * 客户所持有的有效资质类型
     */
    private String qualificationType;

    /**
     * 客户资质的有效期截止日期
     */
    private String expirationDate;

    /**
     * 负责审核的用户名
     */
    private String userName;

    /**
     * 附件id
     */
    private Integer attachmentId;

    /**
     * 记录创建的时间戳
     */
    private String createTime;

    /**
     * 当前记录的审核状态
     */
    private String auditStatus;

    /**
     * 备注
     */
    private String remark;
}
