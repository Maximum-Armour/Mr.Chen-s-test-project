package com.ccit.area.sales.web.controller.archives;

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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceEditDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlacePageListDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlacePageListVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “提货地档案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:56:06
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : DeliveryPlaceController
 */
@RestController
@Tag(name = "提货地档案管理")
@RequestMapping(value = "/webapi/archives/deliveryPlace")
public class DeliveryPlaceController {

	/**
	 * “提货地档案”服务类
	 */
	@Autowired
    private IDeliveryPlaceService deliveryPlaceService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:14
	 * 描述 : 提货地档案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<DeliveryPlacePageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "deliveryPlace:select")
	@Log(moduleName = "提货地档案管理", description = "提货地档案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "提货地档案列表", description = "提货地档案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<DeliveryPlacePageListVO>> selectPageList(@RequestBody PageEntity<DeliveryPlacePageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(deliveryPlaceService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:20
	 * 描述 : 新增提货地档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryPlace:add")
	@Log(moduleName = "提货地档案管理", description = "新增提货地档案", businessType = BusinessType.INSERT)
	@Operation(summary = "新增提货地档案")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody DeliveryPlaceAddDTO entity) {
		return ResponseVO.success(deliveryPlaceService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:25
	 * 描述 : 获取提货地档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<DeliveryPlaceDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "deliveryPlace:get")
	@Log(moduleName = "提货地档案管理", description = "获取提货地档案", businessType = BusinessType.GET)
	@Operation(summary = "获取提货地档案")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "提货地档案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<DeliveryPlaceDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(deliveryPlaceService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:34
	 * 描述 : 修改提货地档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryPlace:edit")
	@Log(moduleName = "提货地档案管理", description = "修改提货地档案", businessType = BusinessType.EDIT)
	@Operation(summary = "修改提货地档案")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody DeliveryPlaceEditDTO entity) {
		return ResponseVO.success(deliveryPlaceService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:54:40
	 * 描述 : 删除提货地档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "deliveryPlace:delete")
	@Log(moduleName = "提货地档案管理", description = "删除提货地档案", businessType = BusinessType.DELETE)
	@Operation(summary = "删除提货地档案")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody DeliveryPlaceDeleteDTO entity) {
		return ResponseVO.success(deliveryPlaceService.delete(entity));
	}
	
}
