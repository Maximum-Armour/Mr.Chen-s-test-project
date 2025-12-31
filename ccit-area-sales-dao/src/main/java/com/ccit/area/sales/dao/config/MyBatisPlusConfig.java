package com.ccit.area.sales.dao.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus相关配置类
 *
 * @author: chinacoalit.com
 * @date 2024/1/9 15:49
 * @version: v1.0 初始化
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置分页插件
     * @param
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     * @exception
     * @author chinacoalit.com
     * @date 2024/1/9 15:35
     */
    @Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 向MyBatis-Plus的过滤器链中添加分页拦截器，需要设置数据库类型（主要用于分页方言）
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		// 添加乐观锁拦截器
		// interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
	}
}
