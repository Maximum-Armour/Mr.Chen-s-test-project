package com.ccit.area.sales.dao.dingding.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 钉钉模板类型字典表
 * @TableName t_approval_dictionary
 */
@TableName(value ="t_approval_dictionary")
@Data
public class TApprovalDictionaryPO implements Serializable {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 凭证类型
     */
    @TableField(value = "business")
    private String business;

    /**
     * 凭证类型
     */
    @TableField(value = "business_code")
    private String businessCode;

    /**
     * 数据库表名
     */
    @TableField("database_name")
    private String databaseName;

    /**
     * 字典值
     */
    @TableField(value = "dictionary")
    private String dictionary;

    @TableField(value = "is_deleted")
    private String isDeleted;
}