package com.ccit.area.sales.dao.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “日志分页列表”DTO
 * 创建人 : yn
 * 创建时间 : 2024年10月30日 下午3:58:26
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.dto.system
 * 类名 : SystemLogPageListDTO
 */
@Data
@Schema(description = "【日志分页列表】接受参数实体类")
public class SystemLogPageListDTO {

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 模块描述
     */
    @Schema(description = "模块描述")
    private String moduleDesc;

    /**
     * 业务操作类型
     */
    @Schema(description = "业务操作类型")
    private String businessType;

    /**
     * 方法名称
     */
    @Schema(description = "方法名称")
    private String method;

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
     * 操作状态
     */
    @Schema(description = "操作状态")
    private String status;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String errorMsg;
	
}
