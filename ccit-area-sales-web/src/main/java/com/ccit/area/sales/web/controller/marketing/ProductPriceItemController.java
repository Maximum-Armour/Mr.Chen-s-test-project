package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;
import com.ccit.area.sales.service.marketing.IProductPriceItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 描述 : “产品定价明细管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:23
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.marketing
 * 类名 : ProductPriceItemController
 */
@RestController
@Tag(name = "产品定价明细管理")
@RequestMapping(value = "/webapi/marketing/productPrice/item")
public class ProductPriceItemController {
		
	/**
	 * “产品定价明细”服务类
	 */
	@Autowired
    private IProductPriceItemService productPriceItemService;
    
    /**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午04:23:23
	 * 描述 : 产品定价明细列表
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  Result<List<ProductPriceItemPageListVO>>
	 *  @throws
	 */
	@Log(moduleName = "产品定价明细管理", description = "产品定价明细列表", businessType = BusinessType.SELECT)
	@Operation(summary = "产品定价明细列表", description = "产品定价明细列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<List<ProductPriceItemPageListVO>> selectPageList(@RequestBody ProductPriceItemPageListDTO entity) {
		return ResponseVO.success(productPriceItemService.selectPageList(entity));
    }

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 下午04:23:23
	 * 描述 : 产品定价明细列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : selectPageList
	 *  Result<Page<ProductPriceItemPageListVO>>
	 *  @throws
	 */
	@Log(moduleName = "产品定价明细管理", description = "产品定价明细列表", businessType = BusinessType.SELECT)
	@Operation(summary = "产品定价明细列表", description = "产品定价明细列表，支持分页查询和高级查询")
	@PostMapping(value = "/list")
	public ResponseVO<Page<ProductPriceItemPageListVO>> selectList(@RequestBody PageEntity<ProductPriceItemPageListDTO> entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(productPriceItemService.selectList(param));
	}

    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年8月16日 下午4:20:26
     * 描述 : 修改产品定价明细
     * 包名 : com.ccit.area.sales.web.controller.marketing
     * 方法名 : edit
     *  ResponseVO<String>  
     *  @throws
     */
	@Permissions(value = "/archives/productPriceItem/edit")
	@Log(moduleName = "产品定价明细管理", description = "修改产品定价明细", businessType = BusinessType.EDIT)
	@Operation(summary = "修改产品定价明细")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody ProductPriceItemEditDTO entity) {
		return ResponseVO.success(productPriceItemService.edit(entity));
	}
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午8:55:22
	 * 描述 : 删除产品定价明细
	 * 包名 : com.ccit.area.sales.web.controller.marketing
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
//	@Permissions(value = "/archives/productPriceItem/delete")
	@Log(moduleName = "产品定价明细管理", description = "删除产品定价明细", businessType = BusinessType.DELETE)
	@Operation(summary = "删除产品定价明细")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody ProductPriceItemDeleteDTO entity) {
		return ResponseVO.success(productPriceItemService.delete(entity));
	}
    
}
