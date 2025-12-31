package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPricePageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceSelectVO;
import com.ccit.area.sales.service.marketing.IProductPriceService;
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
 * 描述 : “产品定价管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:23
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.marketing
 * 类名 : ProductPriceController
 */
@RestController
@Tag(name = "产品定价管理")
@RequestMapping(value = "/webapi/marketing/productPrice")
public class ProductPriceController {

	/**
	 * “产品定价”服务类
	 */
	@Autowired
    private IProductPriceService productPriceService;
    
    /**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 产品定价列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  Result<Page<ProductPricePageListVO>>  
	 *  @throws
	 */
    @Permissions(value = "productPrice:select")
	@Log(moduleName = "产品定价管理", description = "产品定价列表", businessType = BusinessType.SELECT)
	@Operation(summary = "产品定价列表", description = "产品定价列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<ProductPricePageListVO>> selectPageList(@RequestBody PageEntity<ProductPricePageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(productPriceService.selectPageList(param));
    }
    
    /**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 新增产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : add
	 *  Result<String> 	  
	 *  @throws
	 */
//    @Permissions(value = "productPrice:insert")
	@Log(moduleName = "产品定价管理", description = "新增产品定价", businessType = BusinessType.INSERT)
	@Operation(summary = "新增产品定价")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody ProductPriceAddDTO entity) {
		return ResponseVO.success(productPriceService.add(entity));
	}
    
    /**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 获取产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : get
	 *  Result<产品定价DetailsVO>  
	 *  @throws
	 */
    @Permissions(value = "productPrice:get")
	@Log(moduleName = "产品定价管理", description = "获取产品定价", businessType = BusinessType.GET)
	@Operation(summary = "获取产品定价")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "产品定价ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<ProductPriceDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(productPriceService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 修改产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "productPrice:edit")
	@Log(moduleName = "产品定价管理", description = "修改产品定价", businessType = BusinessType.EDIT)
	@Operation(summary = "修改产品定价")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody ProductPriceEditDTO entity) {
		return ResponseVO.success(productPriceService.edit(entity));
	}
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:22
	 * 描述 : 删除产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "productPrice:delete")
	@Log(moduleName = "产品定价管理", description = "删除产品定价", businessType = BusinessType.DELETE)
	@Operation(summary = "删除产品定价")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody ProductPriceDeleteDTO entity) {
		return ResponseVO.success(productPriceService.delete(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月12日 下午04:23:23
	 * 描述 : 修改产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "productPrice:submit")
	@Log(moduleName = "产品定价管理", description = "提交产品定价", businessType = BusinessType.EDIT)
	@Operation(summary = "提交产品定价")
	@PostMapping(value = "/submit")
	public ResponseVO<String> submit(@Valid @RequestBody ProductPriceSubmitDTO entity) {
		return ResponseVO.success(productPriceService.submit(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月12日 下午04:23:23
	 * 描述 : 修改产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "productPrice:examine")
	@Log(moduleName = "产品定价管理", description = "审核产品定价", businessType = BusinessType.EDIT)
	@Operation(summary = "审核产品定价")
	@PostMapping(value = "/examine")
	public ResponseVO<String> examine(@Valid @RequestBody ProductPriceSubmitDTO entity) {
		return ResponseVO.success(productPriceService.examine(entity));
	}

	/**
	 *
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 获取产品定价
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : get
	 *  Result<产品定价DetailsVO>
	 *  @throws
	 */
	@Permissions(value = "productPrice:get")
	@Log(moduleName = "产品定价管理", description = "获取产品定价", businessType = BusinessType.GET)
	@Operation(summary = "获取产品定价")
	@Parameters({
			@Parameter(in = ParameterIn.QUERY, name = "id", description = "产品定价ID",
					required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/select")
	public ResponseVO<ProductPriceSelectVO> select(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(productPriceService.select(id));
	}
}
