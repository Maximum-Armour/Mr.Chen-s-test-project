package com.ccit.area.sales.common.exception;

/**
 * 
 * 描述 : 自定义【业务异常】
 * 创建人 : yn
 * 创建时间 : 2024年6月13日 下午8:50:52
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.util.exception
 * 类名 : BusinessException
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
	}
	
}
