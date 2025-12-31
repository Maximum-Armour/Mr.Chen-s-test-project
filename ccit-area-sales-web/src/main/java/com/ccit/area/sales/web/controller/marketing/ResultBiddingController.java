package com.ccit.area.sales.web.controller.marketing;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingResultPageListDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingSubmitDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesWinBidStatusDTO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingResultPageListVO;
import com.ccit.area.sales.service.marketing.IPriceBiddingService;
import com.ccit.area.sales.service.marketing.IResultBiddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
  * 描述 : “竞价结果管理”前端控制器
  * 创建人 : tb
  * 创建时间 : 2024年09月18日 上午10:56:23
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.web.controller.bidding
  * 类名 : ResultBiddingController
 */
@RestController
@Tag(name = "产品竞价管理")
@RequestMapping(value = "/webapi/bidding/resultBidding")
public class ResultBiddingController {

    /**
     * “竞价结果”服务类
     */
    @Autowired
    private IResultBiddingService resultBiddingService;

    @Autowired
    private IPriceBiddingService priceBiddingService;


    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 上午08:51:23
     * 描述 : 竞价结果列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : selectPageList
     *  Result<Page<SalesBiddingPageListVO>>
     *  @throws
     */
//    @Permissions(value = "resultBidding:select")
    @Log(moduleName = "竞价结果管理", description = "竞价结果列表", businessType = BusinessType.SELECT)
    @Operation(summary = "竞价结果列表", description = "竞价结果列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<List<SalesBiddingResultPageListVO>> selectPageList(@RequestBody SalesBiddingResultPageListDTO param) {
        return ResponseVO.success(resultBiddingService.selectPageList(param));
    }

    /**
     * 竞价中标结果修改
     */
    @Operation(summary = "竞价结果列表", description = "中标状态修改")
    @PostMapping(value = "/updateStatus")
    public ResponseVO<String> updateStatus(@RequestBody ApplyBiddingSubmitDTO entity) {
        return ResponseVO.success(priceBiddingService.updateStatus(entity));
    }


    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 下午03:43:14
     * 描述 : 竞价结果审核
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : submit
     *  ResponseVO<String>
     *  @throws
     */
    @Permissions(value = "resultBidding:submit")
    @Log(moduleName = "产品竞价管理", description = "提交产品竞价", businessType = BusinessType.EDIT)
    @Operation(summary = "提交产品竞价")
    @PostMapping(value = "/submit")
    public ResponseVO<String> submit(@Valid @RequestBody SalesBiddingSubmitDTO entity) {
        return ResponseVO.success(resultBiddingService.submit(entity));
    }

    @Operation(summary = "修改中标状态")
    @PostMapping(value = "/updateWinBidStatus")
    public ResponseVO<String> updateWinBidStatus(@Valid @RequestBody SalesWinBidStatusDTO entity) {
        return ResponseVO.success(resultBiddingService.updateWinBidStatus(entity));
    }



}

