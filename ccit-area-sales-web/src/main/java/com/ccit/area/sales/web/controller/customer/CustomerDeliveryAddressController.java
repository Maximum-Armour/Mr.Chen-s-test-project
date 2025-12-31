package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.TCarTransportAddressesDTO;
import com.ccit.area.sales.dao.dto.customer.TTrainTransportAddressesDTO;
import com.ccit.area.sales.dao.vo.customer.TCarTransportAddresseVO;
import com.ccit.area.sales.dao.vo.customer.TCarTransportAddressesVO;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddresseVO;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddressesVO;
import com.ccit.area.sales.service.customerprofile.CarTransportAddressesService;
import com.ccit.area.sales.service.customerprofile.TrainTransportAddressesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "客户档案")
@RestController
@RequestMapping("/webapi/customer")
public class CustomerDeliveryAddressController {

    @Autowired
    private CarTransportAddressesService tCarTransportAddressesService;

    @Autowired
    private TrainTransportAddressesService tTrainTransportAddressesService;

    @Operation(summary = "客户配送地址存储表",description = "客户汽运配送地址分页展示和条件查询")
    @RequestMapping("/Transport/automobilePage")
    public ResponseVO<Page<TCarTransportAddresseVO>> selectAutomobilePage(@RequestBody PageEntity<TCarTransportAddressesDTO> entity){
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tCarTransportAddressesService.selectPageList(param));
    }

    @Operation(summary = "客户配送地址存储表",description = "客户铁路配送地址分页展示和条件查询")
    @RequestMapping("/railwayTransport/railwayPage")
    public ResponseVO<Page<TTrainTransportAddresseVO>> selectRailwayPage(@RequestBody PageEntity<TTrainTransportAddressesDTO> entity){
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tTrainTransportAddressesService.selectPageList(param));
    }

    @Operation(summary = "客户配送地址存储表", description = "根据Id汽车运输信息")
    @GetMapping (value = "/Transport/automobileRransportMessage/{id}")
    public ResponseVO<TCarTransportAddressesVO> getRegisterAccountMessage(@PathVariable Long id) {
        return ResponseVO.success(tCarTransportAddressesService.getRegisterAccountMessage(id));
    }

    @Operation(summary = "客户配送地址存储表", description = "根据Id铁路运输信息")
    @GetMapping(value = "/Transport/getRailwayRransportMessage/{id}")
    public ResponseVO<TTrainTransportAddressesVO> getRailwayRransportMessage(@PathVariable Long id) {
        return ResponseVO.success(tTrainTransportAddressesService.getRailwayRransportMessage(id));
    }

    @Operation(summary = "企业基本信息",description = "汽运运输信息导出Excel数据")
    @PostMapping("/enterpriseInformation/automobileDeriveExcel")
    public ResponseVO<byte[]> automobileDeriveExcel(){
        return ResponseVO.success(tCarTransportAddressesService.automobileDeriveExcel());
    }

    @Operation(summary = "企业基本信息",description = "铁路运输信息导出Excel数据")
    @PostMapping("/enterpriseInformation/railwayDeriveExcel")
    public ResponseVO<byte[]> railwayDeriveExcel(){
        return ResponseVO.success(tTrainTransportAddressesService.railwayDeriveExcel());
    }

}
