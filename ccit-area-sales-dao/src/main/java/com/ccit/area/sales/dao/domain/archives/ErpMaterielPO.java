package com.ccit.area.sales.dao.domain.archives;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ccit.area.sales.dao.domain.BasePO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “ERP产品档案”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午4:48:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.archives
 * 类名 : ErpMaterielPO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_erp_materiel")
public class ErpMaterielPO extends BasePO<ErpMaterielPO> {

    private static final long serialVersionUID = 1L;

    /**
     * ERP产品属性
     */
    @TableField(value = "erp_materiel_showdesc")
    private String erpMaterielShowdesc;

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
     * 计量单位
     */
    private String unit;

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

}
