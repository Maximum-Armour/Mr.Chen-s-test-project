package com.ccit.area.sales.common.aop.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.service.AsyncService;
import com.ccit.area.sales.common.utils.JoinPointUtil;
import com.ccit.area.sales.dto.SystemLogAddDTO;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.ip.IpUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 描述 : 操作日志记录切面类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 下午3:34:35
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.aspect
 * 类名 : LogAspect
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
	
	/**
	 * “异步”服务类
	 */
	private final AsyncService asyncService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:35:12
	 * 描述 : 请求后进行通知
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : doAfterReturning
	 *  void  
	 *  @throws
	 */
	@AfterReturning(pointcut = "@annotation(entity)", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Log entity, Object jsonResult) {
		this.handleLog(joinPoint, entity, null, jsonResult);
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:35:28
	 * 描述 : 抛出异常之前通知
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : doAfterThrowing
	 *  void  
	 *  @throws
	 */
	@AfterThrowing(pointcut = "@annotation(entity)", throwing = "exp")
	public void doAfterThrowing(JoinPoint joinPoint, Log entity, Exception exp) {
		this.handleLog(joinPoint, entity, exp, null);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午3:42:00
	 * 描述 : 处理日志
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : handleLog
	 *  void  
	 *  @throws
	 */
	private void handleLog(final JoinPoint joinPoint, Log entity, final Exception exp, Object jsonResult) {
		HttpServletRequest request = ServletUtil.getRequest(); // 获取请求
		try {
			// 创建“操作日志记录”实体类
			SystemLogAddDTO systemLog = new SystemLogAddDTO();
			systemLog.setModuleName(entity.moduleName()); // 模块名称
			systemLog.setModuleDesc(entity.description()); // 模块描述
			systemLog.setRequestIp(IpUtil.getIpAddr(request)); // 请求IP
			systemLog.setRequestMethod(request.getMethod()); // 请求方式
			systemLog.setRequestPath(request.getRequestURI()); // 请求地址
			systemLog.setBusinessType(entity.businessType().toString()); // 业务操作类型
			// 方法名称
			String className = joinPoint.getTarget().getClass().getName();
			systemLog.setMethod(className + "." + joinPoint.getSignature().getName() + "()");
			// 判断是否异常
			if (exp != null) {
				systemLog.setStatus("YC"); // 操作状态为异常
				systemLog.setErrorMsg(StringUtils.substring(exp.getMessage(), 0, 2000)); // 记录异常日志并截取前2000字
			}
			// 是否需要保存request参数和值
			if (entity.isSaveRequestData()) {
				this.setRequestValue(joinPoint, systemLog);
			}
			// 是否需要保存response参数和值
			if (entity.isSaveResponseData() && jsonResult != null) {
				systemLog.setJsonResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
			}
			// 新增操作日志记录
			asyncService.insertLog(systemLog);
		} catch (Exception e) {
			log.error("记录操作日志失败:{}", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午4:09:22
	 * 描述 : 设置参数值(私有方法)
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : setRequestValue
	 *  void  
	 *  @throws
	 */
	private void setRequestValue(JoinPoint joinPoint, SystemLogAddDTO entity) {
		String requestMethod = entity.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params = JoinPointUtil.argsArrayToString(joinPoint.getArgs());
			entity.setRequestParam(StringUtils.substring(params, 0, 2000));
		} else {
			entity.setRequestParam(StringUtils.substring(JoinPointUtil.getRequestParam(), 0, 2000));
		}
	}
	
}
