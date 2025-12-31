package com.ccit.area.sales.common.result;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 描述 : 响应返回实体类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午10:53:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.result
 * 类名 : ResponseVO
 */
@Data
@NoArgsConstructor
@Schema(description = "全局结果集")
public class ResponseVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态码
	 */
	@Schema(description = "状态码")
	private String code;
	
	/**
	 * 提示信息
	 */
	@Schema(description = "提示信息")
	private String msg;
	
	/**
	 * 数据源
	 */
	@Schema(description = "数据源")
	private T data;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:47:29
	 * 构造方法名 : ResponseVO(ResultCode ResultCode)
	 * 描述 : 全局结果集编码
	 */
	private ResponseVO(ResultCode ResultCode) {
		this.code = ResultCode.getCode();
		this.msg = ResultCode.getMsg();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:47:41
	 * 构造方法名 : ResponseVO(T data)  
	 * 描述 : 数据源
	 */
	private ResponseVO(T data) {
		this.data = data;
		this.code = ResultCode.SUCCESS.getCode();
		this.msg = ResultCode.SUCCESS.getMsg();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:47:54
	 * 构造方法名 : ResponseVO(ResultCode ResultCode, T data) 
	 * 描述 : 全局结果集编码、数据源
	 */
	private ResponseVO(ResultCode ResultCode, T data) {
		this.data = data;
		this.code = ResultCode.getCode();
		this.msg = ResultCode.getMsg();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:48:37
	 * 构造方法名 : ResponseVO(ResultMsg ResultMsg)
	 * 描述 : 全局自定义错误提示信息
	 */
	private ResponseVO(ResultMsg ResultMsg) {
		this.code = ResultMsg.getCode();
		this.msg = ("%s".equals(ResultMsg.getMsg()) ? "操作失败" : ResultMsg.getMsg());
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:48:48
	 * 构造方法名 : ResponseVO(ResultMsg ResultMsg, T data) 
	 * 描述 : 全局自定义错误提示信息、数据源
	 */
	private ResponseVO(ResultMsg ResultMsg, T data) {
		this.data = data;
		this.code = ResultMsg.getCode();
		this.msg = ("%s".equals(ResultMsg.getMsg()) ? "操作失败" : ResultMsg.getMsg());
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:04
	 * 描述 : 调用成功
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : success
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> success() {
		return new ResponseVO<T>(ResultCode.SUCCESS);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:11
	 * 描述 : 调用成功【数据源】
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : success
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> success(T data) {
		return new ResponseVO<T>(data);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:18
	 * 描述 : 调用失败【全局结果集编码】
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : failed
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> failed(ResultCode ResultCode) {
		return new ResponseVO<T>(ResultCode);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:25
	 * 描述 : 调用失败【全局结果集编码、数据源】
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : failed
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> failed(ResultCode ResultCode, T data) {
		return new ResponseVO<T>(ResultCode, data);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:33
	 * 描述 : 调用失败【全局自定义错误提示信息】
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : failed
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> failed(ResultMsg ResultMsg) {
		return new ResponseVO<T>(ResultMsg);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月13日 下午8:49:38
	 * 描述 : 调用失败【全局自定义错误提示信息、数据源】
	 * 包名 : com.ccit.area.sales.common.result
	 * 方法名 : failed
	 *  ResponseVO<T>  
	 *  @throws
	 */
	public static <T> ResponseVO<T> failed(ResultMsg ResultMsg, T data) {
		return new ResponseVO<T>(ResultMsg, data);
	}
	
}
