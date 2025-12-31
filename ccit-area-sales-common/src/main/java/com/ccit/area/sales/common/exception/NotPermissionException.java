package com.ccit.area.sales.common.exception;

/**
 * 
 * 描述 : 自定义【鉴权异常】
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午2:15:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.exception
 * 类名 : NotPermissionException
 */
public class NotPermissionException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public NotPermissionException() {
		super();
	}
	
	public NotPermissionException(String msg) {
		super(msg);
	}
	
}
