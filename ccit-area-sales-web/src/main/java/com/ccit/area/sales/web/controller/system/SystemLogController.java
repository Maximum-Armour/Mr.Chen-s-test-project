package com.ccit.area.sales.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemLogPageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemLogPageListVO;
import com.ccit.area.sales.service.system.ISystemLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 
 * 描述 : “日志管理”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年10月30日 下午3:36:26
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.system
 * 类名 : SystemLogController
 */
@RestController
@Tag(name = "日志管理")
@RequestMapping(value = "/webapi/system/log")
public class SystemLogController {

	/**
	 * “操作日志记录”服务类
	 */
	@Autowired
	private ISystemLogService systemLogService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午3:38:51
	 * 描述 : 日志列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.web.controller.system
	 * 方法名 : selectTreeList
	 *  ResponseVO<Page<SystemLogPageListVO>>  
	 *  @throws
	 */
	@Permissions(value = "log:select")
	@Operation(summary = "日志列表", description = "日志列表，支持分页查询和高级查询")
	@PostMapping(value = "/page/list")
    public ResponseVO<Page<SystemLogPageListVO>> selectTreeList(@RequestBody PageEntity<SystemLogPageListDTO> entity) {
		Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
		param.put("page", entity.getPage());
		param.put("size", entity.getSize());
		return ResponseVO.success(systemLogService.selectPageList(param));
    }
	
}
