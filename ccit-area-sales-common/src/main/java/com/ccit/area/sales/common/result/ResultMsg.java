package com.ccit.area.sales.common.result;

import lombok.Data;

/**
 * 
 * 描述 : 自定义错误提示类
 * 创建人 : yn
 * 创建时间 : 2024年6月13日 下午8:46:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.result
 * 类名 : ResultMsg
 */
@Data
public class ResultMsg {
	
	public static final ResultMsg FAILED = new ResultMsg("500", "%s");
	
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
	 * 创建时间 : 2024年6月13日 下午8:46:37
	 * 构造方法名 : ResultMsg(String code, String msg)
	 * 描述 : 状态码、提示信息
	 */
	public ResultMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:46:49
	 * 描述 : 填充参数
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : fillArgs
	 *  ResultMsg  
	 *  @throws
	 */
	public ResultMsg fillArgs(Object... args) {
		String code = this.code;
		String msg = String.format(this.msg, args);
		return new ResultMsg(code, msg);
	}
	
}
