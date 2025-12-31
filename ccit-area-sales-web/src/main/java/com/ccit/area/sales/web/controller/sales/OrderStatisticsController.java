package com.ccit.area.sales.web.controller.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsDTO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsItemUpdateDTO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsSaveDTO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsItemVO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsVO;
import com.ccit.area.sales.service.sales.OrderStatisticsItemService;
import com.ccit.area.sales.service.sales.OrderStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "订单管理")
@RestController
@RequestMapping("/webapi/sales/statistics")
public class OrderStatisticsController {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderStatisticsItemService orderStatisticsItemService;

    @Operation(summary = "接单统计", description = "接单统计分页和条件查询")
    @PostMapping("/page")
    public ResponseVO<Page<OrderStatisticsVO>> selectPageList(@RequestBody PageEntity<OrderStatisticsDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(orderStatisticsService.selectPageList(param));
    }

    @Operation(summary = "接单统计", description = "根据id查询接单统计详细信息")
    @PostMapping("/get")
    public ResponseVO<OrderStatisticsVO> get(@RequestParam String takeOrderNo) {
        return ResponseVO.success(orderStatisticsService.get(takeOrderNo));
    }

    @Operation(summary = "接单统计", description = "新增接单统计信息")
    @PostMapping("/saveOrderStatistics")
    public ResponseVO<String> saveOrderStatistics(@RequestBody List<OrderStatisticsSaveDTO> orderStatisticsSaveDTO) {
        return ResponseVO.success(orderStatisticsService.saveOrderStatistics(orderStatisticsSaveDTO));
    }

    @Operation(summary = "接单统计", description = "批量删除接单统计信息")
    @PostMapping("/delete")
    public ResponseVO<String> delete(@RequestParam List<Long> ids) {
        return ResponseVO.success(orderStatisticsService.delete(ids));
    }

    @Operation(summary = "接单统计", description = "根据id查询接单统计明细信息")
    @PostMapping("/getItem")
    public ResponseVO<List<OrderStatisticsItemVO>> getItem(@RequestParam String takeOrderNo) {
        return ResponseVO.success(orderStatisticsItemService.getItem(takeOrderNo));
    }

    @Operation(summary = "接单统计", description = "保存接单明细信息")
    @PostMapping("/updateItem")
    public ResponseVO<String> updateItem(@RequestBody List<OrderStatisticsItemUpdateDTO> entity) {
        return ResponseVO.success(orderStatisticsItemService.updateItem(entity));
    }

    @Operation(summary = "接单统计", description = "接单统计明细导出Excel数据")
    @PostMapping("/deriveExcel")
    public ResponseVO<byte[]> deriveExcel() {
        return ResponseVO.success(orderStatisticsItemService.deriveExcel());
    }

}
