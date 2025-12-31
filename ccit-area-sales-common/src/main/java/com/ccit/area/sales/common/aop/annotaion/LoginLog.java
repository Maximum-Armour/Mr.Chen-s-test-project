package com.ccit.area.sales.common.aop.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 描述 : 自定义【登录日志】注解
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:33:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.annotaion
 * 类名 : LoginLog
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午5:22:10
	 * 描述 : 是否保存请求的参数
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : isSaveRequestData
	 *  boolean  
	 *  @throws
	 */
	public boolean isSaveRequestData() default true;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午5:22:14
	 * 描述 : 是否保存响应的参数
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : isSaveResponseData
	 *  boolean  
	 *  @throws
	 */
	public boolean isSaveResponseData() default true;
	
}
