package com.ccit.area.sales.dao.domain.system;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “序列号”实体类
 * 创建人 : yn
 * 创建时间 : 2024年08月22日 上午10:05:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemSequencePO
 */
@Data
@Accessors(chain = true)
@TableName(value = "t_system_sequence")
public class SystemSequencePO implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableField(value = "code")
	private String code;
	
    /**
     * 值
     */
    @TableField(value = "value")
	private Long value;
	
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    
    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年12月6日 下午2:23:41
     * 构造方法名 : SystemSequencePO() 
     * 描述 : 
     */
    public SystemSequencePO() {
    }
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年12月6日 下午2:24:09
     * 构造方法名 : SystemSequencePO(String code, Long value) 
     * 描述 : 构造函数
     * 参数 :	code	编码
     *		value	值
     */
    public SystemSequencePO(String code, Long value) {
    	this.code = code;
    	this.value = value;
    }
	
}
