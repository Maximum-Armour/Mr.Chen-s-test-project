package com.ccit.area.sales.web.controller.archives;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.archives.*;
import com.ccit.area.sales.dao.vo.archives.DepotDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DepotPageListVO;
import com.ccit.area.sales.dao.vo.archives.DepotTreeVO;
import com.ccit.area.sales.service.archives.IDepotService;
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
 * 描述 : “仓库档案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:55:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : DepotController
 */
@RestController
@Tag(name = "仓库档案管理")
@RequestMapping(value = "/webapi/archives/depot")
public class DepotController {

	/**
	 * “仓库档案”服务类
	 */
	@Autowired
    private IDepotService depotService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:45:00
	 * 描述 : 仓库档案树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<List<DepotTreeVO>>  
	 *  @throws
	 */
	@Permissions(value = "depot:select")
	@Log(moduleName = "仓库档案管理", description = "仓库档案树列表", businessType = BusinessType.SELECT)
	@Operation(summary = "仓库档案树列表", description = "仓库档案树列表，支持高级查询")
	@PostMapping(value = "/tree/list")
    public ResponseVO<List<DepotTreeVO>> selectPageList(@RequestBody DepotTreeDTO entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity);
		return ResponseVO.success(depotService.selectTreeList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 下午4:27:00
	 * 描述 : 仓库档案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<List<DepotPageListVO>>  
	 *  @throws
	 */
	//@Permissions(value = "depot:select")
	@Log(moduleName = "仓库档案管理", description = "仓库档案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "仓库档案列表", description = "仓库档案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<DepotPageListVO>> selectPageList(@RequestBody PageEntity<DepotPageListDTO> entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(depotService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:45:08
	 * 描述 : 新增仓库档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "depot:add")
	@Log(moduleName = "仓库档案管理", description = "新增仓库档案", businessType = BusinessType.INSERT)
	@Operation(summary = "新增仓库档案")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody DepotAddDTO entity) {
		return ResponseVO.success(depotService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:45:16
	 * 描述 : 获取仓库档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<DepotDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "depot:get")
	@Log(moduleName = "仓库档案管理", description = "获取仓库档案", businessType = BusinessType.GET)
	@Operation(summary = "获取仓库档案")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "仓库档案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<DepotDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(depotService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:45:22
	 * 描述 : 修改仓库档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "depot:edit")
	@Log(moduleName = "仓库档案管理", description = "修改仓库档案", businessType = BusinessType.EDIT)
	@Operation(summary = "修改仓库档案")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody DepotEditDTO entity) {
		return ResponseVO.success(depotService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:46:03
	 * 描述 : 删除仓库档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "depot:delete")
	@Log(moduleName = "仓库档案管理", description = "删除仓库档案", businessType = BusinessType.DELETE)
	@Operation(summary = "删除仓库档案")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody DepotDeleteDTO entity) {
		return ResponseVO.success(depotService.delete(entity));
	}
	
}
