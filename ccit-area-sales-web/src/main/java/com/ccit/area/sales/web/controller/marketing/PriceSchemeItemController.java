package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;
import com.ccit.area.sales.service.marketing.IPriceSchemeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “定价方案明细管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 上午8:43:56
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.marketing
 * 类名 : PriceSchemeItemController
 */
@RestController
@Tag(name = "定价方案明细管理")
@RequestMapping(value = "/webapi/marketing/priceScheme/item")
public class PriceSchemeItemController {

	/**
	 * “定价方案明细”服务类
	 */
	@Autowired
    private IPriceSchemeItemService priceSchemeItemService;

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 上午8:58:32
	 * 描述 :
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  ResponseVO<List<PriceSchemeItemPageListVO>>
	 *  @throws
	 */
	@Log(moduleName = "定价方案明细管理", description = "定价方案明细列表", businessType = BusinessType.SELECT)
	@Operation(summary = "定价方案明细列表", description = "定价方案明细列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<List<PriceSchemeItemPageListVO>> selectPageList(@RequestBody PriceSchemeItemPageListDTO entity) {
		return ResponseVO.success(priceSchemeItemService.selectPageList(entity));
    }

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 上午8:58:32
	 * 描述 : 定价方案明细列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<PriceSchemeItemPageListVO>>
	 *  @throws
	 */
	@Log(moduleName = "定价方案明细管理", description = "定价方案明细列表", businessType = BusinessType.SELECT)
	@Operation(summary = "定价方案明细列表", description = "定价方案明细列表，支持分页查询和高级查询")
	@PostMapping(value = "/list")
	public ResponseVO<Page<PriceSchemeItemPageListVO>> selectPageList(@RequestBody PageEntity<PriceSchemeItemPageListDTO> entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(priceSchemeItemService.selectItemList(param));
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 上午8:58:38
	 * 描述 : 删除定价方案明细
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : delete
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Log(moduleName = "定价方案明细管理", description = "删除定价方案明细", businessType = BusinessType.DELETE)
	@Operation(summary = "删除定价方案明细")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody PriceSchemeItemDeleteDTO entity) {
		return ResponseVO.success(priceSchemeItemService.delete(entity));
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月4日 上午8:58:38
	 * 描述 : 保存定价方案明细
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : edit
	 *  ResponseVO<String>
	 *  @throws
	 */
	@Log(moduleName = "定价方案明细管理", description = "保存定价方案明细", businessType = BusinessType.DELETE)
	@Operation(summary = "保存定价方案明细")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody PriceSchemeItemEditDTO entity) {
		return ResponseVO.success(priceSchemeItemService.edit(entity));
	}
}
