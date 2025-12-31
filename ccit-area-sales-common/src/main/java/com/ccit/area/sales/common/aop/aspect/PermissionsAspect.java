package com.ccit.area.sales.common.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.NotPermissionException;
import com.ccit.area.sales.common.utils.CurrentUserUtil;

/**
 * 
 * 描述 : 鉴权切面类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午1:52:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.aop.aspect
 * 类名 : PermissionsAspect
 */
@Aspect
@Component
public class PermissionsAspect {

	/**
	 * 定义AOP签名(切入所有使用鉴权注解的方法)
	 */
	public static final String POINTCUT_SIGN = "@annotation(com.ccit.area.sales.common.aop.annotaion.Permissions)";

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:14:06
	 * 描述 : 声明AOP签名
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : pointcut
	 *  void  
	 *  @throws
	 */
	@Pointcut(POINTCUT_SIGN)
	public void pointcut() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:13:21
	 * 描述 : 环绕切入
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : around
	 *  Object  
	 *  @throws
	 */
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		this.checkMethodAnnotation(signature.getMethod());
		try {
			Object obj = joinPoint.proceed();
			return obj;
		} catch (Throwable e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午2:16:51
	 * 描述 : 检查方法注释
	 * 包名 : com.ccit.area.sales.common.aop.aspect
	 * 方法名 : checkMethodAnnotation
	 *  void  
	 *  @throws
	 */
	private void checkMethodAnnotation(Method method) {
		Permissions requiresPermissions = method.getAnnotation(Permissions.class);
		if (requiresPermissions != null) {
			JSONObject currentUser = CurrentUserUtil.getCurrentUser();
			String userName = currentUser.getString("userName");
			if (!GlobalConstants.SUPER_ADMIN.equals(userName)) {
				JSONArray currentUserPurview = currentUser.getJSONArray("purview");
				if (currentUserPurview == null || !currentUserPurview.contains(requiresPermissions.value())) {
					throw new NotPermissionException("没有访问权限，请联系业务员进行授权！");
				}
			}
		}
	}
	
}
