package com.ccit.area.sales.web.controller.system;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemDictAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemDictEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemDictTreeDTO;
import com.ccit.area.sales.dao.vo.system.SystemDictChildrenVO;
import com.ccit.area.sales.dao.vo.system.SystemDictDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;
import com.ccit.area.sales.service.system.ISystemDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “字典管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 上午9:50:59
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemDictController
 */
@RestController
@Tag(name = "字典管理")
@RequestMapping(value = "/webapi/system/dict")
public class SystemDictController {

	/**
	 * “字典”服务类
	 */
	@Autowired
	private ISystemDictService systemDictService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:00:23
	 * 描述 : 字典树列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectTreeList
	 *  ResponseVO<List<SystemDictTreeVO>>  
	 *  @throws
	 */
	@Permissions(value = "dict:select")
	@Log(moduleName = "字典管理", description = "字典列表", businessType = BusinessType.SELECT)
	@Operation(summary = "字典树列表", description = "字典树列表，支持分页查询和高级查询")
	@PostMapping(value = "/tree/list")
	public ResponseVO<List<SystemDictTreeVO>> selectTreeList(@RequestBody SystemDictTreeDTO entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity);
		return ResponseVO.success(systemDictService.selectTreeList(param));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:00:29
	 * 描述 : 新增字典
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "dict:add")
	@Log(moduleName = "字典管理", description = "新增字典", businessType = BusinessType.INSERT)
	@Operation(summary = "新增字典")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SystemDictAddDTO entity) {
		return ResponseVO.success(systemDictService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:00:35
	 * 描述 : 获取字典
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : get
	 *  ResponseVO<SystemDictDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "dict:get")
	@Log(moduleName = "字典管理", description = "获取字典", businessType = BusinessType.GET)
	@Operation(summary = "获取字典")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "部门ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SystemDictDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemDictService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:00:43
	 * 描述 : 修改字典
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "dict:edit")
	@Log(moduleName = "字典管理", description = "修改字典", businessType = BusinessType.EDIT)
	@Operation(summary = "修改字典")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SystemDictEditDTO entity) {
		return ResponseVO.success(systemDictService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:00:49
	 * 描述 : 删除字典
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "dict:delete")
	@Log(moduleName = "字典管理", description = "删除字典", businessType = BusinessType.DELETE)
	@Operation(summary = "删除字典")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody Long id) {
		return ResponseVO.success(systemDictService.delete(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午10:03:15
	 * 描述 : 根据字典编码获取子节点数组
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : getChildrenList
	 *  ResponseVO<List<SystemDictChildrenVO>>  
	 *  @throws
	 */
	@Operation(summary = "根据字典编码获取子节点数组", description = "只可获取一级子节点")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "dictCode", description = "字典编码", 
				required = true, schema = @Schema(type = "String"))
	})
	@GetMapping(value = "/getChildrenList")
	public ResponseVO<List<SystemDictChildrenVO>> getChildrenList(@RequestParam("dictCode") String dictCode) {
		return ResponseVO.success(systemDictService.getChildrenList(dictCode));
	}

}
