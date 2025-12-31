package com.ccit.area.sales.dao.domain.marketing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
  * 描述 : “流程操作”实体类
  * 创建人 : tb
  * 创建时间 : 2024年9月26日 下午2:29:15
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.domain.bidding
  * 类名 : ProcessOperationPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_process_operation")
public class ProcessOperationPO extends BasePO<ProcessOperationPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 上级模块
     */
    @TableField(value = "parent_name")
    private String parentName;

    /**
     * 流程
     */
    @TableField(value = "technological_process")
    private String technologicalProcess;

    /**
     * 操作者
     */
    @TableField(value = "operator_name")
    private String operatorName;

    /**
     * 区域名称
     */
    @TableField(value = "operator_time")
    private Date operatorTime;

    /**
     * 操作
     */
    @TableField(value = "operation")
    private String operation;

    /**
     * 组织编码
     */
    @TableField(value = "org_no")
    private String orgNo;

    /**
     * 组织名称
     */
    @TableField(value = "org_name")
    private String orgName;

    /**
     * 部门编码
     */
    @TableField(value = "dept_no")
    private String deptNo;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     *
      * 创建人 : tb
      * 创建时间 : 2024年9月26日 下午4:11:05
      * 构造方法名 : ProcessOperationPO()
      * 描述 :
     */
    public ProcessOperationPO() {
    }

    /**
     *
      * 创建人 : tb
      * 创建时间 : 2024年9月26日 下午4:11:15
      * 构造方法名 : ProcessOperationPO(Boolean deleted, Boolean isAutoFillUser)
     * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
     */
    public ProcessOperationPO(Boolean deleted, Boolean isAutoFillUser) {
        super.setDeleted(deleted ? 1 : 0);
        super.setDeletedTime(new Date());
        if (isAutoFillUser) {
            super.setDeletedByName(null);
            super.setDeletedTime(null);
        }
    }

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年9月26日 下午4:12:08
     * 描述 : 包装器
     * 包名 : com.ccit.area.sales.dao.domain.bidding
     * 方法名 : wrapper
     *  LambdaQueryWrapper<ProcessOperationPO>
     *  @throws
     */
    public static LambdaQueryWrapper<ProcessOperationPO> wrapper() {
        LambdaQueryWrapper<ProcessOperationPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProcessOperationPO::getDeleted, 0);
        return wrapper;
    }
}
