package com.ccit.area.sales.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.dingding.utils.HttpClientUtil;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.PasswordUtil;
import com.ccit.area.sales.dao.dingding.vo.GetTokenResponse;
import com.ccit.area.sales.dao.dingding.vo.MobileGetUserIdVO;
import com.ccit.area.sales.dao.domain.system.SystemUserOrgPO;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.domain.system.SystemUserRolePO;
import com.ccit.area.sales.dao.dto.system.*;
import com.ccit.area.sales.dao.mapper.system.SystemUserMapper;
import com.ccit.area.sales.dao.vo.system.*;
import com.ccit.area.sales.service.system.ISystemUserOrgService;
import com.ccit.area.sales.service.system.ISystemUserRoleService;
import com.ccit.area.sales.service.system.ISystemUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  * 描述 : “用户”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月4日 上午10:40:56
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.system.impl
 *  * 类名 : SystemUserServiceImpl
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserPO> implements ISystemUserService {


    @Autowired
    private ISystemUserOrgService iSystemUserOrgService;

    /**
     * “用户角色中间”服务类
     */
    @Autowired
    private ISystemUserRoleService systemUserRoleService;

    /**
     * “用户组织中间”服务类
     */
    @Autowired
    private ISystemUserOrgService systemUserOrgService;

    @Value("${dingding.get_token}")
    private String getTokenUrl;
    @Value("${dingding.app_key}")
    private String appKey;
    @Value("${dingding.app_secret}")
    private String appSecret;

    @Value("${dingding.mobile_get_userId}")
    private String mobileGetUserId;


    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:51:09
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : selectPageList
     * Page<SystemUserPageListVO>
     *
     * @throws
     */
    @Override
    public Page<SystemUserPageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<SystemUserPageListVO> pages = null;
        if (page == null && size == null || page <= 0 && size <= 0) {
            pages = new Page<>(0, Integer.MAX_VALUE);
        } else {
            pages = new Page<>(page, size);
        }
        param.put("deleted", GlobalConstants.DELETE_NO);
        param.put("neUserName", GlobalConstants.SUPER_ADMIN);
        pages.setRecords(this.baseMapper.selectPageList(pages, param));
        return pages;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:51:13
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : add
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String add(SystemUserAddDTO entity) {
        LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
        wrapper.eq(SystemUserPO::getUserName, entity.getUserName());
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount > 0) {
            throw new BusinessException("用户账号已存在，请进行检查！");
        }
        SystemUserPO systemUser = new SystemUserPO();
        systemUser.setPassword(PasswordUtil.getEncodePwd(GlobalConstants.DEFAULT_PASSWRD));
        BeanUtils.copyProperties(entity, systemUser);
        int insertFlag = this.baseMapper.insert(systemUser);
        if (insertFlag > 0) {
            SystemUserRolePO systemUserRole = new SystemUserRolePO(systemUser.getId(), entity.getRoleId());
            systemUserRoleService.save(systemUserRole);
            List<SystemUserOrgPO> userOrgList = new ArrayList<>();
            entity.getOrgIds().forEach(orgId -> {
                userOrgList.add(new SystemUserOrgPO(systemUser.getId(), orgId));
            });
            systemUserOrgService.saveBatch(userOrgList);
//            return "新增成功！";
        }

        try {
            String sendGet = HttpClientUtil.sendGet(getTokenUrl + "?Gppkey=" + appKey + "&appsecret=" + appSecret, null);
            GetTokenResponse tokenResponse = JSON.parseObject(sendGet, GetTokenResponse.class);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", systemUser.getMobile());
            String sendPost = HttpClientUtil.sendPost(mobileGetUserId + "?access_token=" + tokenResponse.getAccessToken(), jsonObject.toString());
            MobileGetUserIdVO mobileGetUserIdVO = JSON.parseObject(sendPost, MobileGetUserIdVO.class);
            if (mobileGetUserIdVO.getResult().getUserid() == null) {
                return "新增用户信息成功,钉钉账号不存在,请稍后手动同步";
            }
            LambdaUpdateWrapper<SystemUserPO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SystemUserPO::getUserName, systemUser.getUserName());
            updateWrapper.set(SystemUserPO::getDingdingUserId, mobileGetUserIdVO.getResult().getUserid());
            int update = this.baseMapper.update(null, updateWrapper);
            if (update > 0) {
                return "新增成功!";
            }

        } catch (Exception e) {
            log.error("新增用户信息成功,钉钉账号不存在,请稍后手动同步");
            return "新增用户信息成功,钉钉账号不存在,请稍后手动同步";
        }

        throw new BusinessException("新增失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:51:16
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : get
     * SystemUserDetailsVO
     *
     * @throws
     */
    @Override
    public SystemUserDetailsVO get(Long id) {
        if (null == id) {
            throw new BusinessException("用户ID不能为空，请进行检查！");
        }
        SystemUserPO systemUser = this.baseMapper.selectById(id);
        if (null == systemUser) {
            throw new BusinessException("获取用户失败，请刷新浏览器重新操作！");
        }
        Long roleId = 0L;
        LambdaQueryWrapper<SystemUserRolePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUserRolePO::getUserId, systemUser.getId());
        SystemUserRolePO systemUserRole = systemUserRoleService.getOne(wrapper);
        if (systemUserRole != null) {
            roleId = systemUserRole.getRoleId();
        }
        List<Long> orgIds = new ArrayList<>();
        LambdaQueryWrapper<SystemUserOrgPO> userOrgWrapper = new LambdaQueryWrapper<>();
        userOrgWrapper.eq(SystemUserOrgPO::getUserId, id);
        List<SystemUserOrgPO> systemUserOrgList = systemUserOrgService.list(userOrgWrapper);
        if (!CollectionUtils.isEmpty(systemUserOrgList)) {
            orgIds = systemUserOrgList.stream().map(v -> v.getOrgId()).collect(Collectors.toList());
        }
        SystemUserDetailsVO systemUserDetailsVO = new SystemUserDetailsVO();
        BeanUtils.copyProperties(systemUser, systemUserDetailsVO);
        systemUserDetailsVO.setRoleId(roleId);
        systemUserDetailsVO.setOrgIds(orgIds);
        return systemUserDetailsVO;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:51:20
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(SystemUserEditDTO entity) {
        SystemUserPO systemUser = new SystemUserPO();
        BeanUtils.copyProperties(entity, systemUser);
        int updateByIdFlag = this.baseMapper.updateById(systemUser);
        if (updateByIdFlag > 0) {
            LambdaQueryWrapper<SystemUserRolePO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemUserRolePO::getUserId, entity.getId());
            SystemUserRolePO systemUserRole = systemUserRoleService.getOne(wrapper);
            if (systemUserRole != null) {
                if (systemUserRole.getRoleId() != entity.getRoleId()) {
                    systemUserRole.setRoleId(entity.getRoleId());
                    systemUserRoleService.updateById(systemUserRole);
                }
            }
            LambdaQueryWrapper<SystemUserOrgPO> userOrgWrapper = new LambdaQueryWrapper<>();
            userOrgWrapper.eq(SystemUserOrgPO::getUserId, entity.getId());
            systemUserOrgService.remove(userOrgWrapper);
            List<SystemUserOrgPO> userOrgList = new ArrayList<>();
            entity.getOrgIds().forEach(orgId -> {
                userOrgList.add(new SystemUserOrgPO(systemUser.getId(), orgId));
            });
            systemUserOrgService.saveBatch(userOrgList);
            return "修改成功！";
        }
        throw new BusinessException("修改失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:51:22
     * 描述 : 删除【一条&多条】数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : delete
     * String
     *
     * @throws
     */
    @Override
    public String delete(SystemUserDeleteDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("用户ID不能为空，请进行检查！");
        }
        LambdaQueryWrapper<SystemUserPO> wrapper = SystemUserPO.wrapper();
        wrapper.in(SystemUserPO::getId, entity.getIds());
        int updateFlag = this.baseMapper.update(new SystemUserPO(true, false), wrapper);
        if (updateFlag > 0) {
            return "删除成功！";
        }
        throw new BusinessException("删除失败，请刷新浏览器重新操作！");
    }

    /**
     * 根据用户名查询用户详情
     */
    @Override
    public SystemUserDetailVO selectUserDetails(String userName) {
        return this.baseMapper.selectUserDetails(userName);
    }

    @Override
    public List<SystemUserDetailVO> selectUserDetailsList(String userName) {
        return this.baseMapper.selectUserDetailsList(userName);
    }

    /**
     * 同步钉钉与用户信息关联
     *
     * @param
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateUserOAInformation(Long id) {

        SystemUserPO systemUserPO = this.baseMapper.selectById(id);

        if (systemUserPO == null) {
            return "用户信息不存在!";
        }

        String sendGet = HttpClientUtil.sendGet(getTokenUrl + "?appkey=" + appKey + "&appsecret=" + appSecret, null);
        GetTokenResponse tokenResponse = JSON.parseObject(sendGet, GetTokenResponse.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", systemUserPO.getMobile());
        String sendPost = HttpClientUtil.sendPost(mobileGetUserId + "?access_token=" + tokenResponse.getAccessToken(), jsonObject.toString());
        MobileGetUserIdVO mobileGetUserIdVO = JSON.parseObject(sendPost, MobileGetUserIdVO.class);
        if (mobileGetUserIdVO.getResult().getUserid() == null) {
            return "同步失败,钉钉账号不存在!";
        }

        LambdaUpdateWrapper<SystemUserPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUserPO::getUserName, systemUserPO.getUserName());
        updateWrapper.set(SystemUserPO::getDingdingUserId, mobileGetUserIdVO.getResult().getUserid());
        int update = this.baseMapper.update(null, updateWrapper);
        if (update > 0) {
            return "同步成功!";
        }

        throw new BusinessException("同步失败!");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年11月1日 下午3:14:19
     * 描述 : 用户解锁
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : unlock
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String unlock(SystemUserUnlockDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("用户ID不能为空，请进行检查！");
        }
        UpdateWrapper<SystemUserPO> wrapper = new UpdateWrapper<>();
        wrapper.set("status", 0);
        wrapper.set("locked_date", null);
        wrapper.set("login_fail_count", 0);
        wrapper.in("id", entity.getIds());
        Boolean updateFlag = this.update(wrapper);
        if (updateFlag) {
            return "用户解锁失败！";
        }
        throw new BusinessException("用户解锁失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年11月1日 下午3:14:25
     * 描述 : 重置密码
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : resetPwd
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String resetPwd(SystemUserResetPwdDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("用户ID不能为空，请进行检查！");
        }
        UpdateWrapper<SystemUserPO> wrapper = new UpdateWrapper<>();
        wrapper.set("password", PasswordUtil.getEncodePwd(GlobalConstants.DEFAULT_PASSWRD));
        wrapper.set("password_expired", 1);
        wrapper.set("password_last_changed", new Date());
        wrapper.in("id", entity.getIds());
        Boolean updateFlag = this.update(wrapper);
        if (updateFlag) {
            return "重置密码成功！";
        }
        throw new BusinessException("重置密码失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:50:45
     * 描述 : 组织
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : selectOrgName
     * List<SystemUserDetailVO>
     *
     * @throws
     */
    @Override
    public List<SystemOrgVO> selectOrgName(String userName) {
        List<SystemOrgVO> systemOrgVOS = this.baseMapper.selectOrg(userName);
        List<SystemGroupOrgNameVO> systemGroupOrgNameVOS = this.baseMapper.selectOrgType(userName);
        for (int i = 0; i < systemGroupOrgNameVOS.size(); i++) {
            String orgType = systemGroupOrgNameVOS.get(i).getOrgType();
            if (orgType.equals("DEPT") || orgType.equals("GROUP")) {
                String orgName = systemGroupOrgNameVOS.get(i).getOrgName();
                Long parentId = systemGroupOrgNameVOS.get(i).getParentId();
                String s = this.baseMapper.selectOrgName(parentId);
                systemOrgVOS.get(i).setOrgName(s + "—" + orgName);
            } else {
                systemOrgVOS.get(i).setOrgName(systemGroupOrgNameVOS.get(i).getOrgName());
            }
            systemOrgVOS.get(i).setOrgNo(systemGroupOrgNameVOS.get(i).getOrgNo());
            systemOrgVOS.get(i).setDisplayStatus(systemGroupOrgNameVOS.get(i).getDisplayStatus());
            systemOrgVOS.get(i).setOrgId(Integer.parseInt(systemGroupOrgNameVOS.get(i).getOrgId()));
        }
        try {
            List<SystemUserDetailVO> selectUserDetailsList = this.baseMapper.selectUserDetailsList(userName);
            for (SystemUserDetailVO systemUserDetailVO : selectUserDetailsList) {
                if ("1".equals(systemUserDetailVO.getDisplayStatus())) {
                    return systemOrgVOS;
                }
            }
            // 如果没有找到显示状态为 "1" 的部门信息，则选择第一个部门信息并尝试更新显示状态
            SystemUserDetailVO systemUserDetailVO = selectUserDetailsList.get(0);
            SystemOrgUpdateStatusDTO systemOrgUpdateStatusDTO = new SystemOrgUpdateStatusDTO();
            systemOrgUpdateStatusDTO.setUserId(systemUserDetailVO.getUserId());
            systemOrgUpdateStatusDTO.setOrgId(systemUserDetailVO.getDeptId());
            systemOrgUpdateStatusDTO.setDisplayStatus("1");
            String status = iSystemUserOrgService.updateDisplayStatus(systemOrgUpdateStatusDTO);

            if ("修改失败".equals(status)) {
                log.error("用户 {} 部门初始化信息初始失败: 修改显示状态失败");
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }
        return systemOrgVOS;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:50:45
     * 描述 : 组织更新
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : updateOrgName
     * String
     *
     * @throws
     */
    @Override
    public String updateOrgName(String orgId, String userId) {
        int display = this.baseMapper.display(userId);
        if (display>0){
            int updateByIdFlag = this.baseMapper.displayStatus(orgId,userId);
            if (updateByIdFlag > 0) {
                return "修改成功";
            }
        }
        throw new BusinessException("修改失败");
    }

    @Override
    public SystemUserDetailsVO getUserName(SystemUserPageListDTO entity) {

        SystemUserDetailsVO userName = this.baseMapper.getUserName(entity.getUserName());
        if (userName == null) {
            throw new BusinessException("用户不存在，请检查输入的用户名");
        }
        return userName;
    }


}
