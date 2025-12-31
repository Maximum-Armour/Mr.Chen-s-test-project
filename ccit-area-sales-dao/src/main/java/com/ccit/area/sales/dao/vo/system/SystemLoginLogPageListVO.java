package com.ccit.area.sales.dao.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
  * 描述 : “登录日志分页列表”VO
  * 创建人 : tb
  * 创建时间 : 2024年11月27日 下午3:44:21
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.vo.system
  * 类名 : SystemLoginLogPageListVO
 */
@Data
@Schema(description = "【登录日志分页列表】返回结果实体类")
public class SystemLoginLogPageListVO {

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
     * 请求Ip
     */
    @Schema(description = "请求Ip")
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
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    private String errorMsg;

    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    private String deviceType;
}
