package com.ccit.area.sales.common.exception.handler;

import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.exception.JwtTokenException;
import com.ccit.area.sales.common.exception.NotPermissionException;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.result.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 
 * 描述 : 全局异常处理
 * 创建人 : yn
 * 创建时间 : 2024年6月15日 下午3:25:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.exception.handler
 * 类名 : GlobalExceptionHandler
 */
// @Slf4j
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午9:55:23
	 * 描述 : 处理系统异常
	 * 包名 : com.ccit.area.sales.common.exception.handler
	 * 方法名 : handlerException
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseVO<String> handlerException(Exception e) {
		e.printStackTrace();
		log.error("系统异常: {}", e.getMessage(), e);
		return ResponseVO.failed(ResultMsg.FAILED.fillArgs(e.getMessage()));
	}
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午9:55:43
	 * 描述 : 处理方法参数无效异常
	 * 包名 : com.ccit.area.sales.common.exception.handler
	 * 方法名 : handlerMethodArgumentNotValidException
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseVO<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		String message = "操作失败";
		if (allErrors.size() > 0) {
			message = allErrors.get(0).getDefaultMessage();
		}
		// String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
		return ResponseVO.failed(ResultMsg.FAILED.fillArgs(message));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:39:53
	 * 描述 : 处理JwtToken异常
	 * 包名 : com.ccit.area.sales.common.exception.handler
	 * 方法名 : handlerJwtTokenException
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@ExceptionHandler(value = JwtTokenException.class)
	public ResponseVO<String> handlerJwtTokenException(JwtTokenException e) {
		return ResponseVO.failed(new ResultMsg("401", e.getMessage()));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:16:18
	 * 描述 : 处理鉴权异常
	 * 包名 : com.ccit.area.sales.common.exception.handler
	 * 方法名 : handlerNotPermissionException
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@ExceptionHandler(value = NotPermissionException.class)
	public ResponseVO<String> handlerNotPermissionException(NotPermissionException e) {
		return ResponseVO.failed(new ResultMsg("403", e.getMessage()));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午3:25:55
	 * 描述 : 处理业务异常
	 * 包名 : org.ning.music.common.security.handler
	 * 方法名 : handlerBusinessException
	 *  Result<String>  
	 *  @throws
	 */
	@ExceptionHandler(value = BusinessException.class)
	public ResponseVO<String> handlerBusinessException(BusinessException e) {
		return ResponseVO.failed(new ResultMsg("500", e.getMessage()));
	}
	
}
