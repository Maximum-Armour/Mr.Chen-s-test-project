package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.TCustomerQualificationDTO;
import com.ccit.area.sales.dao.dto.customer.TPdfFileDTO;
import com.ccit.area.sales.dao.vo.customer.TCustomerQualificationVO;
import com.ccit.area.sales.dao.vo.customer.TPdfFileVO;
import com.ccit.area.sales.service.customerprofile.CustomerQualificationService;
import com.ccit.area.sales.service.customerprofile.PdfFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Tag(name = "客户档案")
@RestController
@RequestMapping("/webapi/customer")
public class CustomerQualificationController {

    @Autowired
    private CustomerQualificationService tCustomerQualificationService;

    @Autowired
    private PdfFileService tPdfFileService;

    @Operation(summary = "客户资质", description = "客户资质分页信息和的查询")
    @PostMapping(value = "/customerQualification/page")
    public ResponseVO<Page<TCustomerQualificationVO>> selectPageList(@RequestBody PageEntity<TCustomerQualificationDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tCustomerQualificationService.selectPageList(param));
    }

    @Operation(summary = "客户资质", description = "客户资质附件分页信息和的查询")
    @PostMapping(value = "/customerQualification/selectQualification")
    public ResponseVO<Page<TPdfFileVO>> selectQualification(@RequestBody PageEntity<TPdfFileDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tPdfFileService.selectQualification(param));
    }
}
