package com.ccit.area.sales.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.common.aop.annotaion.Log;
import com.ccit.area.sales.common.aop.annotaion.Permissions;
import com.ccit.area.sales.common.aop.enums.BusinessType;
import com.ccit.area.sales.common.entity.PageEntity;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.utils.MapUtil;
import com.ccit.area.sales.dao.dto.system.*;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemUserPageListVO;
import com.ccit.area.sales.service.system.ISystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 *  * 描述 : “用户管理”前端控制器
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月4日 上午10:57:33
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.web.controller.system
 *  * 类名 : SystemUserController
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping(value = "/webapi/system/user")
public class SystemUserController {

    /**
     * “用户”服务类
     */
    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 上午11:11:42
     * 描述 : 用户列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : selectPageList
     * ResponseVO<Page<SystemUserPageListVO>>
     *
     * @throws
     */
    @Permissions(value = "user:select")
    @Log(moduleName = "用户管理", description = "用户列表", businessType = BusinessType.SELECT)
    @Operation(summary = "用户列表", description = "用户列表，支持分页查询和高级查询")
    @PostMapping(value = "/page/list")
    public ResponseVO<Page<SystemUserPageListVO>> selectPageList(@RequestBody PageEntity<SystemUserPageListDTO> entity) {
        Map<String, Object> param = MapUtil.objectToMap(entity.getParam());
        param.put("page", entity.getPage());
        param.put("size", entity.getSize());
        return ResponseVO.success(systemUserService.selectPageList(param));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 上午11:11:47
     * 描述 : 新增用户
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : add
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "user:add")
    @Log(moduleName = "用户管理", description = "新增用户", businessType = BusinessType.INSERT)
    @Operation(summary = "新增用户")
    @PostMapping(value = "/add")
    public ResponseVO<String> add(@Valid @RequestBody SystemUserAddDTO entity) {
        return ResponseVO.success(systemUserService.add(entity));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 上午11:11:51
     * 描述 : 获取用户
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : get
     * ResponseVO<SystemUserDetailsVO>
     *
     * @throws
     */
    @Permissions(value = "user:get")
    @Log(moduleName = "用户管理", description = "获取用户", businessType = BusinessType.GET)
    @Operation(summary = "获取用户")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, name = "id", description = "用户ID",
                    required = true, schema = @Schema(type = "Long"))
    })
    @GetMapping(value = "/get")
    public ResponseVO<SystemUserDetailsVO> get(@RequestParam(value = "id", required = true) Long id) {
        return ResponseVO.success(systemUserService.get(id));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 上午11:11:56
     * 描述 : 修改用户
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : edit
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "user:edit")
    @Log(moduleName = "用户管理", description = "修改用户", businessType = BusinessType.EDIT)
    @Operation(summary = "修改用户")
    @PostMapping(value = "/edit")
    public ResponseVO<String> edit(@Valid @RequestBody SystemUserEditDTO entity) {
        return ResponseVO.success(systemUserService.edit(entity));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 上午11:12:00
     * 描述 : 删除用户
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : delete
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "user:delete")
    @Log(moduleName = "用户管理", description = "删除用户", businessType = BusinessType.DELETE)
    @Operation(summary = "删除用户")
    @DeleteMapping(value = "/delete")
    public ResponseVO<String> delete(@Valid @RequestBody SystemUserDeleteDTO entity) {
        return ResponseVO.success(systemUserService.delete(entity));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年11月1日 下午3:10:35
     * 描述 : 用户解锁
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : resetPwd
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "user:unlock")
    @Log(moduleName = "用户管理", description = "用户解锁", businessType = BusinessType.EDIT)
    @Operation(summary = "用户解锁")
    @PostMapping(value = "/unlock")
    public ResponseVO<String> unlock(@Valid @RequestBody SystemUserUnlockDTO entity) {
        return ResponseVO.success(systemUserService.unlock(entity));
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年11月1日 下午3:05:23
     * 描述 : 重置密码
     * 包名 : com.ccit.area.sales.web.controller.system
     * 方法名 : resetPwd
     * ResponseVO<String>
     *
     * @throws
     */
    @Permissions(value = "user:resetPwd")
    @Log(moduleName = "用户管理", description = "重置密码", businessType = BusinessType.EDIT)
    @Operation(summary = "重置密码")
    @PostMapping(value = "/resetPwd")
    public ResponseVO<String> resetPwd(@Valid @RequestBody SystemUserResetPwdDTO entity) {
        return ResponseVO.success(systemUserService.resetPwd(entity));
    }

    @Operation(summary = "同步钉钉用户信息")
    @GetMapping("/updateUserOAInformation/{id}")
    public ResponseVO<String> updateUserOAInformation(@PathVariable Long id) {
        return ResponseVO.success(systemUserService.updateUserOAInformation(id));
    }

    @Operation(summary = "获取钉钉用户ID")
    @PostMapping("/getUserName")
    public ResponseVO<SystemUserDetailsVO> getUserName(@RequestBody SystemUserPageListDTO entity) {

        return ResponseVO.success(systemUserService.getUserName(entity));
    }

}
