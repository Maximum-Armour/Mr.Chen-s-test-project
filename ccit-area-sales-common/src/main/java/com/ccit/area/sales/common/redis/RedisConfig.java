package com.ccit.area.sales.common.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 描述 : 缓存配置类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 上午11:06:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.redis
 * 类名 : RedisConfig
 */
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午11:07:02
	 * 描述 : 配置RedisTemplate
	 * 包名 : com.ccit.area.sales.common.redis
	 * 方法名 : redisTemplate
	 *  RedisTemplate<Object,Object>  
	 *  @throws
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		serializer.setObjectMapper(mapper);
		template.setValueSerializer(serializer);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
}
