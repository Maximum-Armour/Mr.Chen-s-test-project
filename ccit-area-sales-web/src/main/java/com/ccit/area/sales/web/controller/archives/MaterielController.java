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
import com.ccit.area.sales.dao.dto.archives.MaterielAddDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielEditDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielPageListDTO;
import com.ccit.area.sales.dao.vo.archives.MaterielDetailsVO;
import com.ccit.area.sales.dao.vo.archives.MaterielPageListVO;
import com.ccit.area.sales.service.archives.IMaterielService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “产品档案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午5:29:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : MaterielController
 */
@RestController
@Tag(name = "产品档案管理")
@RequestMapping(value = "/webapi/archives/materiel")
public class MaterielController {

	/**
	 * “产品档案”服务类
	 */
	@Autowired
    private IMaterielService materielService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:03:27
	 * 描述 : 产品档案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<MaterielPageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "materiel:select")
	@Log(moduleName = "产品档案管理", description = "产品档案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "产品档案列表", description = "产品档案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<MaterielPageListVO>> selectPageList(@RequestBody PageEntity<MaterielPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(materielService.selectPageList(param));
    }
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:03:32
	 * 描述 : 新增产品档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : add
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "materiel:add")
	@Log(moduleName = "产品档案管理", description = "新增产品档案", businessType = BusinessType.INSERT)
	@Operation(summary = "新增产品档案")
	@PostMapping(value = "/add")
	public ResponseVO<String> add(@Valid @RequestBody MaterielAddDTO entity) {
		return ResponseVO.success(materielService.add(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:03:38
	 * 描述 : 获取产品档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : get
	 *  ResponseVO<MaterielDetailsVO>  
	 *  @throws
	 */
	@Permissions(value = "materiel:get")
	@Log(moduleName = "产品档案管理", description = "获取产品档案", businessType = BusinessType.GET)
	@Operation(summary = "获取产品档案")
	@Parameters({
		@Parameter(in = ParameterIn.QUERY, name = "id", description = "产品档案ID", 
				required = true, schema = @Schema(type = "Long"))
	})
	@GetMapping(value = "/get")
	public ResponseVO<MaterielDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
		return ResponseVO.success(materielService.get(id));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:03:45
	 * 描述 : 修改产品档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : edit
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "materiel:edit")
	@Log(moduleName = "产品档案管理", description = "修改产品档案", businessType = BusinessType.EDIT)
	@Operation(summary = "修改产品档案")
	@PostMapping(value = "/edit")
	public ResponseVO<String> edit(@Valid @RequestBody MaterielEditDTO entity) {
		return ResponseVO.success(materielService.edit(entity));
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:03:50
	 * 描述 : 删除产品档案
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : delete
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@Permissions(value = "materiel:delete")
	@Log(moduleName = "产品档案管理", description = "删除产品档案", businessType = BusinessType.DELETE)
	@Operation(summary = "删除产品档案")
	@DeleteMapping(value = "/delete")
	public ResponseVO<String> delete(@Valid @RequestBody MaterielDeleteDTO entity) {
		return ResponseVO.success(materielService.delete(entity));
	}
	
}
