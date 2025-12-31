package com.ccit.area.sales.common.aop.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 描述 : 自定义【鉴权】注解
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午1:51:16
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.annotaion
 * 类名 : Permissions
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Permissions {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午1:51:52
	 * 描述 : 权限编码
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : value
	 *  String  
	 *  @throws
	 */
	String value() default "";
	
}
