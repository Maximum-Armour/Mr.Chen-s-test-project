package com.ccit.area.sales.web.controller.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingPageListVO;
import com.ccit.area.sales.service.marketing.IApplyBiddingService;
import com.ccit.area.sales.service.marketing.IPriceBiddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

/**
 *  * 描述 : “竞价审核管理”前端控制器
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月12日 下午3:56:23
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.bidding
 *  * 类名 : ApplyBiddingController
 */
@RestController
@Tag(name = "竞价审核管理")
@RequestMapping(value = "/webapi/bidding/applyBidding")
@Slf4j
public class ApplyBiddingController {

    /**
     * “竞价审核”服务类
     */
    @Autowired
    private IApplyBiddingService applyBiddingService;

    @Autowired
    private IPriceBiddingService priceBiddingService;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午3:51:23
     * 描述 : 产品竞价列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : selectPageList
     * Result<Page<ApplyBiddingPageListVO>>
     *
     * @throws
     */
    @Permissions(value = "applyBidding:select")
    @Log(moduleName = "竞价审核管理", description = "竞价审核列表", businessType = BusinessType.SELECT)
    @Operation(summary = "竞价审核列表", description = "竞价审核列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<Page<ApplyBiddingPageListVO>> selectPageList(@RequestBody PageEntity<ApplyBiddingPageListDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(applyBiddingService.selectPageList(param));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午4:43:14
     * 描述 : 提交产品竞价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : submit
     * ResponseVO<String>
     *
     * @throws
     */
//    @Permissions(value = "applyBidding:submit")
    @Log(moduleName = "竞价审核管理", description = "竞价审核", businessType = BusinessType.EDIT)
    @Operation(summary = "竞价审核")
    @PostMapping(value = "/submit")
    public ResponseVO<String> submit(@Valid @RequestBody ApplyBiddingSubmitDTO entity) {
        return ResponseVO.success(applyBiddingService.submit(entity));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 下午03:23:23
     * 描述 : 结果编辑
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : edit
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderBidding:edit")
    @Log(moduleName = "结果编辑", description = "结果编辑", businessType = BusinessType.EDIT)
    @Operation(summary = "结果编辑")
    @PostMapping(value = "/edit")
    public ResponseVO<String> edit(@Valid @RequestBody ApplyBiddingEditDTO entity) {
        return ResponseVO.success(applyBiddingService.edit(entity));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月19日 下午03:23:23
     * 描述 : 报名新增
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : add
     * ResponseVO<String>
     *
     * @throws
     */
    @Log(moduleName = "报名新增", description = "报名新增", businessType = BusinessType.INSERT)
    @Operation(summary = "报名新增")
    @PostMapping(value = "/add")
    public ResponseVO<String> add(@RequestBody ApplyBiddingAddDTO entity) {
        return ResponseVO.success(applyBiddingService.add(entity));
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月20日 下午03:23:23
     * 描述 : 客户报价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : quote
     * ResponseVO<String>
     *
     * @throws
     */
    @Log(moduleName = "客户报价", description = "客户报价", businessType = BusinessType.INSERT)
    @Operation(summary = "客户报价")
    @PostMapping(value = "/quote")
    public ResponseVO<String> quote(@RequestBody ApplyBiddingQuoteDTO entity) {
        return ResponseVO.success(priceBiddingService.quote(entity));
    }

    /**
     * 更改审批
     *
     * @param tenderNumber
     * @return
     */
    @PostMapping(value = "/updateApprovalStatus")
    public ResponseVO<String> updateApprovalStatus(@RequestParam Long id, @RequestParam String tenderNumber,
                                                   @RequestParam String status) {
        return ResponseVO.success(applyBiddingService.updateApprovalStatus(id,tenderNumber,status));
    }

    /**
     * 获取当前时间的毫秒级时间戳
     *
     * @return ResponseVO<Long> 包含当前时间毫秒级时间戳的响应对象
     */
    @GetMapping("/getTime")
    public ResponseVO<Long> getTime() {
        // 使用 Instant 获取当前时间的毫秒级时间戳
        Instant instant = Instant.now();
        // 返回包含当前时间毫秒级时间戳的响应对象
        return ResponseVO.success(instant.toEpochMilli());
    }
}
