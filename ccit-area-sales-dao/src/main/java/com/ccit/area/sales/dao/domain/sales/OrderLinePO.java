package com.ccit.area.sales.dao.domain.sales;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 描述 : “订单行”实体类
 * 创建人 : cf
 * 创建时间 : 2024年09月14日 下午10:37:23
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.sales
 * 类名 : OrderLinePO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_order_line")
public class OrderLinePO extends BasePO<OrderLinePO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 订单编码
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 订单行编号
     */
    @TableField(value = "order_line_no")
    private String orderLineNo;

    /**
     * 挂牌编码
     */
    @TableField(value = "listed_no")
    private String listedNo;

    /**
     * 订单提交价格
     */
    @TableField(value = "order_submission_price")
    private BigDecimal orderSubmissionPrice;

    /**
     * 订单提交数量
     */
    @TableField(value = "order_submission_quantity")
    private BigDecimal orderSubmissionQuantity;

    /**
     * 订单成交价格
     */
    @TableField(value = "transaction_price")
    private BigDecimal transactionPrice;

    /**
     * 订单成交数量
     */
    @TableField(value = "turnover_quantity")
    private BigDecimal turnoverQuantity;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 对标价格
     */
    @TableField(value = "benchmark_price")
    private BigDecimal benchmarkPrice;
    /**
     * 订单生效日期
     */
    @TableField(value = "order_start_time")
    private Date orderStartTime;
    /**
     * 订单截至日期
     */
    @TableField(value = "order_end_time")
    private Date orderEndTime;

    /**
     * 订单提醒日期
     */
    @TableField(value = "dd_warn_day")
    private String ddWarnDay;

    /**
     * 订单审核时间
     */
    @TableField(value = "order_approval_time")
    private Date orderApprovalTime;

    /**
     * 客户编码
     */
    @TableField(value = "customer_no")
    private String customerNo;

    /**
     * 客户名称
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 工作流ID
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
    @TableField(value = "company_no")
    private String companyNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     *订单类型
     */
    @TableField(value = "order_type")
    private String orderType;

    @TableField(value = "tb_status")
    private String tbStatus;
    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月9日 下午16:07:09
     * 构造方法名 : OrderLinePO()
     * 描述 :
     */
    public OrderLinePO() {
    }

    /**
     *
     * 创建人 :
     * 创建时间 : 2024年9月9日 下午16:07:09
     * 构造方法名 : OrderLinePO(Boolean deleted, Boolean isAutoFillUser) 
     * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
     */
    public OrderLinePO(Boolean deleted, Boolean isAutoFillUser) {
        super.setDeleted(deleted ? 1 : 0);
        super.setDeletedTime(new Date());
        if (isAutoFillUser) {
            super.setDeletedByName(null);
            super.setDeletedTime(null);
        }
    }

    /**
     *
     * 创建人 :
     * 创建时间 :  2024年9月9日 下午16:09:13
     * 描述 : 包装器
     * 包名 : com.ccit.area.sales.dao.domain.sales
     * 方法名 : wrapper
     *  LambdaQueryWrapper<OrderLinePO>
     *  @throws
     */
    public static LambdaQueryWrapper<OrderLinePO> wrapper() {
        LambdaQueryWrapper<OrderLinePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderLinePO::getDeleted, 0);
        return wrapper;
    }

}
