package com.ccit.area.sales.common.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * 描述 : JWT属性
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午3:42:39
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.jwt
 * 类名 : JwtProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	
	/**
	 * 加密秘钥
	 */
	private String secret;
	
	/**
	 * 有效期【分钟】
	 */
	private Integer expire;
	
	/**
	 * 头部key
	 */
	private String header;

}
