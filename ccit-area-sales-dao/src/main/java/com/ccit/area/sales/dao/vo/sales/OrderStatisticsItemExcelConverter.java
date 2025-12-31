package com.ccit.area.sales.dao.vo.sales;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderStatisticsItemExcelConverter {

    @ExcelProperty("序号")
    private Integer serialNumber;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("产品")
    private String materielName;

    @ExcelProperty("销售单价(元/吨)")
    private BigDecimal orderSubmissionPrice;

    @ExcelProperty("需求吨数")
    private BigDecimal orderSubmissionQuantity;

    @ExcelProperty("备注")
    private String remark;


    // 其他字段和方法...

    public static OrderStatisticsItemExcelConverter createSubtotalRow(String customerName, BigDecimal subtotalQuantity) {
        OrderStatisticsItemExcelConverter converter = new OrderStatisticsItemExcelConverter();
        converter.setCustomerName("小计");
        converter.setOrderSubmissionQuantity(subtotalQuantity);
        converter.setRemark("");
        return converter;
    }

    // **创建合计行数据**
    public static OrderStatisticsItemExcelConverter createTotalRow(BigDecimal totalQuantity) {
        OrderStatisticsItemExcelConverter totalRow = new OrderStatisticsItemExcelConverter();
        totalRow.setCustomerName("总计");
        totalRow.setOrderSubmissionQuantity(totalQuantity);
        return totalRow;
    }
}


