package com.ccit.area.sales.dao.vo.sales;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “订单详情”VO
  * 创建人 : cf
  * 创建时间 : 2024年9月18日 上午9:52:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.sales
  * 类名 : CustomerIntentionVO
 */
@Data
@Schema(description = "【客户意向详情】返回结果实体类")
public class OrderLineListVO {
    /**
     * 挂牌编码
     */
    @Schema(description = "挂牌编码")
    private String listedNo;

    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String companyName;
}
