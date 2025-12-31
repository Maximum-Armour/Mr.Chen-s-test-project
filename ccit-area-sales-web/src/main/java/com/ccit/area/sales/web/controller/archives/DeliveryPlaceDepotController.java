package com.ccit.area.sales.web.controller.archives;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotAddDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotPageListDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotPageListVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceDepotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “提货地仓库管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午1:58:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : DeliveryPlaceDepotController
 */
@RestController
@Tag(name = "提货地仓库管理")
@RequestMapping(value = "/webapi/archives/deliveryPlace/depot")
public class DeliveryPlaceDepotController {

	/**
	 * “提货地仓库中间表”服务类
	 */
	@Autowired
    private IDeliveryPlaceDepotService deliveryPlaceDepotService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:06:22
	 * 描述 : 提货地仓库列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<DeliveryPlaceDepotPageListVO>>  
	 *  @throws
	 */
	@Log(moduleName = "提货地仓库管理", description = "提货地仓库列表", businessType = BusinessType.SELECT)
	@Operation(summary = "提货地仓库列表", description = "提货地仓库列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
	public ResponseVO<Page<DeliveryPlaceDepotPageListVO>> selectPageList(@RequestBody PageEntity<DeliveryPlaceDepotPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(deliveryPlaceDepotService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:10:29
	 * 描述 : 删除提货地仓库
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Log(moduleName = "提货地仓库管理", description = "删除提货地仓库", businessType = BusinessType.DELETE)
	@Operation(summary = "删除提货地仓库")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody DeliveryPlaceDepotDeleteDTO entity) {
		return ResponseVO.success(deliveryPlaceDepotService.delete(entity));
	}



	/**
	 *
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:10:29
	 * 描述 : 新增提货地仓库
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Log(moduleName = "提货地仓库管理", description = "新增提货地仓库", businessType = BusinessType.DELETE)
	@Operation(summary = "修改提货地仓库")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody DeliveryPlaceDepotAddDTO entity) {
		return ResponseVO.success(deliveryPlaceDepotService.edit(entity));
	}



	/**
	 *
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:10:29
	 * 描述 : 新增提货地仓库
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Log(moduleName = "提货地仓库管理", description = "查询提货地仓库", businessType = BusinessType.DELETE)
	@Operation(summary = "查询提货地仓库")
	@PostMapping(value = "/selectdepotNo")
	public ResponseVO<List<String>> selectdepotNo(@Valid @RequestBody DeliveryPlaceDepotAddDTO entity) {
		return ResponseVO.success(deliveryPlaceDepotService.selectdepotNo(entity));
	}
}
