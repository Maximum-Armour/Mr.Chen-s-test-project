package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import com.ccit.area.sales.service.quartz.QuartzBidScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 *  * 描述 : “产品竞价管理”前端控制器
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月06日 上午10:56:23
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.bidding
 *  * 类名 : SalesBiddingController
 */
@RestController
@Tag(name = "产品竞价管理")
@RequestMapping(value = "/webapi/bidding/salesBidding")
public class SalesBiddingController {

    /**
     * “产品竞价”服务类
     */
    @Autowired
    private ISalesBiddingService salesBiddingService;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 新增产品定价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : add
     * Result<String>
     *
     * @throws
     */
    @Permissions(value = "salesBidding:add")
    @Log(moduleName = "产品竞价管理", description = "新增产品竞价", businessType = BusinessType.INSERT)
    @Operation(summary = "新增产品竞价")
    @PostMapping(value = "/add")
    public ResponseVO<String> add(@Valid @RequestBody SalesBiddingAddDTO entity) {
        return ResponseVO.success(salesBiddingService.add(entity));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午08:51:23
     * 描述 : 产品竞价列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : selectPageList
     * Result<Page<SalesBiddingPageListVO>>
     *
     * @throws
     */
    @Permissions(value = "salesBidding:select")
    @Log(moduleName = "产品竞价管理", description = "产品竞价列表", businessType = BusinessType.SELECT)
    @Operation(summary = "产品竞价列表", description = "产品竞价列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<Page<SalesBiddingPageListVO>> selectPageList(@RequestBody PageEntity<SalesBiddingPageListDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(salesBiddingService.selectPageList(param));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午09:10:23
     * 描述 : 获取产品竞价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : get
     * Result<SalesBiddingDetailsVO>
     *
     * @throws
     */
    @Permissions(value = "salesBidding:get")
    @Log(moduleName = "产品竞价管理", description = "获取产品竞价", businessType = BusinessType.GET)
    @Operation(summary = "获取产品竞价")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "产品竞价ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<SalesBiddingDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
        return ResponseVO.success(salesBiddingService.get(id));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:23:23
     * 描述 : 修改产品竞价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : edit
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "salesBidding:edit")
    @Log(moduleName = "产品竞价管理", description = "修改产品竞价", businessType = BusinessType.EDIT)
    @Operation(summary = "修改产品竞价")
    @PostMapping(value = "/edit")
    public ResponseVO<String> edit(@Valid @RequestBody SalesBiddingEditDTO entity) {
        return ResponseVO.success(salesBiddingService.edit(entity));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:55:22
     * 描述 : 删除产品竞价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : delete
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "salesBidding:delete")
    @Log(moduleName = "产品竞价管理", description = "删除产品竞价", businessType = BusinessType.DELETE)
    @Operation(summary = "删除产品竞价")
    @DeleteMapping(value = "/delete")
    public ResponseVO<String> delete(@Valid @RequestBody SalesBiddingDeleteDTO entity) {
        return ResponseVO.success(salesBiddingService.delete(entity));
    }

    @PostMapping(value = "/updateStatus")
    public ResponseVO<String> updateStatus(@RequestParam String tenderNumber,
                                           @RequestParam String status) {
        return ResponseVO.success(salesBiddingService.updateStatus(tenderNumber, status));
    }

    @Autowired
    private QuartzBidScheduler quartzBidScheduler;
    @PostMapping("/test")
    public void test(){
        quartzBidScheduler.scheduleBidTask(10L);
    }
}
