package com.ccit.area.sales.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.SystemLoginLogPageListDTO;
import com.ccit.area.sales.dao.vo.system.SystemLoginLogPageListVO;
import com.ccit.area.sales.service.system.ISystemLoginLogService;
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
  * 描述 : “登录日志管理”前端控制器
  * 创建人 : tb
  * 创建时间 : 2024年11月27日 下午3:36:26
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.web.controller.system
  * 类名 : SystemLoginLogController
 */
@RestController
@Tag(name = "登录日志管理")
@RequestMapping(value = "/webapi/system/loginlog")
public class SystemLoginLogController {

    @Autowired
    private ISystemLoginLogService systemLoginLogService;

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月28日 下午3:38:51
     * 描述 : 登录日志列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : selectTreeList
     *  ResponseVO<Page<SystemLoginLogPageListVO>>
     *  @throws
     */
    @Permissions(value = "loginlog:select")
    @Operation(summary = "登录日志列表", description = "登录日志列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<Page<SystemLoginLogPageListVO>> selectTreeList(@RequestBody PageEntity<SystemLoginLogPageListDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(systemLoginLogService.selectPageList(param));
    }
}
