package com.ccit.area.sales.service.sales.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.mapper.sales.QuantityManagementMapper;
import com.ccit.area.sales.dao.vo.sales.WeightPageListVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.sales.QuantityManagementSevice;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuantityManagementSeviceImpl extends ServiceImpl<QuantityManagementMapper, ListedPO> implements QuantityManagementSevice {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ISystemOrgService isSystemOrgService;

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月10日 下午1:52:06
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.sales.impl
     * 方法名 : selectPageList
     * Page<WeightPageListVO>
     *
     * @throws
     */
    @Override
    public Page<WeightPageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<WeightPageListVO> pages = null;
        if (page == null && size == null || page <= 0 && size <= 0) {
            pages = new Page<>(0, Integer.MAX_VALUE);
        } else {
            pages = new Page<>(page, size);
        }
        param.put("deleted", GlobalConstants.DELETE_NO);
        SystemCurrentUserVO systemUser = null;
        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            String userName = systemUser.getUserName();
            if (userName.equals("sysadmin")) {
                param.put("userName", "");
                param.put("orgNo", "");
            } else {
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
                List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
                param.put("orgNoList", existOrgNo);
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }
        //获取挂牌信息
        List<WeightPageListVO> weightPageListVOList = this.baseMapper.selectPageList(param);
        //获取竞价信息
        List<WeightPageListVO> weightPageListVOS = this.baseMapper.selectBiddingPageList(param);
        if (!weightPageListVOS.isEmpty()) {
            weightPageListVOS.forEach(t -> {
                t.setUseStatus("YSJ");
                t.setListedMode("JJ");
            });
            weightPageListVOList.addAll(weightPageListVOS);
        }
        pages.setRecords(weightPageListVOList);

        // **手动计算分页参数**
        long total = weightPageListVOList.size(); // 总记录数
        long totalPages = (total + size - 1) / size; // 计算总页数，向上取整
        long currentSize = Math.min(size, total - (page - 1) * size); // 当前页实际数据条数
        pages.setTotal(total);
        pages.setPages((int) totalPages);
        pages.setSize(currentSize);

        if (!pages.getRecords().isEmpty()) {
            pages.getRecords().forEach(t -> {
                t.setListedModeName(redisUtils.getDict("listedMode_", t.getListedMode()));
                t.setUseStatusName(redisUtils.getDict("useStatus_", t.getUseStatus()));
                t.setPayWayName(redisUtils.getDict("zhlx_", t.getPayWay()));
                t.setTradeTypeName(redisUtils.getDict("tradeType_", t.getTradeType()));
                t.setUnitWeightName(redisUtils.getDict("unit_", t.getUnitWeight()));
                t.setUnitName(redisUtils.getDict("priceUnit_", t.getUnit()));
                t.setDistributionWay(redisUtils.getDict("psfs_", t.getDistributionWay()));
                t.setCurrency(redisUtils.getDict("currency_", t.getCurrency()));
                t.setTransportWay(redisUtils.getDict("ysfs_", t.getTransportWay()));
            });
        }
        return pages;

    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月10日 上午8:59:12
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.sales.impl
     * 方法名 : get
     * WeightPageListVO
     *
     * @throws
     */
    @Override
    public WeightPageListVO get(Long id) {
        if (null == id) {
            throw new BusinessException("挂牌ID不能为空");
        }
        WeightPageListVO selectlist = this.baseMapper.selectlist(id);
        if (null == selectlist) {
            throw new BusinessException("获取挂牌失败");
        }
        return selectlist;
    }


}




