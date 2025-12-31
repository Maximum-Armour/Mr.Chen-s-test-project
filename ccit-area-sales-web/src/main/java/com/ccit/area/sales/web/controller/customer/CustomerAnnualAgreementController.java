package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.TAnnualAgreementDTO;
import com.ccit.area.sales.dao.dto.customer.TPdfFileDTO;
import com.ccit.area.sales.dao.vo.customer.TAnnualAgreementVO;
import com.ccit.area.sales.dao.vo.customer.TPdfFileVO;
import com.ccit.area.sales.service.customerprofile.AnnualAgreementService;
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
public class CustomerAnnualAgreementController {

    @Autowired
    private AnnualAgreementService tAnnualAgreementService;

    @Autowired
    private PdfFileService tPdfFileService;

    @Operation(summary = "企业年度协议", description = "客户年度协议分页和条件查询")
    @PostMapping("/customerAnnualAgreement/page")
    public ResponseVO<Page<TAnnualAgreementVO>> selectPageList(@RequestBody PageEntity<TAnnualAgreementDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tAnnualAgreementService.selectPageList(param));
    }
    @Permissions(value = "customerAnnualAgreement:selectAttachment")
    @Operation(summary = "企业年度协议", description = "客户年度协议附件分页查询")
    @PostMapping("/customerAnnualAgreement/selectAttachment")
    public ResponseVO<Page<TPdfFileVO>> selectAttachment(@RequestBody PageEntity<TPdfFileDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tPdfFileService.selectAttachment(param));
    }

}
