package com.ccit.area.sales.service.sales.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.common.utils.SortUtil;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsItemPO;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsPO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsSaveDTO;
import com.ccit.area.sales.dao.mapper.sales.OrderStatisticsMapper;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsItemVO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsSubmitVO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.sales.OrderStatisticsItemService;
import com.ccit.area.sales.service.sales.OrderStatisticsService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.ISystemUserService;
import com.ccit.common.utils.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Baishangqianxue
 * @description 针对表【t_order_statistics(接单统计表)】的数据库操作Service实现
 * @createDate 2025-02-24 16:55:41
 */
@Service
public class OrderStatisticsServiceImpl extends ServiceImpl<OrderStatisticsMapper, OrderStatisticsPO>
        implements OrderStatisticsService {
    @Autowired
    private ISystemUserService iSystemUserService;
    @Autowired
    private ISystemOrgService isSystemOrgService;
    @Autowired
    private ISystemSequenceService systemSequenceService;
    @Autowired
    private OrderStatisticsItemService orderStatisticsItemService;

    @Override
    public Page<OrderStatisticsVO> selectPageList(Map<String, Object> param) {

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
        Page<OrderStatisticsVO> pages = new Page<>(page, size);
        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);
        Object field = param.get("field");
        if (field != null && !field.toString().isEmpty()) {
            // 将转换后的 key 和原始的 value 放入 param
            param.put("field", SortUtil.convertCamelToUnderline(param.get("field")));
        }

        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            SystemCurrentUserVO systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            String userName = systemUser.getUserName();
            if ("sysadmin".equals(userName)) {
                param.put("orgNoList", "");
            } else {
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
                List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
                param.put("orgNoList", existOrgNo);
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }

        // 执行数据库查询
        pages.setRecords(this.baseMapper.selectPageList(pages, param));
        return pages;
    }

    @Override
    public OrderStatisticsVO get(String takeOrderNo) {
        LambdaQueryWrapper<OrderStatisticsPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderStatisticsPO::getTakeOrderNo, takeOrderNo);
        OrderStatisticsPO orderStatisticsPO = this.baseMapper.selectOne(queryWrapper);
        if (orderStatisticsPO != null) {
            OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
            BeanUtil.copyProperties(orderStatisticsPO, orderStatisticsVO);
            return orderStatisticsVO;
        }
        log.error("查询接单统计详情接口失败!");
        throw new BusinessException("查询失败!");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String delete(List<Long> ids) {
        LambdaUpdateWrapper<OrderStatisticsPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(OrderStatisticsPO::getId, ids);
        wrapper.set(OrderStatisticsPO::getDeleted, 1);
        int update = this.baseMapper.update(null, wrapper);
        if (update > 0) {
            return "删除成功";
        }
        throw new BusinessException("删除失败");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String saveOrderStatistics(List<OrderStatisticsSaveDTO> orderStatisticsSaveDTO) {

        if (orderStatisticsSaveDTO.isEmpty()) {
            throw new BusinessException("参数为空,新增失败");
        }

        //获取用户信息
        JSONObject userJson = CurrentUserUtil.getCurrentUser();
        SystemCurrentUserVO systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
        if (systemUser == null) {
            throw new BusinessException("用户信息不存在");
        }
        //获取公司信息
        SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemUser.getUserName());
        if (selectUserDetails == null) {
            throw new BusinessException("公司信息不存在");
        }
        SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(selectUserDetails.getOrgNo());
        if (fatherOrgNo == null) {
            throw new BusinessException("公司信息不存在");
        }

        //生成接单编码
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        String code = "JDQKO" + formattedDate;
        String taskOrderNo = systemSequenceService.get(code, 4);
        if (taskOrderNo == null) {
            throw new BusinessException("接单编码生成失败");
        }

        //新增接单统计表
        OrderStatisticsPO orderStatisticsPO = new OrderStatisticsPO();
        orderStatisticsPO.setCreateBy(systemUser.getUserName());
        orderStatisticsPO.setCreateByName(systemUser.getRealName());
        orderStatisticsPO.setCompanyNo(fatherOrgNo.getOrgNo());
        orderStatisticsPO.setCompanyName(fatherOrgNo.getOrgName());
        orderStatisticsPO.setTakeOrderNo(taskOrderNo);
        int insert = this.baseMapper.insert(orderStatisticsPO);
        if (insert < 1) {
            throw new BusinessException("新增失败");
        }

        for (OrderStatisticsSaveDTO statisticsSaveDTO : orderStatisticsSaveDTO) {
            OrderStatisticsItemPO orderStatisticsItemPO = new OrderStatisticsItemPO();
            BeanUtils.copyProperties(statisticsSaveDTO, orderStatisticsItemPO);
            orderStatisticsItemPO.setCreateBy(systemUser.getUserName());
            orderStatisticsItemPO.setCreateByName(systemUser.getRealName());
            orderStatisticsItemPO.setCompanyNo(fatherOrgNo.getOrgNo());
            orderStatisticsItemPO.setCompanyName(fatherOrgNo.getOrgName());
            orderStatisticsItemPO.setTakeOrderNo(taskOrderNo);
            //新增接单统计明细表
            boolean save = orderStatisticsItemService.save(orderStatisticsItemPO);
            if (!save) {
                throw new BusinessException("新增失败");
            }
        }


        return "新增成功";
    }

    /**
     * 提交钉钉
     *
     * @param id
     * @return
     */
    @Override
    public OrderStatisticsSubmitVO submit(Long id) {
        //获取表头
        OrderStatisticsPO orderStatisticsPO = this.baseMapper.selectById(id);
        OrderStatisticsSubmitVO orderStatisticsSubmitVO = new OrderStatisticsSubmitVO();
        BeanUtils.copyProperties(orderStatisticsPO, orderStatisticsSubmitVO);
        //拼接明细
        List<OrderStatisticsItemVO> statisticsItemVOS = orderStatisticsItemService.getItem(orderStatisticsPO.getTakeOrderNo());
        List<String> itemList = statisticsItemVOS.stream().map(statisticsItemVO -> {
            String item = "编码:" + statisticsItemVO.getId() + "接单编码:" + statisticsItemVO.getTakeOrderNo() + "订单行编码:" + statisticsItemVO.getOrderLineNo() + "客户名称:" + statisticsItemVO.getCustomerName() +
                    "产品名称:" + statisticsItemVO.getMaterielName() + "提交价格:" + statisticsItemVO.getOrderSubmissionPrice() + "提交数量:" + statisticsItemVO.getOrderSubmissionQuantity() + "公司名称:" + statisticsItemVO.getCompanyName() + "制单人:" + statisticsItemVO.getCreateByName() + "制单时间:" + statisticsItemVO.getGmtCreate();
            return item;
        }).collect(Collectors.toList());
        orderStatisticsSubmitVO.setItem(itemList.toString());
        return orderStatisticsSubmitVO;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateStatus(Long id) {
        OrderStatisticsPO orderStatisticsPO = this.baseMapper.selectById(id);
        List<OrderStatisticsItemVO> item = orderStatisticsItemService.getItem(orderStatisticsPO.getTakeOrderNo());
        for (OrderStatisticsItemVO orderStatisticsItemVO : item) {
            Integer status = this.baseMapper.updateStatus(orderStatisticsItemVO.getOrderLineNo());
            if (status < 0) {
                throw new BusinessException("修改失败");
            }
        }
        return "修改成功";
    }
}




