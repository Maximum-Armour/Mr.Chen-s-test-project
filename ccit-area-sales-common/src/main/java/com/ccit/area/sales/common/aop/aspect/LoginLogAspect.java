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
import com.ccit.area.sales.common.aop.annotaion.LoginLog;
import com.ccit.area.sales.common.aop.service.AsyncService;
import com.ccit.area.sales.common.utils.JoinPointUtil;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.ip.IpUtil;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 描述 : 登录日志切面类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:41:48
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.aspect
 * 类名 : LoginLogAspect
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoginLogAspect {
	
	/**
	 * “异步”服务类
	 */
	private final AsyncService asyncService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:44:05
	 * 描述 : 请求后进行通知
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : doAfterReturning
	 *  void  
	 *  @throws
	 */
	@AfterReturning(pointcut = "@annotation(entity)", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, LoginLog entity, Object jsonResult) {
		this.handleLoginLog(joinPoint, entity, null, jsonResult);
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:44:09
	 * 描述 : 抛出异常之前通知
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : doAfterThrowing
	 *  void  
	 *  @throws
	 */
	@AfterThrowing(pointcut = "@annotation(entity)", throwing = "exp")
	public void doAfterThrowing(JoinPoint joinPoint, LoginLog entity, Exception exp) {
		this.handleLoginLog(joinPoint, entity, exp, null);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:44:18
	 * 描述 : 处理登录日志
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : handleLoginLog
	 *  void  
	 *  @throws
	 */
	private void handleLoginLog(final JoinPoint joinPoint, LoginLog entity, final Exception exp, Object jsonResult) {
		HttpServletRequest request = ServletUtil.getRequest(); // 获取请求
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		try {
			// 创建“登录日志”实体类
			SystemLoginLogAddDTO systemLoginLog = new SystemLoginLogAddDTO();
			systemLoginLog.setErrorMsg(null); // 错误消息默认为NULL
			systemLoginLog.setRequestIp(IpUtil.getIpAddr(request)); // 请求IP
			systemLoginLog.setRequestMethod(request.getMethod()); // 请求方式
			systemLoginLog.setRequestPath(request.getRequestURI()); // 请求地址
			systemLoginLog.setDeviceType(userAgent.getOperatingSystem().getName() + " " 
					+ userAgent.getBrowser().getName()); // 设备类型
			// 判断是否异常
			if (exp != null) {
				systemLoginLog.setStatus(1); // 操作状态为异常
				systemLoginLog.setErrorMsg(StringUtils.substring(exp.getMessage(), 0, 2000)); // 记录异常日志并截取前2000字
			}
			// 处理请求参数值
			if (entity.isSaveRequestData()) {
				this.setRequestValue(joinPoint, systemLoginLog);
			}
			// 是否需要保存response参数和值
			if (entity.isSaveResponseData() && jsonResult != null) {
				JSONObject parseObject = JSONObject.parseObject(JSONObject.toJSONString(jsonResult));
				if ("200".equals(parseObject.getString("code"))) {
					request.setAttribute("token", parseObject.getJSONObject("data").getString("token"));
				}
				systemLoginLog.setJsonResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
			}
			// 新增登录日志
			asyncService.insertLoginLog(systemLoginLog);
		} catch (Exception e) {
			log.error("记录操作日志失败:{}", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:46:28
	 * 描述 : 设置参数值(私有方法)
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : setRequestValue
	 *  void  
	 *  @throws
	 */
	private void setRequestValue(JoinPoint joinPoint, SystemLoginLogAddDTO entity) {
		String requestMethod = entity.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params = JoinPointUtil.argsArrayToString(joinPoint.getArgs());
			entity.setRequestParam(StringUtils.substring(params, 0, 2000));
		} else {
			entity.setRequestParam(StringUtils.substring(JoinPointUtil.getRequestParam(), 0, 2000));
		}
	}
	
}
