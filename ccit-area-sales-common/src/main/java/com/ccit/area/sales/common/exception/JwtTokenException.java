package com.ccit.area.sales.common.exception;

/**
 * 
 * 描述 : 自定义【JwtToken异常】
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午4:39:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.exception
 * 类名 : JwtTokenException
 */
public class JwtTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public JwtTokenException() {
		super();
	}
	
	public JwtTokenException(String msg) {
		super(msg);
	}
	
}
