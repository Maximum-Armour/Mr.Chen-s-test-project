package com.ccit.area.sales.web.controller.archives;

import java.util.Map;

import javax.validation.Valid;

import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassPageListVO;
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
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassEditDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassPageListDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassDetailsVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “提货地分类管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:56:53
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : DeliveryPlaceClassController
 */
@RestController
@Tag(name = "提货地分类管理")
@RequestMapping(value = "/webapi/archives/deliveryPlaceClass")
public class DeliveryPlaceClassController {

	/**
	 * “提货地分类”服务类
	 */
	@Autowired
    private IDeliveryPlaceClassService deliveryPlaceClassService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:56
	 * 描述 : 提货地分类列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<DeliveryPlaceClassPageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "deliveryplaceclass:select")
	@Log(moduleName = "提货地分类管理", description = "提货地分类列表", businessType = BusinessType.SELECT)
	@Operation(summary = "提货地分类列表", description = "提货地分类列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<DeliveryPlaceClassPageListVO>> selectPageList(@RequestBody PageEntity<DeliveryPlaceClassPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(deliveryPlaceClassService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:02
	 * 描述 : 新增提货地分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryplaceclass:add")
	@Log(moduleName = "提货地分类管理", description = "新增提货地分类", businessType = BusinessType.INSERT)
	@Operation(summary = "新增提货地分类")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody DeliveryPlaceClassAddDTO entity) {
		return ResponseVO.success(deliveryPlaceClassService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:08
	 * 描述 : 获取提货地分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<DeliveryPlaceClassDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "deliveryplaceclass:get")
	@Log(moduleName = "提货地分类管理", description = "获取提货地分类", businessType = BusinessType.GET)
	@Operation(summary = "获取提货地分类")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "提货地分类ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<DeliveryPlaceClassDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(deliveryPlaceClassService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:14
	 * 描述 : 修改提货地分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryplaceclass:edit")
	@Log(moduleName = "提货地分类管理", description = "修改提货地分类", businessType = BusinessType.EDIT)
	@Operation(summary = "修改提货地分类")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody DeliveryPlaceClassEditDTO entity) {
		return ResponseVO.success(deliveryPlaceClassService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:22
	 * 描述 : 删除提货地分类
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryplaceclass:delete")
	@Log(moduleName = "提货地分类管理", description = "删除提货地分类", businessType = BusinessType.DELETE)
	@Operation(summary = "删除提货地分类")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody DeliveryPlaceClassDeleteDTO entity) {
		return ResponseVO.success(deliveryPlaceClassService.delete(entity));
	}
	
}
