package com.ccit.area.sales.web.controller.archives;

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
import com.ccit.area.sales.dao.dto.archives.ProductClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassEditDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassTreeDTO;
import com.ccit.area.sales.dao.vo.archives.ProductClassDetailsVO;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
import com.ccit.area.sales.service.archives.IProductClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “产品分类管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午2:24:53
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : ProductClassController
 */
@RestController
@Tag(name = "产品分类管理")
@RequestMapping(value = "/webapi/archives/productClass")
public class ProductClassController {

	/**
	 * “产品分类表”服务类
	 */
	@Autowired
    private IProductClassService productClassService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:28:01
	 * 描述 : 产品分类树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<List<ProductClassTreeVO>>  
	 *  @throws
	 */
	@Permissions(value = "productClass:select")
	@Log(moduleName = "产品分类管理", description = "产品分类树列表", businessType = BusinessType.SELECT)
	@Operation(summary = "产品分类树列表", description = "产品分类树列表，支持高级查询")
	@PostMapping(value = "/tree/list")
    public ResponseVO<List<ProductClassTreeVO>> selectPageList(@RequestBody ProductClassTreeDTO entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity);
		return ResponseVO.success(productClassService.selectTreeList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:28:27
	 * 描述 : 新增产品分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "productClass:add")
	@Log(moduleName = "产品分类管理", description = "新增产品分类", businessType = BusinessType.INSERT)
	@Operation(summary = "新增产品分类")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody ProductClassAddDTO entity) {
		return ResponseVO.success(productClassService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:28:33
	 * 描述 : 获取产品分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<ProductClassDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "productClass:get")
	@Log(moduleName = "产品分类管理", description = "获取产品分类", businessType = BusinessType.GET)
	@Operation(summary = "获取产品分类")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "产品分类ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<ProductClassDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(productClassService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:28:39
	 * 描述 : 修改产品分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "productClass:edit")
	@Log(moduleName = "产品分类管理", description = "修改产品分类", businessType = BusinessType.EDIT)
	@Operation(summary = "修改产品分类")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody ProductClassEditDTO entity) {
		return ResponseVO.success(productClassService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:28:45
	 * 描述 : 删除产品分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "productClass:delete")
	@Log(moduleName = "产品分类管理", description = "删除产品分类", businessType = BusinessType.DELETE)
	@Operation(summary = "删除产品分类")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody ProductClassDeleteDTO entity) {
		return ResponseVO.success(productClassService.delete(entity));
	}
	
}
