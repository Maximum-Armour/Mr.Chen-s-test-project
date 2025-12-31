package com.ccit.area.sales.web.controller.inner;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.system.SystemLogAddDTO;
import com.ccit.area.sales.dao.vo.system.SystemDictListVO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.area.sales.service.system.ISystemDictService;
import com.ccit.area.sales.service.system.ISystemLogService;
import com.ccit.area.sales.service.system.ISystemLoginLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “系统管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 上午11:09:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.inner
 * 类名 : SystemController
 */
@RestController
@Tag(name = "系统管理")
@RequestMapping(value = "/api/system")
public class SystemController {

	/**
	 * “操作日志记录”服务类
	 */
	@Autowired
    private ISystemLogService systemLogService;
	
	/**
	 * “登录日志”服务类
	 */
	@Autowired
    private ISystemLoginLogService systemLoginLogService;
	
	/**
	 * “字典”服务类
	 */
	@Autowired
	private ISystemDictService systemDictService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午4:18:28
	 * 描述 : 新增操作日志
	 * 包名 : com.ccit.area.sales.web.controller.inner
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Operation(summary = "新增操作日志")
	@PostMapping(value = "/log/add")
	public ResponseVO<String> logAdd(@Valid @RequestBody SystemLogAddDTO entity) {
		return ResponseVO.success(systemLogService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:52:38
	 * 描述 : 新增登录日志
	 * 包名 : com.ccit.area.sales.web.controller.inner
	 * 方法名 : loginLogAdd
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Operation(summary = "新增登录日志")
	@PostMapping(value = "/login/log/add")
	public ResponseVO<String> loginLogAdd(@Valid @RequestBody SystemLoginLogAddDTO entity) {
		return ResponseVO.success(systemLoginLogService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 下午2:38:51
	 * 描述 : 字典列表
	 * 包名 : com.ccit.area.sales.web.controller.inner
	 * 方法名 : selectList
	 *  List<SystemDictListVO>  
	 *  @throws
	 */
	@Operation(summary = "字典列表")
	@PostMapping(value = "/dict/list")
	public List<SystemDictListVO> selectList() {
		return systemDictService.selectList(new HashMap<>());
	}
	
}
