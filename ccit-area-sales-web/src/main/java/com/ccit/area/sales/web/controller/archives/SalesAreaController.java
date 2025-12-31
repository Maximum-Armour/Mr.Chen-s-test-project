package com.ccit.area.sales.web.controller.archives;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.ccit.area.sales.dao.dto.archives.*;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
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
import com.ccit.area.sales.dao.vo.archives.SalesAreaDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SalesAreaPageListVO;
import com.ccit.area.sales.service.archives.ISalesAreaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “销售区域管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:42:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : SalesAreaController
 */
@RestController
@Tag(name = "销售区域管理")
@RequestMapping(value = "/webapi/archives/salesArea")
public class SalesAreaController {
		
	/**
	 * “销售区域”服务类
	 */
	@Autowired
    private ISalesAreaService salesAreaService;
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:45:36
	 * 描述 : 销售区域列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<SalesAreaPageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "salesArea:select")
	@Log(moduleName = "销售区域管理", description = "销售区域列表", businessType = BusinessType.SELECT)
	@Operation(summary = "销售区域列表", description = "销售区域列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<SalesAreaPageListVO>> selectPageList(@RequestBody PageEntity<SalesAreaPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(salesAreaService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:45:43
	 * 描述 : 新增销售区域
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "salesArea:add")
	@Log(moduleName = "销售区域管理", description = "新增销售区域", businessType = BusinessType.INSERT)
	@Operation(summary = "新增销售区域")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SalesAreaAddDTO entity) {
		return ResponseVO.success(salesAreaService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:45:49
	 * 描述 : 获取销售区域
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<SalesAreaDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "salesArea:get")
	@Log(moduleName = "销售区域管理", description = "获取销售区域", businessType = BusinessType.GET)
	@Operation(summary = "获取销售区域")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "销售区域ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SalesAreaDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(salesAreaService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:45:55
	 * 描述 : 修改销售区域
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "salesArea:edit")
	@Log(moduleName = "销售区域管理", description = "修改销售区域", businessType = BusinessType.EDIT)
	@Operation(summary = "修改销售区域")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SalesAreaEditDTO entity) {
		return ResponseVO.success(salesAreaService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:46:01
	 * 描述 : 删除销售区域
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "salesArea:delete")
	@Log(moduleName = "销售区域管理", description = "删除销售区域", businessType = BusinessType.DELETE)
	@Operation(summary = "删除销售区域")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SalesAreaDeleteDTO entity) {
		return ResponseVO.success(salesAreaService.delete(entity));
	}

//	/**
//	 *
//	 * 创建人 : tb
//	 * 创建时间 : 2024年10月18日 下午2:28:01
//	 * 描述 : 产品分类树列表，支持高级查询
//	 * 包名 : com.ccit.area.sales.web.controller.archives
//	 * 方法名 : selectPageList
//	 *  ResponseVO<List<ProductClassTreeVO>>
//	 *  @throws
//	 */
//	@Permissions(value = "/archives/salesArea/select")
//	@Log(moduleName = "销售区域", description = "销售区域树列表", businessType = BusinessType.SELECT)
//	@Operation(summary = "销售区域树列表", description = "销售区域树列表，支持高级查询")
//	@PostMapping(value = "/tree/list")
//	public ResponseVO<List<SalesAreaPageListVO>> selectPageList(@RequestBody SalesAreaPageListVO entity) {
//		return ResponseVO.success(salesAreaService.selectTreeList(entity));
//	}
}
