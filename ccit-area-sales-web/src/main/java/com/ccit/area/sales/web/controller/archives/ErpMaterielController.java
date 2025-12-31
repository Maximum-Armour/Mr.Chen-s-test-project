package com.ccit.area.sales.web.controller.archives;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ccit.area.sales.dao.dto.archives.ErpMaterielPageListDTO;
import com.ccit.area.sales.dao.vo.archives.ErpMaterielPageListVO;
import com.ccit.area.sales.service.archives.IErpMaterielService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * 描述 : “ERP产品档案管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午5:06:40
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.archives
 * 类名 : ErpMaterielController
 */
@RestController
@Tag(name = "ERP产品档案管理")
@RequestMapping(value = "/webapi/archives/erp/materiel")
public class ErpMaterielController {

	/**
	 * “ERP产品档案”服务类
	 */
	@Autowired
    private IErpMaterielService erpMaterielService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月29日 下午5:08:12
	 * 描述 : ERP产品档案列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.archives
	 * 方法名 : selectPageList
	 *  ResponseVO<Page<ErpMaterielPageListVO>>  
	 *  @throws
	 */
	@Log(moduleName = "ERP产品档案管理", description = "ERP产品档案列表", businessType = BusinessType.SELECT)
	@Operation(summary = "ERP产品档案列表", description = "ERP产品档案列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<ErpMaterielPageListVO>> selectPageList(@RequestBody PageEntity<ErpMaterielPageListDTO> entity) {
    	Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(erpMaterielService.selectPageList(param));
    }
	
}
