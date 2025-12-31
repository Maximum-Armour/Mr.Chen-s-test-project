package com.ccit.area.sales.dao.vo.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *  * 描述 : “客户审核通过”VO
 *  * 创建人 : tb
 *  * 创建时间 :2024年12月27日 下午3:26:55
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.vo.bidding
 *  * 类名 : ApplyBiddingEndorseVO
 */
@Data
@Schema(description = "【客户审核通过】返回结果实体类")
public class ApplyBiddingEndorseVO {

    /**
     * 招标编码
     */
    @Schema(description = "招标编码")
    private String tenderNumber;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerId;
}
