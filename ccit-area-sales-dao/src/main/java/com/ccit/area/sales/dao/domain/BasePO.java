package com.ccit.area.sales.dao.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 描述 : 通用属性或方法
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午10:06:03
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain
 * 类名 : BasePO
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BasePO<T extends Model<?>> extends Model<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
	
	/**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建者名称
     */
    @TableField(value = "create_by_name", fill = FieldFill.INSERT)
    private String createByName;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新者名称
     */
    @TableField(value = "update_by_name", fill = FieldFill.INSERT_UPDATE)
    private String updateByName;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    /**
     * 删除标识：0-未删除；1-已删除；
     */
    @TableField(value = "is_deleted")
    private Integer deleted;

    /**
     * 删除者
     */
    @TableField(value = "deleted_by")
    private String deletedBy;

    /**
     * 删除者名称
     */
    @TableField(value = "deleted_by_name")
    private String deletedByName;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedTime;
    
}
