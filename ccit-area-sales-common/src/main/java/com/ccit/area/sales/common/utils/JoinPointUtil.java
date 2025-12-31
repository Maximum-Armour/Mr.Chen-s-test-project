package com.ccit.area.sales.common.utils;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ccit.common.utils.ServletUtil;

/**
 * 
 * 描述 : 切面编程工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:38:42
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.utils
 * 类名 : JoinPointUtil
 */
public class JoinPointUtil {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:40:10
	 * 描述 : 参数拼装
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : argsArrayToString
	 *  String  
	 *  @throws
	 */
	public static String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (Object o : paramsArray) {
				if (o != null && !isFilterObject(o)) {
					try {
						Object jsonObj = JSONObject.toJSON(o);
						params += jsonObj.toString() + " ";
					} catch (Exception e) {
					}
				}
			}
		}
		return params.trim();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:40:15
	 * 描述 : 判断是否需要过滤的对象
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : isFilterObject
	 *  boolean  
	 *  @throws
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isFilterObject(final Object o) {
		Class<?> clazz = o.getClass();
		if (clazz.isArray()) {
			return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
		} else if (Collection.class.isAssignableFrom(clazz)) {
			Collection collection = (Collection) o;
			for (Object value : collection) {
				return value instanceof MultipartFile;
			}
		} else if (Map.class.isAssignableFrom(clazz)) {
			Map map = (Map) o;
			for (Object value : map.entrySet()) {
				Map.Entry entry = (Map.Entry) value;
				return entry.getValue() instanceof MultipartFile;
			}
		}
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
				|| o instanceof BindingResult;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:40:28
	 * 描述 : 从请求中获取参数
	 * 包名 : com.ccit.area.sales.common.utils
	 * 方法名 : getRequestParam
	 *  String  
	 *  @throws
	 */
	public static String getRequestParam() {
		String requestParamObj = "";
		try {
			HttpServletRequest request = ServletUtil.getRequest();
			Map<String, String[]> params = request.getParameterMap();
			if (params != null && !params.isEmpty()) {
				requestParamObj = "?";
				for (Map.Entry<String, String[]> param : params.entrySet()) {
					requestParamObj += param.getKey() + "=" + StringUtils.join(param.getValue()).trim() + "&";
				}
				requestParamObj = requestParamObj.substring(0, requestParamObj.length() - 1);
			}
		} catch (Exception e) {
		}
		return requestParamObj;
	}
	
}
