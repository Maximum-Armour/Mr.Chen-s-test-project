package com.ccit.area.sales.dao.domain.system;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 描述 : “操作日志记录”实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:49:22
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.domain.system
 * 类名 : SystemLogPO
 */
@Data
@Accessors(chain = true)
@TableName(value = "t_system_log")
public class SystemLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 模块描述
     */
    @TableField(value = "module_desc")
    private String moduleDesc;

    /**
     * 业务操作类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 方法名称
     */
    private String method;

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
     * 操作状态
     */
    private String status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    private String errorMsg;
    
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
