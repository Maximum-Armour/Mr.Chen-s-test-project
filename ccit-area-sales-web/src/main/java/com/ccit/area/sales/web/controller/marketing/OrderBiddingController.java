package com.ccit.area.sales.web.controller.marketing;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingSubmitVO;
import com.ccit.area.sales.service.marketing.IOrderBiddingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
  * 描述 : “竞价订单管理”前端控制器
  * 创建人 : tb
  * 创建时间 : 2024年09月24日 上午10:56:23
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.web.controller.bidding
  * 类名 : OrderBiddingController
 */
@RestController
@Tag(name = "竞价订单管理")
@RequestMapping(value = "/webapi/bidding/orderBidding")
public class OrderBiddingController {

    /**
     * “竞价订单”服务类
     */
    @Autowired
    private IOrderBiddingService orderBiddingService;

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月24日 上午09:10:23
     * 描述 : 获取竞价订单
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : get
     *  Result<OrderBiddingDetailsVO>
     *  @throws
     */
    @Permissions(value = "orderBidding:get")
    @Log(moduleName = "竞价订单管理", description = "获取竞价订单", businessType = BusinessType.GET)
    @Operation(summary = "获取竞价订单")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "产品竞价ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<OrderBiddingDetailsVO> get(@RequestParam String tenderNumber) {
        return ResponseVO.success(orderBiddingService.get(tenderNumber));
    }

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月25日 下午03:23:23
     * 描述 : 修改结果确认
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : edit
     *  ResponseVO<String>
     *  @throws
     */
    @Permissions(value = "orderBidding:edit")
    @Log(moduleName = "结果确认", description = "修改结果确认", businessType = BusinessType.EDIT)
    @Operation(summary = "修改结果确认")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "产品竞价ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/edit")
    public ResponseVO<String> edit(@RequestParam Long id) {
        return ResponseVO.success(orderBiddingService.edit(id));
    }

    @Operation(description = "竞价订单管理",summary = "提交竞价结果审批")
    @GetMapping(value = "/submit")
    public ResponseVO<OrderBiddingSubmitVO> submit(@RequestParam Long id) {
        return ResponseVO.success(orderBiddingService.submit(id));
    }

    @GetMapping("/test")
    public void createOrder(@RequestParam Long id){
        orderBiddingService.createOrder(id);
    }
}
