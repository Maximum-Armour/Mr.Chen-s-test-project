package com.ccit.area.sales.web.intercept;

import com.ccit.area.sales.common.jwt.JwtUtils;
import com.ccit.area.sales.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 
 * 描述 : “JwtToken身份验证”拦截器
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:09:26
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.intercept
 * 类名 : JwtTokenInterceptor
 */
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

	/**
	 * JWT工具类
	 */
	@Autowired
	private JwtUtils jwtUtils;
	
	/**
	 * Redis缓存工具类
	 */
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:09:44
	 * 描述 : 在请求处理之前进行调用【Controller方法调用之前】
	 * 包名 : com.ccit.area.sales.web.intercept
	 * 方法名 : preHandle
	 *  boolean  
	 *  @throws
	 */
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		// 如果请求方式为“OPTIONS”时，则返回true，表示可以正常方法
//		if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
//			return true;
//		}
//		// 获取默认头部Key值
//		String defaultHeader = jwtUtils.getDefaultHeader();
//		// 从Header中获取令牌
//		String token = request.getHeader(defaultHeader);
//		// 如果Header中令牌为空则从请求参数中获取令牌
//		if (StringUtils.isBlank(token)) {
//			token = request.getParameter(defaultHeader);
//		}
//		// 再次校验令牌是否为空
//		if (StringUtils.isBlank(token) || "null".equals(token)) {
//			throw new JwtTokenException("令牌不能为空，请检查！");
//		}
//		// 校验令牌是否已过期
//		if (jwtUtils.isExpiration(token)) {
//			throw new JwtTokenException("令牌已过期，请重新登录！");
//		}
//		// 解析令牌获取内容
//		Claims claims = jwtUtils.getClaimsToken(token);
//		// 判断Claims和Subject是否为空
//		if (claims == null || StringUtils.isBlank(claims.getSubject())) {
//			throw new JwtTokenException("令牌已过期，请重新登录！");
//		}
//		// 根据Subject从Redis缓存中获取Token令牌
//		String redisToken = (String) redisUtils.get(RedisConstants.USER_TOKEN + claims.getSubject());
//		// 校验RedisToken令牌是否为空
//		if (StringUtils.isBlank(redisToken) || "null".equals(redisToken)) {
//			throw new JwtTokenException("当前账号已经下线，请重新登录！");
//		}
//		// 判断Token令牌是否已被刷新
//		redisToken = AESUtil.decrypt(redisToken, GlobalConstants.AES_PARAM_KEY);
//		if (!token.equals(redisToken)) {
//			throw new JwtTokenException("当前账号已经下线，请重新登录！");
//		}
//		return true;
//	}
	
}
