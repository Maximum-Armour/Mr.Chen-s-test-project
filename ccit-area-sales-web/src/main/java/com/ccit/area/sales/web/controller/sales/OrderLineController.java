package com.ccit.area.sales.web.controller.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.sales.OrderLineDTO;
import com.ccit.area.sales.dao.dto.sales.OrderLinesDTO;
import com.ccit.area.sales.dao.dto.sales.OrderSummaryDTO;
import com.ccit.area.sales.dao.dto.sales.OrderUpDTO;
import com.ccit.area.sales.dao.vo.sales.OrderLineListVO;
import com.ccit.area.sales.dao.vo.sales.OrderLineVO;
import com.ccit.area.sales.service.sales.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
  * 描述 : “订单”前端控制器
  * 创建人 : cf
  * 创建时间 : 2024年09月09日 下午14:23:22
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.web.controller.sales
  * 类名 : OrderLineController
 */
@RestController
@Tag(name = "订单")
@RequestMapping(value = "/webapi/sales/orderline")
@Slf4j
public class OrderLineController {


    @Autowired
    private OrderService OrderService;
    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午8:46:06
     * 描述 : 客户意向详情
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : selectOrder
     *  ResponseVO<List<OrderLineVO>>
     *  @throws
     */
    @Permissions(value = "orderline:select")
    @Log(moduleName = "分量管理", description = "订单列表", businessType = BusinessType.SELECT)
    @Operation(summary = "订单列表", description = "订单列表")
    @PostMapping(value = "/selectOrder")
    public ResponseVO<List<OrderLineVO>> selectOrder(@RequestBody OrderLineListVO entity) {

        return ResponseVO.success(OrderService.selectOrder(entity));
    }


    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:49:19
     * 描述 : 修改订单
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : edit
     *  ResponseVO<String>
     *  @throws
     */
    @Permissions(value = "orderline:edit")
    @Log(moduleName = "订单", description = "订单", businessType = BusinessType.EDIT)
    @Operation(summary = "修改订单信息")
    @PostMapping(value = "/edit")
    public ResponseVO<String> edit(@Valid @RequestBody OrderUpDTO entity) {
        return ResponseVO.success(OrderService.edit(entity));
    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:49:19
     * 描述 : 订单汇总
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : summary
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderline:summary")
    @Log(moduleName = "订单", description = "订单", businessType = BusinessType.EDIT)
    @Operation(summary = "汇总信息")
    @PostMapping(value = "/summary")
    public ResponseVO<BigDecimal[]> summary(@Valid @RequestBody OrderSummaryDTO entity) {

        return ResponseVO.success(OrderService.summary(entity));
    }



    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月26日 下午15:49:19
     * 描述 : 拒接订单
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : refuse
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderline:refuse")
    @Log(moduleName = "拒接订单", description = "拒接订单", businessType = BusinessType.EDIT)
    @Operation(summary = "拒接订单")
    @PostMapping(value = "/refuse")
    public ResponseVO<String> refuse(@Valid @RequestBody OrderSummaryDTO entity) {

        return ResponseVO.success(OrderService.refuse(entity));


    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月27日 上午10:16:19
     * 描述 : 订单行提交
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : submit
     * ResponseVO<String>
     *
     * @throws
     */
   @Permissions(value = "orderline:submit")
    @Log(moduleName = "提交订单", description = "提交订单", businessType = BusinessType.EDIT)
    @Operation(summary = "提交订单")
    @PostMapping(value = "/submit")
    public ResponseVO<String> submit(@Valid @RequestBody List<OrderLinesDTO> entity) {

        return ResponseVO.success(OrderService.submit(entity));

    }



    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午8:46:06
     * 描述 : 客户意向详情
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : queryReview
     *  ResponseVO<List<OrderLineVO>>
     *  @throws
     */
    @Permissions(value = "orderline:queryReview")
    @Log(moduleName = "查询订单待审核", description = "查询订单待审核", businessType = BusinessType.SELECT)
    @Operation(summary = "查询订单待审核", description = "查询订单待审核")
    @PostMapping(value = "/queryReview")
    public ResponseVO<Page<OrderLineVO>> queryReview(@RequestBody PageEntity<OrderLineDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(OrderService.queryReview(param));
    }



    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月30日 上午10:16:19
     * 描述 : 订单行审核通过或者驳回
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : approvedOrReject
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderline:approvedorreject")
    @Log(moduleName = "订单行审核通过或者驳回", description = "订单行审核通过或者驳回", businessType = BusinessType.EDIT)
    @Operation(summary = "订单行审核通过或者驳回")
    @PostMapping(value = "/approvedorreject")
    public ResponseVO<String> approvedOrReject(@Valid @RequestBody OrderLineDTO entity) {

        return ResponseVO.success(OrderService.approvedorreject(entity));

    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月30日 上午10:59:19
     * 描述 : 订单行审核全部
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : reviewAll
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderline:reviewAll")
    @Log(moduleName = "订单行审核全部", description = "订单行审核全部", businessType = BusinessType.EDIT)
    @Operation(summary = "订单行审核全部")
    @PostMapping(value = "/reviewAll")
    public ResponseVO<String> reviewAll(@Valid @RequestBody OrderSummaryDTO entity) {

        return ResponseVO.success(OrderService.reviewAll(entity));

    }


    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午8:46:06
     * 描述 : 订单明细
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : orderDetails
     *  ResponseVO<List<OrderLineVO>>
     *  @throws
     */
    @Permissions(value = "orderline:orderDetails")
    @Log(moduleName = "订单管理", description = "订单列表", businessType = BusinessType.SELECT)
    @Operation(summary = "订单明细", description = "订单行明细")
    @PostMapping(value = "/orderDetails")
    public ResponseVO<Page<OrderLineVO>> orderDetails (@RequestBody PageEntity<OrderLineDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(OrderService.orderDetails(param));
    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:49:19
     * 描述 : 订单明细汇总
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : detailedSummary
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "orderline:detailedSummary")
    @Log(moduleName = "订单", description = "订单", businessType = BusinessType.EDIT)
    @Operation(summary = "订单明细汇总")
    @PostMapping(value = "/detailedSummary")
    public ResponseVO<BigDecimal> detailedSummary(@Valid @RequestBody OrderSummaryDTO entity) {

        return ResponseVO.success(OrderService.detailedSummary(entity));
    }


    /**
     *
     * 创建人 : yn
     * 创建时间 : 2024年8月21日 上午10:09:07
     * 描述 : 获取订单信息
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : get
     *  ResponseVO<OrderLineVO>
     *  @throws
     */
    @Log(moduleName = "订单", description = "订单", businessType = BusinessType.GET)
    @Operation(summary = "获取订单")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "定价方案ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<OrderLineVO> get(@RequestParam(value = "id", required = true) Long id) {
        return ResponseVO.success(OrderService.get(id));
    }



    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午8:46:06
     * 描述 : 订单详情
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : orderDetail
     *  ResponseVO<OrderLineVO>
     *  @throws
     */
    @Log(moduleName = "订单管理", description = "订单列表", businessType = BusinessType.SELECT)
    @Operation(summary = "订单详情", description = "订单详情")
    @PostMapping(value = "/orderDetail")
    public ResponseVO<OrderLineVO> orderDetail (@RequestBody OrderLineDTO entity) {

        return ResponseVO.success(OrderService.orderDetail(entity));
    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午8:46:06
     * 描述 : 订单详情
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : orderDetail
     * ResponseVO<OrderLineVO>
     *
     * @return
     * @throws
     */
//    @Permissions(value = "orderline:downContract")
    @Log(moduleName = "订单管理", description = "订单列表", businessType = BusinessType.SELECT)
    @Operation(summary = "下载签章合同", description = "下载签章合同")
    @PostMapping(value = "/downloadSignature", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadSignature(@RequestParam String orderLineNo) {
            byte[] bytes = OrderService.downloadSignature(orderLineNo);
            HttpHeaders headers = new HttpHeaders();
            String filename = "sales_confirmation_" + orderLineNo + ".pdf";
            // 设置 Content-Disposition
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 修改订单审批状态
     *
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResponseVO<String> updateStatus(@RequestParam List<Long> ids, @RequestParam String status) {
        return ResponseVO.success(OrderService.updateStatus(ids, status));
    }

//    @Permissions(value = "orderline:queryReview")
    @Log(moduleName = "查询订单审核完成", description = "查询订单审核完成", businessType = BusinessType.SELECT)
    @Operation(summary = "查询订单审核完成", description = "查询订单审核完成")
    @PostMapping(value = "/querySHWCReview")
    public ResponseVO<Page<OrderLineVO>> querySHWCReview(@RequestBody PageEntity<OrderLineDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(OrderService.querySHWCReview(param));
    }

}






