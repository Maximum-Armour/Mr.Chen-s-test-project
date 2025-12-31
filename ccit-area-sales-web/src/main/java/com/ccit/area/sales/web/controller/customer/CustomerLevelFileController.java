package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.TCustomerGradeDTO;
import com.ccit.area.sales.dao.vo.customer.TCustomerGradeVO;
import com.ccit.area.sales.service.customerprofile.CustomerGradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Tag(name = "客户档案")
@RestController
@RequestMapping("/webapi/customer")
public class CustomerLevelFileController {

    @Autowired
    private CustomerGradeService tCustomerGradeService;

    @Operation(summary = "客户级别档案", description = "客户级别档案分页展示和条件查询")
    @RequestMapping("/customerLevelFile/selectPageList")
    public ResponseVO<Page<TCustomerGradeVO>> selectPageList(@RequestBody PageEntity<TCustomerGradeDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tCustomerGradeService.selectPageList(param));
    }

}
