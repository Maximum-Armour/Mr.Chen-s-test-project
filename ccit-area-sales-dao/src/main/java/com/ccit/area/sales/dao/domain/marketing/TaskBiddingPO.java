package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_task_bidding")
public class TaskBiddingPO extends BasePO<TaskBiddingPO> {

    /**
     * 业务id
     */
    @TableField( value = "business_id")
    private Long businessId;
    /**
     * 开始时间
     */
    @TableField( value = "start_time")
    private Date startTime;
    /**
     * 推送标识
     */
    @TableField( value = "push_status")
    private int pushStatus;
}
