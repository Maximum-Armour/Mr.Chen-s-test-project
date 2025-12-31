package com.ccit.area.sales.dao.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 客户级别表
 * @TableName t_customer_grade
 */
@TableName(value ="t_customer_grade")
@Data
public class TCustomerGradePO{
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 客户级别
     */
    @TableField(value = "customer_grade")
    private String customerGrade;

    /**
     * 年度
     */
    @TableField(value = "annual")
    private String annual;

    /**
     * 制单时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 制单人
     */
    @TableField(value = "created_by")
    private String createdBy;
}