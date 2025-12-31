package com.ccit.area.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “登录日志新增”DTO
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:45:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dto
 * 类名 : SystemLoginLogAddDTO
 */
@Data
@Schema(description = "【登录日志新增】接受参数实体类")
public class SystemLoginLogAddDTO {

    /**
     * 请求方式
     */
	@Schema(description = "请求方式")
    private String requestMethod;

    /**
     * 请求地址
     */
	@Schema(description = "请求地址")
    private String requestPath;

    /**
     * 请求IP
     */
	@Schema(description = "请求IP")
    private String requestIp;

    /**
     * 请求参数
     */
	@Schema(description = "请求参数")
    private String requestParam;

    /**
     * 返回参数
     */
	@Schema(description = "返回参数")
    private String jsonResult;

    /**
     * 操作状态：0-正常；1-异常；
     */
	@Schema(description = "操作状态：0-正常；1-异常；")
    private Integer status;

    /**
     * 错误消息
     */
	@Schema(description = "错误消息")
    private String errorMsg;
	
	/**
     * 设备类型
     */
	@Schema(description = "设备类型")
	private String deviceType;
    
}
