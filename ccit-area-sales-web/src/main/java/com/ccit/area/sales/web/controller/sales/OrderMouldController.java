package com.ccit.area.sales.web.controller.sales;

import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.dao.dto.sales.OrderMouldDTO;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;
import com.ccit.area.sales.service.sales.OrderMouldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
  * 描述 : “订单模板”前端控制器
  * 创建人 : cf
  * 创建时间 : 2024年09月19日 下午15:23:22
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.web.controller.sales
  * 类名 : OrderMouldController
 */
@RestController
@Tag(name = "订单模板")
@RequestMapping(value = "/webapi/sales/ordermould")
public class OrderMouldController {

    @Autowired
    private OrderMouldService orderlineservice;

    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月19日 下午15:49:19
     * 描述 : 修改订单模板信息
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : edit
     *  ResponseVO<String>
     *  @throws
     */
    @Permissions(value = "ordermould:edit")
    @Log(moduleName = "订单模板", description = "订单模板", businessType = BusinessType.EDIT)
    @Operation(summary = "修改订单模板信息")
    @PostMapping(value = "/edit")
    public ResponseVO<String> edit(@Valid @RequestBody OrderMouldDTO entity) {
        return ResponseVO.success(orderlineservice.edit(entity));
    }


    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月19日 下午15:49:19
     * 描述 : 查询订单模板信息
     * 包名 : com.ccit.area.sales.web.controller.sales
     * 方法名 : select
     *  ResponseVO<OrderMouldnoVO>
     *  @throws
     */
    @Permissions(value = "ordermould:select")
    @Log(moduleName = "订单", description = "订单", businessType = BusinessType.EDIT)
    @Operation(summary = "查询订单模板信息")
    @PostMapping(value = "/select")
    public ResponseVO<OrderMouldnoVO> select(@RequestBody OrderMouldDTO entity) {

        return ResponseVO.success(orderlineservice.select(entity));
    }


}
