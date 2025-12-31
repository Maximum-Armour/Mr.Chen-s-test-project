package com.ccit.area.sales.dao.vo.system;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * 描述 : “安全日志分页列表”VO
 * 创建人 : yn
 * 创建时间 : 2024年10月30日 下午5:05:24
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.vo.system
 * 类名 : SystemSecurityLogPageListVO
 */
@Data
@Schema(description = "【安全日志分页列表】返回结果实体类")
public class SystemSecurityLogPageListVO {

    /**
     * 请求IP
     */
    @Schema(description = "请求IP")
    private String requestIp;
    
    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    private String deviceType;

    /**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date gmtCreate;
	
}
