package com.ccit.area.sales.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.aop.annotaion.LoginLog;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.dao.dto.system.SystemLoginDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgUpdateStatusDTO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemLoginVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.service.system.ISystemLoginService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *  * 描述 : “登录管理”前端控制器
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月4日 上午10:59:05
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.system
 *  * 类名 : SystemLoginController
 */
@RestController
@Tag(name = "登录管理")
@RequestMapping(value = "/webapi")
@Slf4j
public class SystemLoginController {

    /**
     * “登录”服务类
     */
    @Autowired
    private ISystemLoginService systemLoginService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ISystemOrgService iSystemOrgService;

    @Autowired
    private ISystemUserOrgService iSystemUserOrgService;

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月4日 上午11:15:13
     * 描述 : 登录
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : login
     * ResponseVO<SystemLoginVO>
     *
     * @throws
     */
    @LoginLog
    @Operation(summary = "登录")
    @PostMapping(value = "/login")
    public ResponseVO<SystemLoginVO> login(@Valid @RequestBody SystemLoginDTO entity) {
        return ResponseVO.success(systemLoginService.login(entity));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 下午5:22:03
     * 描述 : 注销
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : logout
     * Result<String>
     *
     * @throws
     */
    @Operation(summary = "注销")
    @GetMapping(value = "/logout")
    public ResponseVO<String> logout() {
        systemLoginService.logout();
        return ResponseVO.success();
    }

    @Operation(summary = "组织列表展示")
    @PostMapping("/getDeptList")
    public ResponseVO<List<SystemOrgVO>> getDeptList() {
        SystemCurrentUserVO systemCurrentUserVO = null;
        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            systemCurrentUserVO = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }

        List<SystemOrgVO> systemOrgVOS = null;
        systemOrgVOS = iSystemUserService.selectOrgName(systemCurrentUserVO.getUserName());
        return ResponseVO.success(systemOrgVOS);
    }

    @Operation(summary = "用户默认组织列表展示状态修改")
    @PostMapping("/updateDeptListStatus")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> updateDeptListStatus(@RequestBody SystemOrgUpdateStatusDTO systemOrgUpdateStatusDTO) {
        String userId = null;
        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            userId = userJson.get("userId").toString();
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }

        String status = null;
        status = iSystemUserService.updateOrgName(systemOrgUpdateStatusDTO.getOrgId().toString(), userId);
        if ("修改失败".equals(status)) {
            throw new BusinessException("修改部门列表展示状态失败！");
        }
        return ResponseVO.success(status);
    }


}
