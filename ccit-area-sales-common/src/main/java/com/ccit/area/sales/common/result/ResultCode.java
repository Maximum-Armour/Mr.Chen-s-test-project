package com.ccit.area.sales.common.result;

import java.io.Serializable;

/**
 * 
 * 描述 : 结果集编码类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午10:46:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.result
 * 类名 : ResultCode
 */
public enum ResultCode implements Serializable {
	
	SUCCESS("200", "操作成功"),
	FAILED("200", "操作失败"),
	NO_AUTH("401", "未授权");
	
	/**
	 * 状态码
	 */
	private String code;
	
	/**
	 * 提示信息
	 */
	private String msg;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:46:03
	 * 构造方法名 : ResultCode(String code, String msg) 
	 * 描述 : 状态码、提示信息
	 */
	ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
