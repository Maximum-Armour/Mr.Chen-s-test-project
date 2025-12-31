package com.ccit.area.sales.dao.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *  * 描述 : “竞价申请报名”DTO
 *  * 创建人 : tb
 *  * 创建时间 : 2024年10月18日 下午1:42:13
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.dto.bidding
 *  * 类名 : ApplyBiddingAddDTO
 */
@Data
@Schema(description = "【竞价申请报名】接受参数实体类")
public class ApplyBiddingAddDTO {

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerId;

    /**
     * 客户
     */
    @Schema(description = "客户")
    private String customer;


    /**
     * 招标编号
     */
    @Schema(description = "招标编号")
    private String tenderNumber;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    private String realName;

    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String companyNo;

    /**
     * 公司编码
     */
    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "报名时间")
    private String signTime;
}
