package com.ccit.area.sales.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import com.ccit.common.utils.ServletUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 
 * 描述 : Feign请求拦截器
 * 创建人 : yn
 * 创建时间 : 2024年10月31日 上午9:52:20
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.interceptor
 * 类名 : FeignHeaderInterceptor
 */
@Configuration
public class FeignHeaderInterceptor implements RequestInterceptor {
	
	final String TOKEN_KEY = "token";
	
	@Override
    public void apply(RequestTemplate template) {
		String token = "";
		HttpServletRequest request = ServletUtil.getRequest();
		if (request != null) {
			token = request.getHeader(TOKEN_KEY);
			if (StringUtils.isEmpty(token)) {
				token = request.getParameter(TOKEN_KEY);
			}
			if (StringUtils.isEmpty(token)){
				token = (String) request.getAttribute(TOKEN_KEY);
			}
		}
        template.header(TOKEN_KEY, token);
    }

}
