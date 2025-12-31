package com.ccit.area.sales.dao.vo.sales;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “签章合同”VO
  * 创建人 : cf
  * 创建时间 : 2024年12月18日 下午15:13:12
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.sales
  * 类名 : orderSignatureVO
 */
@Data
@Schema(description = "【下载签章合同】返回结果实体类")
public class orderSignatureVO {

    private String orderNo;
    private String materielName;
    private String listedAmount;
    private String orderType;
    private String listedTaxPrice;
    private String totalAmount;
    private String areaName;
    private String cashtransfercycle;
    private String acceptancepaymentcycle;
    private String amountMoney;
    private String taxAmount;

}
