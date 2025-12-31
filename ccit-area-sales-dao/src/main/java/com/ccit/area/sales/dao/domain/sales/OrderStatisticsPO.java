package com.ccit.area.sales.dao.domain.sales;

import com.baomidou.mybatisplus.annotation.*;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接单统计表
 * @TableName t_order_statistics
 */
@TableName(value ="t_order_statistics")
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderStatisticsPO  extends BasePO<OrderStatisticsPO> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接单编码
     */
    @TableField(value = "take_order_no")
    private String takeOrderNo;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 工作流编码
     */
    @TableField(value = "workflowid")
    private String workflowid;

    /**
     * 审核标志
     */
    @TableField(value = "shbz")
    private String shbz;

    /**
     * 公司编码
     */
    @TableField(value = "company_no", fill = FieldFill.INSERT)
    private String companyNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name", fill = FieldFill.INSERT)
    private String companyName;
}