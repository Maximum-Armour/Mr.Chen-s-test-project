package com.ccit.area.sales.common.aop.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ccit.area.sales.common.aop.enums.BusinessType;

/**
 * 
 * 描述 : 自定义【操作日志记录】注解
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午3:04:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.annotaion
 * 类名 : Log
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:04:39
	 * 描述 : 模块名称
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : moduleName
	 *  String  
	 *  @throws
	 */
	public String moduleName() default "";
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:04:43
	 * 描述 : 模块描述
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : description
	 *  String  
	 *  @throws
	 */
	public String description() default "";
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:04:47
	 * 描述 : 业务操作类型
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : businessType
	 *  BusinessType  
	 *  @throws
	 */
	public BusinessType businessType() default BusinessType.OTHER;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:04:51
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
	 * 创建时间 : 2024年7月9日 下午3:04:56
	 * 描述 : 是否保存响应的参数
	 * 包名 : com.ccit.area.sales.common.aop.annotaion
	 * 方法名 : isSaveResponseData
	 *  boolean  
	 *  @throws
	 */
	public boolean isSaveResponseData() default true;
	
}
