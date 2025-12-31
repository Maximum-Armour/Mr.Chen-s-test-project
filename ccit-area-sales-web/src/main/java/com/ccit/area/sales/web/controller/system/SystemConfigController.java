package com.ccit.area.sales.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemConfigAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigPageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemConfigDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemConfigPageListVO;
import com.ccit.area.sales.service.system.ISystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 
 * 描述 : “参数配置管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:07:46
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemConfigController
 */
@RestController
@Tag(name = "参数配置管理")
@RequestMapping(value = "/webapi/system/config")
public class SystemConfigController {
		
	/**
	 * “参数配置”服务类
	 */
	@Autowired
    private ISystemConfigService systemConfigService;
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:42
	 * 描述 : 参数配置列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<SystemConfigPageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "config:select")
	@Operation(summary = "参数配置列表", description = "参数配置列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<SystemConfigPageListVO>> selectPageList(@RequestBody PageEntity<SystemConfigPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(systemConfigService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:47
	 * 描述 : 新增参数配置
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "config:add")
	@Operation(summary = "新增参数配置")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SystemConfigAddDTO entity) {
		return ResponseVO.success(systemConfigService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:51
	 * 描述 : 获取参数配置
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : get
	 *  ResponseVO<SystemConfigDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "config:get")
	@Operation(summary = "获取参数配置")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "参数配置ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SystemConfigDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(systemConfigService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:11:56
	 * 描述 : 修改参数配置
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "config:edit")
	@Operation(summary = "修改参数配置")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SystemConfigEditDTO entity) {
		return ResponseVO.success(systemConfigService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 上午11:12:00
	 * 描述 : 删除参数配置
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "config:delete")
	@Operation(summary = "删除参数配置")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SystemConfigDeleteDTO entity) {
		return ResponseVO.success(systemConfigService.delete(entity));
	}
	
}
