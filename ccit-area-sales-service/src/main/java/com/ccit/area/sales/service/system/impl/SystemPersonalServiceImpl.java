package com.ccit.area.sales.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.constants.RedisConstants;
import com.ccit.area.sales.common.constants.RegexConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.exception.JwtTokenException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.common.utils.PasswordUtil;
import com.ccit.area.sales.dao.domain.system.SystemMenuPO;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.dto.system.SystemMyInfoDTO;
import com.ccit.area.sales.dao.dto.system.SystemMyPwdDTO;
import com.ccit.area.sales.dao.mapper.system.SystemPersonalMapper;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserTokenVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemPersonalMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemSecurityLogPageListVO;
import com.ccit.area.sales.service.system.ISystemMenuService;
import com.ccit.area.sales.service.system.ISystemPersonalService;
import com.ccit.area.sales.service.system.ISystemTreeNodeService;
import com.ccit.area.sales.service.system.ISystemUserRoleService;
import com.ccit.area.sales.service.system.ISystemUserService;
import com.ccit.common.utils.AESUtil;

/**
 * 
 * 描述 : “个人中心”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午4:34:
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemPersonalServiceImpl
 */
@Service
public class SystemPersonalServiceImpl implements ISystemPersonalService {
	
	/**
	 * “个人中心”接口
	 */
	@Autowired
	private SystemPersonalMapper systemPersonalMapper;

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
	 * “菜单”服务类
	 */
	@Autowired
	private ISystemMenuService systemMenuService;
	
	/**
	 * “用户角色中间”服务类
	 */
	@Autowired
	private ISystemUserRoleService systemUserRoleService;
	
