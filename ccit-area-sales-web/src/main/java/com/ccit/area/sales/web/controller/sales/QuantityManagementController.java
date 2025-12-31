package com.ccit.area.sales.web.controller.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.sales.WeightPageListDTO;
import com.ccit.area.sales.dao.vo.sales.WeightPageListVO;
import com.ccit.area.sales.service.sales.QuantityManagementSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *  * 描述 : “分量管理”前端控制器
 *  * 创建人 : cf
 *  * 创建时间 : 2024年09月09日 下午14:23:22
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.sales
 *  * 类名 : QuantityManagementController
 */
@RestController
@Tag(name = "分量管理")
@RequestMapping(value = "/webapi/sales/quantityManagement")
public class QuantityManagementController {

    @Autowired
    private QuantityManagementSevice quantityManagementSevice;

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月10日 下午13:49:04
     * 描述 : 分量管理列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : selectPageList
     *
     * @throws
     */
    @Permissions(value = "sales:select")
    @Log(moduleName = "分量管理", description = "分量管理列表", businessType = BusinessType.SELECT)
    @Operation(summary = "分量管理列表", description = "分量管理列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<Page<WeightPageListVO>> selectPageList(@RequestBody PageEntity<WeightPageListDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(quantityManagementSevice.selectPageList(param));
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月9日 下午15:27:13
     * 描述 : 获取分量管理信息
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : get
     *
     * @throws
     */
    @Log(moduleName = "分量管理", description = "获取分量信息", businessType = BusinessType.GET)
    @Operation(summary = "获取分量信息")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "分量管理ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<WeightPageListVO> get(@RequestParam(value = "id", required = true) Long id) {
        return ResponseVO.success(quantityManagementSevice.get(id));
    }

}
