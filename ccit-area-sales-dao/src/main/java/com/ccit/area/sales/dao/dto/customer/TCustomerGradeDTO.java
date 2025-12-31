package com.ccit.area.sales.dao.dto.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class TCustomerGradeDTO {
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户级别
     */
    private String customerGrade;

    /**
     * 年度
     */
    private String annual;

    /**
     * 制单时间
     */
    private String createdAt;

    /**
     * 制单人
     */
    private String createdBy;
}