	/**
	 * “树节点”服务类
	 */
	@Autowired
	private ISystemTreeNodeService systemTreeNodeService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:37:53
	 * 描述 : 获取当前登录用户信息
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getCurrentUser
	 *  SystemCurrentUserVO  
	 *  @throws
	 */
	@Override
	public SystemCurrentUserVO getCurrentUser() {
		JSONObject userJson = CurrentUserUtil.getCurrentUser();
		if (!userJson.isEmpty()) {
			return JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
		}
		throw new JwtTokenException("当前账户已经下线，请重新登录！");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:00:21
	 * 描述 : 获取当前登录用户菜单
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getCurrentUserMenu
	 *  List<SystemCurrentUserMenuVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<SystemCurrentUserMenuVO> getCurrentUserMenu() {
		List<SystemCurrentUserMenuVO> resultTreeList = new ArrayList<>();
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		// wrapper.in(SystemMenuPO::getMenuType, 1, 2); // 菜单类型：1-目录；2-菜单；
		wrapper.apply("menu_router IS NOT NULL AND menu_path IS NOT NULL "
				+ "AND menu_router != '' AND menu_path != ''", "");
		String userName = CurrentUserUtil.getCurrentUser().getString("userName");
		if (!GlobalConstants.SUPER_ADMIN.equals(userName)) {
			List<Long> userMenuIds = systemUserRoleService.getUserMenuIds(userName);
			if (CollectionUtils.isEmpty(userMenuIds)) {
				userMenuIds.add(0L);
			}
			wrapper.in(SystemMenuPO::getId, userMenuIds);
		}
		wrapper.orderByAsc(SystemMenuPO::getMenuOrders);
		List<SystemMenuPO> systemMenuList = systemMenuService.list(wrapper);
		if (!CollectionUtils.isEmpty(systemMenuList)) {
			List arrayCopyTo = ListUtil.arrayCopyTo(systemMenuList, SystemCurrentUserMenuVO.class);
			resultTreeList = systemTreeNodeService.changeDataUserMenuTree(arrayCopyTo);
		}
		return resultTreeList;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月24日 下午4:55:42
	 * 描述 : 根据菜单权限获取菜单详情
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getMenuDetails
	 *  SystemPersonalMenuVO  
	 *  @throws
	 */
	@Override
	public SystemPersonalMenuVO getMenuDetails(String menuPurview) {
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		wrapper.eq(SystemMenuPO::getMenuPurview, menuPurview);
		SystemMenuPO systemMenu = systemMenuService.getOne(wrapper);
		if (null == systemMenu) {
			throw new BusinessException("获取菜单信息失败，请刷新浏览器重新操作！");
		}
		SystemPersonalMenuVO result = new SystemPersonalMenuVO();
		BeanUtils.copyProperties(systemMenu, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:00:23
	 * 描述 : 修改个人信息
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : editMyInfo
	 *  String  
	 *  @throws
	 */
	@Override
	public String editMyInfo(SystemMyInfoDTO entity) {
		JSONObject userJson = CurrentUserUtil.getCurrentUser();
		if (userJson.isEmpty()) {
			throw new JwtTokenException("当前账户已经下线，请重新登录！");
		}
		UpdateWrapper<SystemUserPO> wrapper = new UpdateWrapper<>();
		Map<String, Object> param = MapUtil.objectToMap(entity);
		for (String key : param.keySet()) {
			String value = (String) param.get(key);
			wrapper.set(MapUtil.upperCharToUnderLine(key), value);
			userJson.put(key, value);
		}
		wrapper.eq("user_name", this.getCurrentUser().getUserName());
		boolean updateByIdFlag = systemUserService.update(wrapper);
		if (updateByIdFlag) {
			this.setRedisValue(userJson);
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:00:26
	 * 描述 : 修改个人密码
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : editMyPwd
	 *  String  
	 *  @throws
	 */
	@Override
	public String editMyPwd(SystemMyPwdDTO entity) {
		String userName = this.getCurrentUser().getUserName();
		LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
		wrapper.eq(SystemUserPO::getUserName, userName);
		SystemUserPO systemUser = systemUserService.getOne(wrapper);
		if (null == systemUser) {
			throw new BusinessException("修改失败，未获取当前登录用户信息！");
		}
		Boolean isPwd = PasswordUtil.getMatchesPwd(entity.getOldPwd(), systemUser.getPassword());
		if (!isPwd) {
			throw new BusinessException("原密码输入错误，请重新输入！");
		}
		if (entity.getOldPwd().equals(entity.getNewPwd())) {
			throw new BusinessException("原密码不能与新密码相同！");
		}
		if (!entity.getNewPwd().matches(RegexConstants.PWD)) {
			throw new BusinessException("新密码长度必须为8-16位并且包含大小写字母、数字、特殊字符（~!@&%#_*,.）");
		}
		if (!entity.getConfirmPwd().matches(RegexConstants.PWD)) {
			throw new BusinessException("确认密码长度必须为8-16位并且包含大小写字母、数字、特殊字符（~!@&%#_*,.）");
		}
		if (!entity.getNewPwd().equals(entity.getConfirmPwd())) {
			throw new BusinessException("两次输入密码不一致，请重新输入！");
		}
		systemUser.setPasswordExpired(0);
		systemUser.setPasswordLastChanged(new Date());
		systemUser.setPassword(PasswordUtil.getEncodePwd(entity.getConfirmPwd()));
		boolean updateByIdFlag = systemUserService.updateById(systemUser);
		if (updateByIdFlag) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午4:10:19
	 * 描述 : 安全日志分页列表
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : securityLogPageList
	 *  Page<SystemSecurityLogPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SystemSecurityLogPageListVO> securityLogPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SystemSecurityLogPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("userId", CurrentUserUtil.getCurrentUser().getLong("userId"));
		pages.setRecords(systemPersonalMapper.securityLogPageList(pages, param));
		return pages;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月21日 下午1:56:31
	 * 描述 : 验证原密码
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : checkOldPwd
	 *  Boolean  
	 *  @throws
	 */
	@Override
	public Boolean checkOldPwd(String oldPwd) {
		LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
		wrapper.eq(SystemUserPO::getUserName, this.getCurrentUser().getUserName());
		SystemUserPO systemUser = systemUserService.getOne(wrapper);
		if (null == systemUser) {
			return false;
		}
		Boolean isPwd = PasswordUtil.getMatchesPwd(oldPwd, systemUser.getPassword());
		if (!isPwd) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月26日 下午8:51:16
	 * 描述 : 验证令牌是否过期
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : checkToken
	 *  SystemCurrentUserTokenVO  
	 *  @throws
	 */
	@Override
	public SystemCurrentUserTokenVO checkToken() {
		LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
		wrapper.eq(SystemUserPO::getUserName, this.getCurrentUser().getUserName());
		SystemUserPO systemUser = systemUserService.getOne(wrapper);
		if (systemUser != null) {
			SystemCurrentUserTokenVO result = new SystemCurrentUserTokenVO();
			result.setPasswordExpired(systemUser.getPasswordExpired());
			return result;
		}
		throw new JwtTokenException("当前账户已经下线，请重新登录！");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午5:08:22
	 * 描述 : 把用户信息存储到缓存中
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : setRedisValue
	 *  void  
	 *  @throws
	 */
	private void setRedisValue(JSONObject userJson) {
		// AES参数私钥
		String paramKey = GlobalConstants.AES_PARAM_KEY;
		// 存储用户基础信息
		String key = RedisConstants.USER + AESUtil.encrypt(userJson.getString("userName"), paramKey);
		String value = AESUtil.encrypt(JSON.toJSONString(userJson), paramKey);
		redisUtils.set(key, value, RedisConstants.USER_EXPIRATION, TimeUnit.MINUTES);
	}

}
