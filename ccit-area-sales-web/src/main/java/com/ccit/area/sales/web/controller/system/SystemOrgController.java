package com.ccit.area.sales.web.controller.system;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemOrgAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgTreeDTO;
import com.ccit.area.sales.dao.vo.system.SystemOrgDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgTreeVO;
import com.ccit.area.sales.service.system.ISystemOrgService;
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
 * 描述 : “组织管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:06:05
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemOrgController
 */
@RestController
@Tag(name = "组织管理")
@RequestMapping(value = "/webapi/system/org")
public class SystemOrgController {
		
	/**
	 * “组织”服务类
	 */
	@Autowired
    private ISystemOrgService systemOrgService;
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年9月25日 下午3:20:51
	 * 描述 : 组织树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectTreeList
	 *  ResponseVO<List<SystemOrgTreeVO>>  
	 *  @throws
	 */
	@Permissions(value = "org:select")
	@Log(moduleName = "组织管理", description = "组织列表", businessType = BusinessType.SELECT)
	@Operation(summary = "组织树列表", description = "组织树列表，支持高级查询")
	@PostMapping(value = "/tree/list")
    public ResponseVO<List<SystemOrgTreeVO>> selectTreeList(@RequestBody SystemOrgTreeDTO entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity);
		return ResponseVO.success(systemOrgService.selectTreeList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:47
	 * 描述 : 新增组织
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "org:add")
	@Log(moduleName = "组织管理", description = "新增组织", businessType = BusinessType.INSERT)
	@Operation(summary = "新增组织")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SystemOrgAddDTO entity) {
		return ResponseVO.success(systemOrgService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:51
	 * 描述 : 获取组织
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : get
	 *  ResponseVO<SystemOrgDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "org:get")
	@Log(moduleName = "组织管理", description = "获取组织", businessType = BusinessType.GET)
	@Operation(summary = "获取组织")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "组织ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SystemOrgDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemOrgService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:56
	 * 描述 : 修改组织
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "org:edit")
	@Log(moduleName = "组织管理", description = "修改组织", businessType = BusinessType.EDIT)
	@Operation(summary = "修改组织")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SystemOrgEditDTO entity) {
		return ResponseVO.success(systemOrgService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:12:00
	 * 描述 : 删除组织
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "org:delete")
	@Log(moduleName = "组织管理", description = "删除组织", businessType = BusinessType.DELETE)
	@Operation(summary = "删除组织")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SystemOrgDeleteDTO entity) {
		return ResponseVO.success(systemOrgService.delete(entity));
	}

	@Operation(summary = "同步钉钉组织信息")
	@PostMapping("/synchronizationOAOrgMassages")
	public ResponseVO<String> synchronizationOAOrgMassages(@RequestBody List<String> deptList){
		return ResponseVO.success(systemOrgService.synchronizationOAOrgMassages(deptList));
	}

}
