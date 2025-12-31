package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemePageListVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeSelectVO;
import com.ccit.area.sales.service.marketing.IPriceSchemeService;
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
 * 描述 : “定价方案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年8月9日 上午9:36:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.marketing
 * 类名 : PriceSchemeController
 */
@RestController
@Tag(name = "定价方案管理")
@RequestMapping(value = "/webapi/marketing/priceScheme")
public class PriceSchemeController {

	/**
	 * “定价方案”服务类
	 */
	@Autowired
    private IPriceSchemeService priceSchemeService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:39:58
	 * 描述 : 定价方案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<PriceSchemePageListVO>>  
	 *  @throws
	 */
//	@Permissions(value = "priceScheme:select")
	@Log(moduleName = "定价方案管理", description = "定价方案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "定价方案列表", description = "定价方案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<PriceSchemePageListVO>> selectPageList(@RequestBody PageEntity<PriceSchemePageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(priceSchemeService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:06
	 * 描述 : 新增定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "priceScheme:add")
	@Log(moduleName = "定价方案管理", description = "新增定价方案", businessType = BusinessType.INSERT)
	@Operation(summary = "新增定价方案")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody PriceSchemeAddDTO entity) {
		return ResponseVO.success(priceSchemeService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:13
	 * 描述 : 获取定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : get
	 *  ResponseVO<PriceSchemeDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "priceScheme:get")
	@Log(moduleName = "定价方案管理", description = "获取定价方案", businessType = BusinessType.GET)
	@Operation(summary = "获取定价方案")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "定价方案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<PriceSchemeDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(priceSchemeService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:20
	 * 描述 : 修改定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "priceScheme:edit")
	@Log(moduleName = "定价方案管理", description = "修改定价方案", businessType = BusinessType.EDIT)
	@Operation(summary = "修改定价方案")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody PriceSchemeEditDTO entity) {
		return ResponseVO.success(priceSchemeService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:26
	 * 描述 : 删除定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "priceScheme:delete")
	@Log(moduleName = "定价方案管理", description = "删除定价方案", businessType = BusinessType.DELETE)
	@Operation(summary = "删除定价方案")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody PriceSchemeDeleteDTO entity) {
		return ResponseVO.success(priceSchemeService.delete(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午2:40:20
	 * 描述 : 提交定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : submit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "priceScheme:submit")
	@Log(moduleName = "定价方案管理", description = "提交定价方案", businessType = BusinessType.EDIT)
	@Operation(summary = "提交定价方案")
	@PostMapping(value = "/submit")
	public ResponseVO<String> submit(@Valid @RequestBody PriceSchemeSubmitDTO entity) {
		return ResponseVO.success(priceSchemeService.submit(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午2:40:20
	 * 描述 : 定价方案审核
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : examine
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Permissions(value = "priceScheme:examine")
	@Log(moduleName = "定价方案管理", description = "定价方案审核", businessType = BusinessType.EDIT)
	@Operation(summary = "定价方案审核")
	@PostMapping(value = "/examine")
	public ResponseVO<String> examine(@Valid @RequestBody PriceSchemeSubmitDTO entity) {
		return ResponseVO.success(priceSchemeService.examine(entity));
	}

	/**
	 *
	 * 创建人 : yn
	 * 创建时间 : 2024年8月9日 下午2:40:13
	 * 描述 : 获取定价方案
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : get
	 *  ResponseVO<PriceSchemeDetailsVO>
	 *  @throws
	 */
	@Permissions(value = "priceScheme:get")
	@Log(moduleName = "定价方案管理", description = "获取定价方案", businessType = BusinessType.GET)
	@Operation(summary = "获取定价方案")
	@Parameters({
			@Parameter(in = ParameterIn.QUERY, name = "id", description = "定价方案ID",
					required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/select")
	public ResponseVO<PriceSchemeSelectVO> select(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(priceSchemeService.select(id));
	}
}
