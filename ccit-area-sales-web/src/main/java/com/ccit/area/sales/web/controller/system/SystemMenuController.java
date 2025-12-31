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

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemMenuAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuMoveDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuTreeDTO;
import com.ccit.area.sales.dao.vo.system.SystemMenuDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemMenuTreeVO;
import com.ccit.area.sales.service.system.ISystemMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “菜单管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:12:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemMenuController
 */
@RestController
@Tag(name = "菜单管理")
@RequestMapping(value = "/webapi/system/menu")
public class SystemMenuController {
	
	/**
	 * “菜单”服务类
	 */
	@Autowired
	private ISystemMenuService systemMenuService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午9:25:30
	 * 描述 : 菜单树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectTreeList
	 *  ResponseVO<List<SystemMenuTreeVO>>  
	 *  @throws
	 */
	@Permissions(value = "menu:select")
	@Log(moduleName = "菜单管理", description = "菜单列表", businessType = BusinessType.SELECT)
	@Operation(summary = "菜单树列表", description = "菜单树列表，支持高级查询")
	@PostMapping(value = "/tree/list")
	public ResponseVO<List<SystemMenuTreeVO>> selectTreeList(@RequestBody SystemMenuTreeDTO entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity);
		return ResponseVO.success(systemMenuService.selectTreeList(param));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:36:39
	 * 描述 : 新增菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "menu:add")
	@Log(moduleName = "菜单管理", description = "新增菜单", businessType = BusinessType.INSERT)
	@Operation(summary = "新增菜单")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SystemMenuAddDTO entity) {
		return ResponseVO.success(systemMenuService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:36:43
	 * 描述 : 获取菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : get
	 *  ResponseVO<SystemMenuDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "menu:get")
	@Log(moduleName = "菜单管理", description = "获取菜单", businessType = BusinessType.GET)
	@Operation(summary = "获取菜单")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "菜单ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SystemMenuDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemMenuService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:36:48
	 * 描述 : 修改菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "menu:edit")
	@Log(moduleName = "菜单管理", description = "修改菜单", businessType = BusinessType.EDIT)
	@Operation(summary = "修改菜单")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SystemMenuEditDTO entity) {
		return ResponseVO.success(systemMenuService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:36:52
	 * 描述 : 删除菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "menu:delete")
	@Log(moduleName = "菜单管理", description = "删除菜单", businessType = BusinessType.DELETE)
	@Operation(summary = "删除菜单")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SystemMenuDeleteDTO entity) {
		return ResponseVO.success(systemMenuService.delete(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:36:57
	 * 描述 : 移动菜单
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : move
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "menu:move")
	@Log(moduleName = "菜单管理", description = "移动菜单", businessType = BusinessType.OTHER)
	@Operation(summary = "移动菜单")
	@PostMapping(value = "/move")
	public ResponseVO<String> move(@Valid @RequestBody SystemMenuMoveDTO entity) {
		return ResponseVO.success(systemMenuService.move(entity));
	}
	
}
