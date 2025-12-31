package com.ccit.area.sales.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.constants.RedisConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.jwt.JwtUtils;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.DateTimeUtil;
import com.ccit.area.sales.common.utils.PasswordUtil;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.dto.system.SystemLoginDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgUpdateStatusDTO;
import com.ccit.area.sales.dao.vo.system.SystemLoginVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.system.ISystemLoginService;
import com.ccit.area.sales.service.system.ISystemUserOrgService;
import com.ccit.area.sales.service.system.ISystemUserRoleService;
import com.ccit.area.sales.service.system.ISystemUserService;
import com.ccit.common.utils.AESUtil;
import com.ccit.common.utils.DateUtil;
import com.ccit.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 描述 : “登录”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月4日 上午11:07:19
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemLoginServiceImpl
 */
@Service
@Slf4j
public class SystemLoginServiceImpl implements ISystemLoginService {
	
	/**
	 * JWT工具类
	 */
	@Autowired
    private JwtUtils jwtUtils;
	
	/**
	 * 缓存工具类
	 */
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * “用户”服务类
	 */
	@Autowired
    private ISystemUserService systemUserService;

	/**
	 * “用户角色中间”服务类
	 */
	@Autowired
	private ISystemUserRoleService systemUserRoleService;

	@Autowired
	private ISystemUserOrgService iSystemUserOrgService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 上午8:57:56
	 * 描述 : 登录
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : login
	 *  SystemLoginVO  
	 *  @throws
	 */
	@Override
	public SystemLoginVO login(SystemLoginDTO entity) {
		// 1、校验验证码是否输入错误
		this.validateVerifyCode(entity);
		// 2、校验账号是否存在
		LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
		wrapper.eq(SystemUserPO::getUserName, entity.getUsername());
		SystemUserPO systemUser = systemUserService.getOne(wrapper);
		// 校验账号是否存在
		if (null == systemUser) {
			throw new BusinessException("账号不存在，请重新输入！");
		}
		// 3、校验账号是否被锁定、密码是否正确、状态是否被禁用
		this.validateAccount(entity, systemUser);
		// 4、生成令牌
		Map<String, Object> tokenMap = jwtUtils.createToken(systemUser.getUserName());
		if (tokenMap == null || tokenMap.isEmpty()) {
			throw new BusinessException("生成令牌失败，请联系管理员！");
		}
		// 令牌信息
		String token = (String) tokenMap.get("token");
		// 5、存储到缓存中
		this.setRedisValue(token, systemUser);
		SystemLoginVO result = new SystemLoginVO();
		result.setToken(token);

		// 6. 为用户初始化部门信息
		try {
			List<SystemUserDetailVO> selectUserDetailsList = systemUserService.selectUserDetailsList(systemUser.getUserName());

			for (SystemUserDetailVO systemUserDetailVO : selectUserDetailsList) {
				if ("1".equals(systemUserDetailVO.getDisplayStatus())) {
					return result;
				}
			}

			// 如果没有找到显示状态为 "1" 的部门信息，则选择第一个部门信息并尝试更新显示状态
			SystemUserDetailVO systemUserDetailVO = selectUserDetailsList.get(0);
			SystemOrgUpdateStatusDTO systemOrgUpdateStatusDTO = new SystemOrgUpdateStatusDTO();
			systemOrgUpdateStatusDTO.setUserId(systemUserDetailVO.getUserId());
			systemOrgUpdateStatusDTO.setOrgId(systemUserDetailVO.getDeptId());
			systemOrgUpdateStatusDTO.setDisplayStatus("1");
			String status = iSystemUserOrgService.updateDisplayStatus(systemOrgUpdateStatusDTO);

			if ("修改失败".equals(status)) {
				log.error("用户 {} 部门初始化信息初始失败: 修改显示状态失败", systemUser.getRealName());
			} else {
				log.info("用户 {} 部门初始化信息成功", systemUser.getRealName());
			}
		} catch (Exception e) {
			log.error("用户 {} 部门初始化信息初始失败: {}", systemUser.getRealName(), e.getMessage(), e);
		}

		return result;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:22:50
	 * 描述 : 注销
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : logout
	 *  void  
	 *  @throws
	 */
	@Override
	public void logout() {
		String tokenBySubject = jwtUtils.getTokenBySubject(false);
		String key = RedisConstants.USER_TOKEN + tokenBySubject;
		redisUtils.remove(key);
		key = RedisConstants.USER + jwtUtils.getToken();
		redisUtils.remove(key);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:28:12
	 * 描述 : 校验验证码是否输入错误
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : validateVerifyCode
	 *  void  
	 *  @throws
	 */
	private void validateVerifyCode(SystemLoginDTO entity) {
		if (!"pass".equals(entity.getVerifyCode())) {
			String verifyCodeKey = RedisConstants.VERIFY_CODE_KEY + entity.getVerifyCodeKey();
			String verifyCode = (String) redisUtils.get(verifyCodeKey);
			if (StringUtil.isNoneBlank(verifyCode)) {
				redisUtils.remove(verifyCodeKey);
				if (!verifyCode.equals(entity.getVerifyCode().toUpperCase())) {
					throw new BusinessException("验证码输入错误，请重新输入！");
				}
			} else {
				throw new BusinessException("验证码输入错误，请重新输入！");
			}
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:29:38
	 * 描述 : 校验账号是否被锁定、密码是否正确、状态是否被禁用
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : validateAccount
	 *  void  
	 *  @throws
	 */
	private void validateAccount(SystemLoginDTO entity, SystemUserPO systemUser) {
		// 校验账号是否被锁定
		if (systemUser.getStatus() == 2) {
			Integer accountLockTime = 5;
			Date lockedDate = systemUser.getLockedDate();
			Date unlockedDate = DateUtil.addMinutes(lockedDate, accountLockTime);
			if (new Date().after(unlockedDate)) {
            	systemUser.setStatus(0);
            	systemUser.setLockedDate(null);
                systemUser.setLoginFailCount(0);
                systemUserService.updateById(systemUser);
			} else {
				String unlockDateStr = DateTimeUtil.getTwoDateSubtract(unlockedDate, new Date());
				throw new BusinessException("您的账号已锁定，距离解锁时间还有" + unlockDateStr + "分钟");
			}
		}
		// 校验账号密码是否正确
		Boolean isPwd = false;
		try {
			// AES进行解密密码，如果失败说明密码不正确
			isPwd = PasswordUtil.getMatchesPwd(AESUtil.decrypt(entity.getPassword(), 
					GlobalConstants.AES_PARAM_KEY), systemUser.getPassword());
		} catch (Exception e) {
			isPwd = false;
		}
		// 开始校验密码
		if (!isPwd) {
			Integer accountLockCount = 5;
			Integer loginFailCount = systemUser.getLoginFailCount() + 1;
			Integer surplusCount = accountLockCount - loginFailCount;
			if (surplusCount <= 0) {
				systemUser.setStatus(2);
				systemUser.setLockedDate(new Date());
                systemUser.setLoginFailCount(loginFailCount);
                systemUserService.updateById(systemUser);
                throw new BusinessException("您的账户已被锁定，距离解锁时间还有05:00分钟");
			}
			systemUser.setLoginFailCount(loginFailCount);
            systemUserService.updateById(systemUser);
			throw new BusinessException("密码输入错误，您还有" + surplusCount + "次机会！"
					+ "密码输错" + accountLockCount + "次后，您的账户将被锁定5分钟！");
		}
		// 校验账号状态是否被禁用
		if (systemUser.getStatus() == 1) {
			throw new BusinessException("您的账号已被禁用，请联系管理员！");
		}
		// 登录成功后清空登录失败次数
		systemUser.setLoginFailCount(0);
        systemUserService.updateById(systemUser);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午3:54:42
	 * 描述 : 把用户信息存储到缓存中
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : setRedisValue
	 *  void  
	 *  @throws
	 */
	private void setRedisValue(String token, SystemUserPO systemUser) {
		// AES参数私钥
		String paramKey = GlobalConstants.AES_PARAM_KEY;
		// 存储用户当前登录令牌
		String key = RedisConstants.USER_TOKEN + AESUtil.encrypt(systemUser.getUserName(), paramKey);
		String value = AESUtil.encrypt(token, paramKey);
		redisUtils.set(key, value, RedisConstants.USER_EXPIRATION, TimeUnit.MINUTES);
		// 存储用户基础信息
		key = RedisConstants.USER + AESUtil.encrypt(systemUser.getUserName(), paramKey);
		JSONObject userJson = new JSONObject();
		userJson.put("userId", systemUser.getId());
		userJson.put("userName", systemUser.getUserName());
		userJson.put("realName", systemUser.getRealName());
		userJson.put("userPhoto", systemUser.getUserPhoto());
		userJson.put("email", systemUser.getEmail());
		userJson.put("mobile", systemUser.getMobile());
		userJson.put("signature", systemUser.getSignature());
		if (!GlobalConstants.SUPER_ADMIN.equals(systemUser.getUserName())) {
			List<String> purview = systemUserRoleService.getUserMenuPurview(systemUser.getUserName());
			userJson.put("purview", JSONArray.parseArray(JSON.toJSONString(purview)));
		} else {
			userJson.put("purview", JSONArray.parseArray("['*:*']"));
		}
		value = AESUtil.encrypt(JSON.toJSONString(userJson), paramKey);
		redisUtils.set(key, value, RedisConstants.USER_EXPIRATION, TimeUnit.MINUTES);
	}

}
