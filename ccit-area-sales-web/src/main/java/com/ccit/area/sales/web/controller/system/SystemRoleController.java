package com.ccit.area.sales.web.controller.system;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemRoleAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemRoleEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemRolePageListDTO;
import com.ccit.area.sales.dao.dto.system.SystemRolePermissionDTO;
import com.ccit.area.sales.dao.vo.system.SystemRoleDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemRolePageListVO;
import com.ccit.area.sales.service.system.ISystemRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “角色管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:13:13
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemRoleController
 */
@RestController
@Tag(name = "角色管理")
@RequestMapping(value = "/webapi/system/role")
public class SystemRoleController {

	/**
	 * “角色”服务类
	 */
	@Autowired
	private ISystemRoleService systemRoleService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午10:07:34
	 * 描述 : 角色列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<SystemRolePageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "role:select")
	@Log(moduleName = "角色管理", description = "角色列表", businessType = BusinessType.SELECT)
	@Operation(summary = "角色列表", description = "角色列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<SystemRolePageListVO>> selectPageList(@RequestBody PageEntity<SystemRolePageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(systemRoleService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:15:57
	 * 描述 : 新增角色
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "role:add")
	@Log(moduleName = "角色管理", description = "新增角色", businessType = BusinessType.INSERT)
	@Operation(summary = "新增角色")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SystemRoleAddDTO entity) {
		return ResponseVO.success(systemRoleService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:16:03
	 * 描述 : 获取角色
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : get
	 *  ResponseVO<SystemRoleDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "role:get")
	@Log(moduleName = "角色管理", description = "获取角色", businessType = BusinessType.GET)
	@Operation(summary = "获取角色")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "角色ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SystemRoleDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemRoleService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:16:09
	 * 描述 : 修改角色
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : edit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "role:edit")
	@Log(moduleName = "用户角色", description = "修改角色", businessType = BusinessType.EDIT)
	@Operation(summary = "修改角色")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SystemRoleEditDTO entity) {
		return ResponseVO.success(systemRoleService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:16:13
	 * 描述 : 删除角色
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "role:delete")
	@Log(moduleName = "用户角色", description = "删除角色", businessType = BusinessType.DELETE)
	@Operation(summary = "删除角色")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SystemRoleDeleteDTO entity) {
		return ResponseVO.success(systemRoleService.delete(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:27:55
	 * 描述 : 获取权限
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : getPermission
	 *  ResponseVO<List<Long>>  
	 *  @throws
	 */
	@Permissions(value = "role:permission")
	@Log(moduleName = "角色管理", description = "获取权限", businessType = BusinessType.GET)
	@Operation(summary = "获取权限")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "角色ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/getPermission")
	public ResponseVO<List<Long>> getPermission(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemRoleService.getPermission(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月9日 下午2:50:54
	 * 描述 : 保存权限
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : savePermission
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "role:permission")
	@Log(moduleName = "角色管理", description = "保存权限", businessType = BusinessType.GET)
	@Operation(summary = "保存权限")
	@PostMapping(value = "/savePermission")
	public ResponseVO<String> savePermission(@Valid @RequestBody SystemRolePermissionDTO entity) {
		return ResponseVO.success(systemRoleService.savePermission(entity));
	}
	
}
