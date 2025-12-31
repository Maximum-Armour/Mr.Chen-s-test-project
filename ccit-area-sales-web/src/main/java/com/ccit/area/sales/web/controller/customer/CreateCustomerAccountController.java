package com.ccit.area.sales.web.controller.customer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.customer.TAccountInfomationDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationLockDTO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationVO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationsVO;
import com.ccit.area.sales.service.customerprofile.AccountInfomationService;
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
public class CreateCustomerAccountController {

    @Autowired
    private AccountInfomationService tAccountInfomationService;

    @Operation(summary = "企业注册账号信息", description = "分页信息")
    @PostMapping(value = "/registeredAccount/page")
    public ResponseVO<Page<TAccountInfomationVO>> selectPageList(@RequestBody PageEntity<TAccountInfomationDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(tAccountInfomationService.selectPageList(param));
    }
    @Permissions(value = "registeredAccount:selectById")
    @Operation(summary = "企业注册账号信息", description = "根据id查询注册账户信息")
    @GetMapping (value = "/registeredAccount/getRegisterAccountMessage/{id}")
    public ResponseVO<TAccountInfomationsVO> getRegisterAccountMessage(@PathVariable Long id) {
        return ResponseVO.success(tAccountInfomationService.getRegisterAccountMessage(id));
    }


    @Operation(summary = "企业注册账号信息", description = "更改企业账号基本信息状态")
    @PostMapping(value = "/registeredAccount/updateStatus")
    public ResponseVO<String> updateAccountInformationStatus(@RequestBody UpdateAccountInformationDTO accountInformation) {
        log.info("传入参数为:{}",accountInformation);
        return ResponseVO.success(tAccountInfomationService.updateStatus(accountInformation));
    }

    @Operation(summary = "企业注册账号信息", description = "更改企业账号基本信息锁定状态")
    @PostMapping(value = "/registeredAccount/updateLockStatus")
    public ResponseVO<String> updateAccountInformationLockStatus(@RequestBody UpdateAccountInformationLockDTO accountInformation) {
        return ResponseVO.success(tAccountInfomationService.updateLockStatus(accountInformation));
    }


}
