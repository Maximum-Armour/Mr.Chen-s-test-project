package com.ccit.area.sales.web.controller.system;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemMyInfoDTO;
import com.ccit.area.sales.dao.dto.system.SystemMyPwdDTO;
import com.ccit.area.sales.dao.dto.system.SystemSecurityLogPageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserTokenVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemPersonalMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemSecurityLogPageListVO;
import com.ccit.area.sales.service.system.ISystemPersonalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “个人中心管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午4:32:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemPersonalController
 */
@RestController
@Tag(name = "个人中心管理")
@RequestMapping(value = "/webapi/system/personal")
public class SystemPersonalController {

	/**
	 * “个人中心”服务类
	 */
	@Autowired
	public ISystemPersonalService systemPersonalService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:35:54
	 * 描述 : 获取当前登录用户信息
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : getCurrentUser
	 *  ResponseVO<SystemCurrentUserVO>  
	 *  @throws
	 */
	@Operation(summary = "获取当前登录用户信息")
	@GetMapping(value = "/getCurrentUser")
	public ResponseVO<SystemCurrentUserVO> getCurrentUser() {
		return ResponseVO.success(systemPersonalService.getCurrentUser());
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:57:24
	 * 描述 : 获取当前登录用户菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : getCurrentUserMenu
	 *  ResponseVO<List<SystemCurrentUserMenuVO>>  
	 *  @throws
	 */
	@Operation(summary = "获取当前登录用户菜单")
    @GetMapping(value = "/getCurrentUserMenu")
	public ResponseVO<List<SystemCurrentUserMenuVO>> getCurrentUserMenu() {
		return ResponseVO.success(systemPersonalService.getCurrentUserMenu());
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月24日 下午4:54:50
	 * 描述 : 根据菜单权限获取菜单详情
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : getMenuDetails
	 *  ResponseVO<SystemPersonalMenuVO>  
	 *  @throws
	 */
	@Operation(summary = "根据菜单权限获取菜单详情")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "menuPurview", description = "菜单权限", 
				required = true, schema = @Schema(type = "String"))
	})
	@GetMapping(value = "/getMenuDetails")
	public ResponseVO<SystemPersonalMenuVO> getMenuDetails(
			@RequestParam(value = "menuPurview", required = true) String menuPurview) {
		return ResponseVO.success(systemPersonalService.getMenuDetails(menuPurview));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:57:31
	 * 描述 : 修改个人信息
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : editMyInfo
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Operation(summary = "修改个人信息")
    @PostMapping(value = "/editMyInfo")
    public ResponseVO<String> editMyInfo(@Valid @RequestBody SystemMyInfoDTO entity) {
		return ResponseVO.success(systemPersonalService.editMyInfo(entity));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月8日 下午4:57:36
	 * 描述 : 修改个人密码
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : editMyPwd
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Operation(summary = "修改个人密码")
    @PostMapping(value = "/editMyPwd")
    public ResponseVO<String> editMyPwd(@Valid @RequestBody SystemMyPwdDTO entity) {
		return ResponseVO.success(systemPersonalService.editMyPwd(entity));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年9月24日 下午3:24:38
	 * 描述 : 安全日志分页列表
	 * 包名 : com.ning.music.system.controller.system
	 * 方法名 : securityLogPageList
	 *  Result<Page<SystemSecurityLogPageListVO>>  
	 *  @throws
	 */
	@Operation(summary = "安全日志分页列表")
    @PostMapping(value = "/securityLog/page/list")
	public ResponseVO<Page<SystemSecurityLogPageListVO>> securityLogPageList(
			@RequestBody PageEntity<SystemSecurityLogPageListDTO> entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(systemPersonalService.securityLogPageList(param));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月21日 下午1:55:31
	 * 描述 : 验证原密码
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : checkOldPwd
	 *  ResponseVO<Boolean>  
	 *  @throws
	 */
	@Operation(summary = "验证原密码")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "oldPwd", description = "原密码", 
				required = true, schema = @Schema(type = "String"))
	})
	@GetMapping(value = "/checkOldPwd")
	public ResponseVO<Boolean> checkOldPwd(@RequestParam(value = "oldPwd", required = true) String oldPwd) {
		return ResponseVO.success(systemPersonalService.checkOldPwd(oldPwd));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月26日 下午8:50:23
	 * 描述 : 验证令牌是否过期
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : checkToken
	 *  ResponseVO<SystemCurrentUserTokenVO>  
	 *  @throws
	 */
	@Operation(summary = "验证令牌是否过期")
	@GetMapping(value = "/checkToken")
	public ResponseVO<SystemCurrentUserTokenVO> checkToken() {
		return ResponseVO.success(systemPersonalService.checkToken());
    }
	
}
