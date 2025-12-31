package com.ccit.area.sales.common.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.Data;

/**
 * 
 * 描述 : MinIO配置
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 下午4:42:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.minio
 * 类名 : MinioConfiguration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.minio")
public class MinioConfiguration {

	/**
	 * 私钥
	 */
	private String accessKey;
	
	/**
	 * 密钥
	 */
	private String secretKey;
	
	/**
	 * 服务器地址
	 */
	private String url;
	
	/**
	 * 存储桶名字
	 */
	private String bucketName;
	
	/**
	 * 上传范围
	 */
	private String uploadScope;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2023年11月13日 下午5:13:59
	 * 描述 : MinIO客户端
	 * 包名 : com.ccit.sem.system.server.config
	 * 方法名 : minioClient
	 *  MinioClient  
	 *  @throws
	 */
	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
	}
	
}
