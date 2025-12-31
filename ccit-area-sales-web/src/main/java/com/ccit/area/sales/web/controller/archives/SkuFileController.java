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
import com.ccit.area.sales.dao.dto.archives.SkuFileAddDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileEditDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFilePageListDTO;
import com.ccit.area.sales.dao.vo.archives.SkuFileDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SkuFilePageListVO;
import com.ccit.area.sales.service.archives.ISkuFileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “牌号档案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月17日 下午1:45:09
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : SkuFileController
 */
@RestController
@Tag(name = "牌号档案管理")
@RequestMapping(value = "/webapi/archives/skuFile")
public class SkuFileController {
	
	/**
	 * “牌号档案”服务类
	 */
	@Autowired
    private ISkuFileService skuFileService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:49:04
	 * 描述 : 牌号档案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<SkuFilePageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "skuFile:select")
	@Log(moduleName = "牌号档案管理", description = "牌号档案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "牌号档案列表", description = "牌号档案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<SkuFilePageListVO>> selectPageList(@RequestBody PageEntity<SkuFilePageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(skuFileService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:49:09
	 * 描述 : 新增牌号档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "skuFile:add")
	@Log(moduleName = "牌号档案管理", description = "新增牌号档案", businessType = BusinessType.INSERT)
	@Operation(summary = "新增牌号档案")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody SkuFileAddDTO entity) {
		return ResponseVO.success(skuFileService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:49:14
	 * 描述 : 获取牌号档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<SkuFileDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "skuFile:get")
	@Log(moduleName = "牌号档案管理", description = "获取牌号档案", businessType = BusinessType.GET)
	@Operation(summary = "获取牌号档案")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "牌号档案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<SkuFileDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(skuFileService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:49:19
	 * 描述 : 修改牌号档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "skuFile:edit")
	@Log(moduleName = "牌号档案管理", description = "修改牌号档案", businessType = BusinessType.EDIT)
	@Operation(summary = "修改牌号档案")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody SkuFileEditDTO entity) {
		return ResponseVO.success(skuFileService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:49:24
	 * 描述 : 删除牌号档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "skuFile:delete")
	@Log(moduleName = "牌号档案管理", description = "删除牌号档案", businessType = BusinessType.DELETE)
	@Operation(summary = "删除牌号档案")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody SkuFileDeleteDTO entity) {
		return ResponseVO.success(skuFileService.delete(entity));
	}
	
}
