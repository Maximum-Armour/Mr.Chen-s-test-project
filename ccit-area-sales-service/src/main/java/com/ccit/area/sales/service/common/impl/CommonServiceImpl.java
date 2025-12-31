package com.ccit.area.sales.service.common.impl;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccit.area.sales.common.constants.RedisConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.dto.common.VerifyCodeVO;
import com.ccit.area.sales.service.common.ICommonService;
import com.ccit.common.utils.StringUtil;
import com.ccit.common.utils.VerifyCodeUtil;

/**
 * 
 * 描述 : “基础”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 上午10:09:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.common.impl
 * 类名 : CommonServiceImpl
 */
@Service
public class CommonServiceImpl implements ICommonService {

	/**
	 * 缓存工具类
	 */
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午10:10:02
	 * 描述 : 根据固定值、长度生成随机验证码
	 * 包名 : com.ccit.area.sales.service.common.impl
	 * 方法名 : generateVerifyCode
	 *  VerifyCodeVO  
	 *  @throws
	 */
	@Override
	public VerifyCodeVO generateVerifyCode(String key, Integer size) {
		if (StringUtil.isBlank(key)) {
			throw new BusinessException("唯一标识不能为空，请进行检查！");
		}
		try {
			String verifyCode = VerifyCodeUtil.generateVerifyCode(size);
			String verifyCodeKey = RedisConstants.VERIFY_CODE_KEY + key;
			String verifyCodeExpireTime = RedisConstants.VERIFY_CODE_EXPIRATION;
			redisUtils.set(verifyCodeKey, verifyCode, verifyCodeExpireTime, TimeUnit.MINUTES);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			VerifyCodeUtil.outputImage(111, 36, stream, verifyCode);
			VerifyCodeVO result = new VerifyCodeVO();
			result.setImgBytes(stream.toByteArray());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new BusinessException("获取验证码失败，请刷新浏览器重试！");
	}

}
