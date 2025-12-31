package com.ccit.area.sales.service.customerprofile.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.customer.TAccountInfomationPO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationLockDTO;
import com.ccit.area.sales.dao.mapper.customerprofile.AccountInfomationMapper;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationVO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationsVO;
import com.ccit.area.sales.service.customerprofile.AccountInfomationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_account_infomation(企业注册账户信息表)】的数据库操作Service实现
 * @createDate 2024-10-11 14:20:25
 */
@Service
public class AccountInfomationServiceImpl extends ServiceImpl<AccountInfomationMapper, TAccountInfomationPO> implements AccountInfomationService {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String updateLockStatus(UpdateAccountInformationLockDTO accountInformation) {

        UpdateWrapper<TAccountInfomationPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("resource_locking_status", accountInformation.getResourceLockingStatus());
        updateWrapper.in("id", accountInformation.getIds());
        int update = this.baseMapper.update(null, updateWrapper);
        if (update > 0) {
            return "修改成功";
        }
        return "修改失败";
    }


    @Override
    public String updateStatus(UpdateAccountInformationDTO accountInformation) {

        UpdateWrapper<TAccountInfomationPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", accountInformation.getStatus());
        updateWrapper.in("id", accountInformation.getIds());
        int update = this.baseMapper.update(null, updateWrapper);
        if (update > 0) {
            return "修改成功";
        }
        return "修改失败";
    }

    @Override
    public Page<TAccountInfomationVO> selectPageList(Map<String, Object> param) {
        // 获取并验证页码和每页大小
        Integer page = param.containsKey("page") ? (Integer) param.get("page") : null;
        Integer size = param.containsKey("size") ? (Integer) param.get("size") : null;

        int defaultPage = 1; // 默认页码
        int defaultSize = 10; // 默认每页大小

        if (page == null || page <= 0) {
            page = defaultPage;
        }

        if (size == null || size <= 0) {
            size = defaultSize;
        }

        // 创建分页对象
        Page<TAccountInfomationVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);
        List<TAccountInfomationVO> tAccountInfomationVOS = baseMapper.selectPageList(pages, param);
        if (tAccountInfomationVOS != null) {
            tAccountInfomationVOS.stream().forEach(t -> {
                t.setStatus(redisUtils.getDict("status_",t.getStatus()));
            });
        }
        // 执行数据库查询
        pages.setRecords(tAccountInfomationVOS);
        return pages;
    }

    @Override
    public TAccountInfomationsVO getRegisterAccountMessage(Long id) {
        TAccountInfomationPO accountInfomationPO = this.baseMapper.selectById(id);
        accountInfomationPO.setStatus(redisUtils.getDict("status_",accountInfomationPO.getStatus()));

        TAccountInfomationsVO tAccountInfomationsVO = new TAccountInfomationsVO();
        BeanUtils.copyProperties(accountInfomationPO, tAccountInfomationsVO);

        return tAccountInfomationsVO;
    }


}




