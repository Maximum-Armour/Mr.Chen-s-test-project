package com.ccit.area.sales.common.jwt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.common.utils.AESUtil;
import com.ccit.common.utils.ServletUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 描述 : JWT工具类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午3:43:04
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.jwt
 * 类名 : JwtUtils
 */
@Slf4j
@Component
public class JwtUtils {
	
	/**
	 * JWT属性
	 */
	@Autowired
	private JwtProperties properties;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:10:17
	 * 描述 : 获取默认头部Key值
	 * 包名 : com.ccit.area.sales.common.jwt
	 * 方法名 : getDefaultHeader
	 *  String  
	 *  @throws
	 */
	public String getDefaultHeader() {
		return properties.getHeader();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:45:05
	 * 描述 : 创建令牌
	 * 包名 : com.ccit.area.sales.common.jwt
	 * 方法名 : createToken
	 *  Map<String,Object>  
	 *  @throws
	 */
	public Map<String, Object> createToken(String subject) {
		Map<String, Object> resultMap = new HashMap<>(2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = new Date();
			Date expireDate = DateUtils.addMinutes(date, properties.getExpire());
			log.info("============================================================");
			log.info("【当前用户:{},当前日期:{},过期日期:{}】", subject, sdf.format(date), sdf.format(expireDate));
			log.info("============================================================");
			String token = Jwts.builder()
					.setSubject(AESUtil.encrypt(subject, GlobalConstants.AES_PARAM_KEY)) // AES加密
					.setIssuedAt(date) // 当前日期
					.setExpiration(expireDate) // 过期日期
					.signWith(SignatureAlgorithm.HS256, properties.getSecret()) // 签名算法以及密匙
				.compact();
			resultMap.put("token", token);
			resultMap.put("expire", expireDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:43:24
	 * 描述 : 校验令牌是否已过期
	 * 包名 : com.ccit.area.sales.common.jwt
	 * 方法名 : isExpiration
	 *  boolean  
	 *  @throws
	 */
	public boolean isExpiration(String token) {
		boolean result;
		try {
			result = getClaimsToken(token).getExpiration().before(new Date());
		} catch (Exception e) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:44:07
	 * 描述 : 获取令牌
	 * 包名 : com.ccit.area.sales.common.jwt
	 * 方法名 : getToken
	 *  String  
	 *  @throws
	 */
	public String getToken() {
		HttpServletRequest request = ServletUtil.getRequest();
		String token = request.getHeader(properties.getHeader());
		if (StringUtils.isEmpty(token)) {
			token = request.getParameter(properties.getHeader());
		}
		return token;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:43:32
	 * 描述 : 解析令牌获取内容
	 * 包名 : com.ccit.area.sales.common.jwt
	 * 方法名 : getClaimsToken
	 *  Claims  
	 *  @throws
	 */
	public Claims getClaimsToken(String token) {
		try {
			return Jwts.parser().setSigningKey(properties.getSecret()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午1:01:18
	 * 描述 : 获取令牌中的主题信息
	 * 包名 : com.ning.music.common.utils
	 * 方法名 : getTokenBySubject
	 * 参数 :	isDecrypt 是否AES解密
	 *  String  
	 *  @throws
	 */
	public String getTokenBySubject(Boolean isAesDecrypt) {
		String result = "";
		Claims claims = this.getClaimsToken(this.getToken());
		if (claims != null) {
			result = isAesDecrypt ? AESUtil.decrypt(claims.getSubject(), GlobalConstants.AES_PARAM_KEY)
					: claims.getSubject();
		}
		return result;
	}
	
}