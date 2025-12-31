package com.ccit.area.sales.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ccit.area.sales.web.intercept.JwtTokenInterceptor;


/**
 * 
 * 描述 : “WebMvc”配置类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:07:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.config
 * 类名 : WebMvcConfig
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	/**
	 * “JwtToken身份验证”拦截器
	 */
	@Autowired
	private JwtTokenInterceptor jwtTokenInterceptor;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:08:15
	 * 描述 : 添加跨域映射配置
	 * 包名 : com.ccit.area.sales.web.config
	 * 方法名 : addCorsMappings
	 *  void  
	 *  @throws
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:08:22
	 * 描述 : 添加拦截器配置
	 * 包名 : com.ccit.area.sales.web.config
	 * 方法名 : addInterceptors
	 *  void  
	 *  @throws
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenInterceptor) // 注册“JwtToken身份验证”拦截器
			.addPathPatterns("/**") // 拦截所有请求
			.excludePathPatterns(this.initPatterns()); // 过滤不拦截URL地址
	}
	
//	/**
//	 * 
//	 * 创建人 : yn
//	 * 创建时间 : 2024年7月11日 上午10:10:16
//	 * 描述 : 配置静态资源
//	 * 包名 : com.ccit.area.sales.web.config
//	 * 方法名 : addResourceHandlers
//	 *  void  
//	 *  @throws
//	 */
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/*.jsp").addResourceLocations("classpath:/webapp/");
//    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:09:05
	 * 描述 : 初始化不拦截的地址
	 * 包名 : com.ccit.area.sales.web.config
	 * 方法名 : initPatterns
	 *  List<String>  
	 *  @throws
	 */
	private List<String> initPatterns() {
		List<String> patterns = new ArrayList<>();
		patterns.add("/webapi/login"); // 登录
		patterns.add("/webapi/logout"); // 注销
		patterns.add("/webapi/common/getVerifyCode"); // 验证码
		// SwaggerUI文档~~~~~~~~~开始
		patterns.add("/doc.html");
		patterns.add("/webjars/**");
		patterns.add("/favicon.ico");
		patterns.add("/v3/api-docs/**");
		// SwaggerUI文档~~~~~~~~~结束
		patterns.add("/api/system/**"); // openFeign调用地址
		return patterns;
	}
	
}
