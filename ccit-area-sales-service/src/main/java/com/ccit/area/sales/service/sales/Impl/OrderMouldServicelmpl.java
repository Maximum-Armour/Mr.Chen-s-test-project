package com.ccit.area.sales.service.sales.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.sales.OrderMouldPO;
import com.ccit.area.sales.dao.dto.sales.OrderMouldDTO;
import com.ccit.area.sales.dao.mapper.sales.OrderLineMapper;
import com.ccit.area.sales.dao.mapper.sales.OrderMouldMapper;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;
import com.ccit.area.sales.service.sales.OrderMouldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderMouldServicelmpl extends ServiceImpl<OrderMouldMapper, OrderMouldPO> implements OrderMouldService {

    @Autowired
    private OrderLineMapper orderLineMapper;

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月19日 下午16:30:15
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    @Override
    public String edit(OrderMouldDTO entity) {
        if (entity.getOrderLineNo() == null || entity.getOrderLineNo().isEmpty()) {
            throw new BusinessException("订单行不能为空");
        }
        String orderLineNo = entity.getOrderLineNo();
//        LambdaUpdateWrapper<OrderLinePO> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.eq(OrderLinePO::getOrderLineNo, orderLineNo);
//        wrapper.set(OrderLinePO::getOrderStartTime, entity.getOrderStartTime());
//        wrapper.set(OrderLinePO::getOrderEndTime, entity.getOrderEndTime());
//        int update = orderLineMapper.update(null, wrapper);
//        if (update < 0) {
//            throw new BusinessException("更新失败");
//        }
        OrderMouldnoVO orderMouldnoVO = baseMapper.selectorderLineNo(orderLineNo);
        if (orderMouldnoVO != null) {
            OrderMouldPO orderMouldPO = new OrderMouldPO();
            BeanUtils.copyProperties(entity, orderMouldPO);
            int i = this.baseMapper.updateorderLineNo(orderMouldPO);
            if (i > 0) {
                return "修改成功";
            }
        } else {
            OrderMouldPO OrderMouldPO = new OrderMouldPO();
            BeanUtils.copyProperties(entity, OrderMouldPO);
            int insert = this.baseMapper.insert(OrderMouldPO);
            if (insert > 0) {
                return "新增成功";
            }
        }
        throw new BusinessException("新增失败");
    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月19日 下午16:30:15
     * 描述 : 查询数据
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : select
     * OrderMouldnoVO
     *
     * @throws
     */
    @Override
    public OrderMouldnoVO select(OrderMouldDTO entity) {

        if (entity.getOrderLineNo() == null || entity.getOrderLineNo().isEmpty()) {
            throw new BusinessException("订单行不能为空");
        }
        String orderLineNo = entity.getOrderLineNo();
        OrderMouldnoVO orderMouldnoVO = baseMapper.selectorderLineNo(orderLineNo);
        return orderMouldnoVO;
    }


}
