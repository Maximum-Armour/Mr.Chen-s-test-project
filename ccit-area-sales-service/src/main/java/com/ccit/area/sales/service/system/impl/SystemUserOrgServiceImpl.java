package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.dao.domain.system.SystemUserOrgPO;
import com.ccit.area.sales.dao.dto.system.SystemOrgUpdateStatusDTO;
import com.ccit.area.sales.dao.mapper.system.SystemUserOrgMapper;
import com.ccit.area.sales.service.system.ISystemUserOrgService;
import org.springframework.stereotype.Service;

/**
 *  * 描述 : “用户组织中间”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月9日 上午11:03:58
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.system.impl
 *  * 类名 : SystemUserOrgServiceImpl
 */
@Service
public class SystemUserOrgServiceImpl extends ServiceImpl<SystemUserOrgMapper, SystemUserOrgPO> implements ISystemUserOrgService {
    @Override
    public String updateDisplayStatus(SystemOrgUpdateStatusDTO updateStatusDTO) {

        LambdaUpdateWrapper<SystemUserOrgPO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUserOrgPO::getUserId, updateStatusDTO.getUserId());
        updateWrapper.eq(SystemUserOrgPO::getOrgId, updateStatusDTO.getOrgId());
        updateWrapper.set(SystemUserOrgPO::getDisplayStatus, updateStatusDTO.getDisplayStatus());
        int update = this.baseMapper.update(null, updateWrapper);
        if (update > 0) {
            return "修改成功";
        }
        return "修改失败";
    }
}
