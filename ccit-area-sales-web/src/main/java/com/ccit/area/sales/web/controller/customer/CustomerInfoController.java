package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.CustomerDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateCustomerDTO;
import com.ccit.area.sales.dao.vo.customer.CustomerVO;
import com.ccit.area.sales.service.customerprofile.CustomerInfoService;
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
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;

    @Operation(summary = "企业基本信息", description = "企业基本信息分页和条件查询")
    @PostMapping("/customerInfo/page")
    public ResponseVO<Page<CustomerVO>> selectPageList(@RequestBody PageEntity<CustomerDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(customerInfoService.selectPageList(param));
    }

    //    @Permissions(value = "enterpriseInformation:selectById")
    @Operation(summary = "企业基本信息", description = "根据id查询企业基本信息")
    @GetMapping("/customerInfo/getById/{id}")
    public ResponseVO<CustomerVO> getBasicInformation(@PathVariable Long id) {
        return ResponseVO.success(customerInfoService.getBasicInformation(id));
    }

    @Operation(summary = "企业基本信息", description = "修改企业基本信息状态")
    @PostMapping("/customerInfo/updateStatus")
    public ResponseVO<String> updateStatus(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return ResponseVO.success(customerInfoService.updateStatus(updateCustomerDTO));
    }

    @Operation(summary = "企业基本信息修改", description = "修改企业基本信息")
    @PostMapping("/customerInfo/updateById")
    public ResponseVO<String> updateById(@RequestBody CustomerDTO customerDTO) {
        return ResponseVO.success(customerInfoService.updateCustomerInfo(customerDTO));
    }

    @Operation(summary = "企业基本信息", description = "企业基本信息导出Excel数据")
    @PostMapping("/customerInfo/deriveExcel")
    public ResponseVO<byte[]> deriveExcel() {
        return ResponseVO.success(customerInfoService.deriveExcel());
    }

//    @Operation(summary = "企业基本信息", description = "企业基本信息分页和条件查询")
//    @PostMapping("/enterpriseInformation/companyInfoUpdatePage")
//    public ResponseVO<Page<TCompanyInfoUpdateVO>> selectCompanyInfoUpdatePageList(@RequestBody PageEntity<TCompanyInfoUpdateDTO> entity) {
//        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
//        param.put("page", entity.getPage());
//        param.put("size", entity.getSize());
//        return ResponseVO.success(customerInfoService.selectCompanyInfoUpdatePageList(param));
//    }
}
