package com.ccit.area.sales.dao.domain.system;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 描述 : “登录日志”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:27:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemLoginLogPO
 */
@Data
@Accessors(chain = true)
@TableName(value = "t_system_login_log")
public class SystemLoginLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求地址
     */
    @TableField(value = "request_path")
    private String requestPath;

    /**
     * 请求IP
     */
    @TableField(value = "request_ip")
    private String requestIp;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 返回参数
     */
    @TableField(value = "json_result")
    private String jsonResult;

    /**
     * 操作状态：0-正常；1-异常；
     */
    private Integer status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    private String errorMsg;
    
	/**
     * 设备类型
     */
    @TableField(value = "device_type")
	private String deviceType;

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

}
