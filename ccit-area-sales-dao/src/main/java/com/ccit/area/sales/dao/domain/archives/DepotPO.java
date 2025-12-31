package com.ccit.area.sales.dao.domain.archives;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “仓库档案”实体对象
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:39:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : DepotPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_depot")
public class DepotPO extends BasePO<DepotPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 仓库编码
     */
    @TableField(value = "depot_no")
    private String depotNo;

    /**
     * 仓库名称
     */
    @TableField(value = "depot_name")
    private String depotName;

    /**
     * 仓库排序
     */
    @TableField(value = "depot_orders")
    private String depotOrders;

    /**
     * 仓库列号
     */
    @TableField(value = "depot_seq")
    private String depotSeq;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 是否港口
     */
    @TableField(value = "is_port")
    private String isPort;

    /**
     * 是否中转库
     */
    @TableField(value = "is_transit")
    private String isTransit;

    /**
     * 仓库地址
     */
    @TableField(value = "depot_address")
    private String depotAddress;

    /**
     * 省份编码
     */
    @TableField(value = "province_no")
    private String provinceNo;

    /**
     * 省份名称
     */
    @TableField(value = "province_name")
    private String provinceName;

    /**
     * 市区编码
     */
    @TableField(value = "city_no")
    private String cityNo;

    /**
     * 市区名称
     */
    @TableField(value = "city_name")
    private String cityName;

    /**
     * 区县编码
     */
    @TableField(value = "county_no")
    private String countyNo;

    /**
     * 区县名称
     */
    @TableField(value = "county_name")
    private String countyName;

    /**
     * 管理员
     */
    private String manager;

    /**
     * 管理员联系电话
     */
    @TableField(value = "manager_telephone")
    private String managerTelephone;

    /**
     * 工作流编码
     */
    private String workflowid;

    /**
     * 审核标志
     */
    private String shbz;

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
     * 创建人 : yn
     * 创建时间 : 2024年8月1日 上午9:08:03
     * 构造方法名 : DepotPO() 
     * 描述 : 
     */
	public DepotPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:08:09
	 * 构造方法名 : DepotPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public DepotPO(Boolean deleted, Boolean isAutoFillUser) {
    	super.setDeleted(deleted ? 1 : 0);
    	super.setDeletedTime(new Date());
    	if (isAutoFillUser) {
    		super.setDeletedByName(null);
    		super.setDeletedTime(null);
    	}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:08:26
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<DepotPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<DepotPO> wrapper() {
		LambdaQueryWrapper<DepotPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(DepotPO::getDeleted, 0);
		return wrapper;
	}
    
}
