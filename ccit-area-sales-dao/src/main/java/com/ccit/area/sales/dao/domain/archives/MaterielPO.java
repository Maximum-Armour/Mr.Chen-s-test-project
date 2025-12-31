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
 * 描述 : “产品档案”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 上午10:56:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : MaterielPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_materiel")
public class MaterielPO extends BasePO<MaterielPO> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String status;

    /**
     * 产品编码
     */
    @TableField(value = "materiel_no")
    private String materielNo;

    /**
     * 产品名称
     */
    @TableField(value = "materiel_name")
    private String materielName;

    /**
     * 产品等级
     */
    @TableField(value = "materiel_level")
    private String materielLevel;

    /**
     * 产品分类编码
     */
    @TableField(value = "product_class_no")
    private String productClassNo;

    /**
     * 产品分类名称
     */
    @TableField(value = "product_class_name")
    private String productClassName;

    /**
     * 牌号编码
     */
    @TableField(value = "sku_no")
    private String skuNo;

    /**
     * 牌号名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 生产厂商编码
     */
    @TableField(value = "manufacturer_code")
    private String manufacturerCode;

    /**
     * 生产厂商名称
     */
    @TableField(value = "manufacturer_name")
    private String manufacturerName;

    /**
     * 执行标准
     */
    @TableField(value = "executive_standard")
    private String executiveStandard;

    /**
     * 标准号
     */
    @TableField(value = "standard_number")
    private String standardNumber;

    /**
     * ERP产品编码
     */
    @TableField(value = "erp_materiel_no")
    private String erpMaterielNo;

    /**
     * ERP产品名称
     */
    @TableField(value = "erp_materiel_name")
    private String erpMaterielName;

    /**
     * ERP产品属性
     */
    @TableField(value = "erp_materiel_showdesc")
    private String erpMaterielShowdesc;

    /**
     * 税额
     */
    private String tax;

    /**
     * 开票名称
     */
    @TableField(value = "receipt_name")
    private String receiptName;

    /**
     * 开票规格
     */
    @TableField(value = "receipt_matriel_type")
    private String receiptMatrielType;

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
     * 创建时间 : 2024年7月16日 上午11:04:24
     * 构造方法名 : MaterielPO() 
     * 描述 : 
     */
	public MaterielPO() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午11:04:34
	 * 构造方法名 : MaterielPO(Boolean deleted, Boolean isAutoFillUser)
	 * 参数 :	deleted			删除标识：true-已删除；false-未删除；
     *		isAutoFillUser	是否自动填充用户：true-是；false-否；
	 */
	public MaterielPO(Boolean deleted, Boolean isAutoFillUser) {
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
	 * 创建时间 : 2024年7月16日 上午11:15:16
	 * 描述 : 包装器
	 * 包名 : com.ccit.area.sales.dao.domain.archives
	 * 方法名 : wrapper
	 *  LambdaQueryWrapper<MaterielPO>  
	 *  @throws
	 */
	public static LambdaQueryWrapper<MaterielPO> wrapper() {
		LambdaQueryWrapper<MaterielPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(MaterielPO::getDeleted, 0);
		return wrapper;
	}
    
}
