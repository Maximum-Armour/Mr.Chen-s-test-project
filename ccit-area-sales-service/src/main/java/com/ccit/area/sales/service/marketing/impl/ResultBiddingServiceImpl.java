package com.ccit.area.sales.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.marketing.PriceBiddingPO;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingResultPageListDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingSubmitDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesWinBidStatusDTO;
import com.ccit.area.sales.dao.mapper.marketing.PriceBiddingMapper;
import com.ccit.area.sales.dao.mapper.marketing.ResultBiddingMapper;
import com.ccit.area.sales.dao.mapper.marketing.SalesBiddingMapper;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingResultPageListVO;
import com.ccit.area.sales.service.marketing.IResultBiddingService;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 *  * 描述 : “竞价结果”服务实现类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月18日 上午11:00:24
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding.impl
 *  * 类名 : ResultBiddingServiceImpl
 */
@Service
public class ResultBiddingServiceImpl extends ServiceImpl<ResultBiddingMapper, SalesBiddingPO> implements IResultBiddingService {

    @Autowired
    private SalesBiddingMapper salesBiddingMapper;

    @Autowired
    private PriceBiddingMapper priceBiddingMapper;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 上午08:55:23
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : selectPageList
     * Result<Page<SalesBiddingPageListVO>>
     *
     * @throws
     */
    @Override
    public List<SalesBiddingResultPageListVO> selectPageList(SalesBiddingResultPageListDTO param) {
        List<SalesBiddingResultPageListVO> salesBiddingResultPageListVOS = this.baseMapper.selectPageList(param);
        salesBiddingResultPageListVOS.stream().forEach(t -> {
            t.setLatestQuotation(t.getLatestQuotation().setScale(0, RoundingMode.DOWN));
            t.setQuantity(t.getQuantity().setScale(0, RoundingMode.DOWN));
            t.setTotalAmount(t.getLatestQuotation().multiply(t.getQuantity()).setScale(0, RoundingMode.DOWN));
        });

        return salesBiddingResultPageListVOS;
    }


    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 下午03:43:14
     * 描述 : 审核数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : submit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String submit(SalesBiddingSubmitDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("产品竞价ID不能为空");
        }
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.in(SalesBiddingPO::getId, entity.getIds());
        SalesBiddingPO salesBiddingPO = new SalesBiddingPO();
        String status = entity.getStatus();
        if (Objects.equals(status, "1")) {
            salesBiddingPO.setStatus("审核通过");
            int updateFlag = this.baseMapper.update(salesBiddingPO, wrapper);
            if (updateFlag > 0) {
                return "审核通过";
            }
        } else {
            salesBiddingPO.setStatus("审核拒绝");
            int updateFlag = this.baseMapper.update(salesBiddingPO, wrapper);
            if (updateFlag > 0) {
                return "审核拒绝";
            }
        }
        throw new BusinessException("提交失败");
    }

    /**
     * 更新中标状态
     *
     * 该方法主要用于更新销售投标中的中标状态信息它首先验证输入参数的有效性，
     * 然后根据提供的实体信息更新数据库中的中标状态如果更新成功，进一步检查是否需要更新关联的销售投标状态
     *
     * @param entity 包含要更新的中标状态信息的DTO对象
     * @return 如果更新成功，返回"修改成功"；否则抛出异常
     * @throws IllegalArgumentException 如果输入参数无效
     * @throws BusinessException 如果更新操作失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateWinBidStatus(SalesWinBidStatusDTO entity) {

        // 验证输入参数的有效性
        if (entity == null || entity.getIds() == null || entity.getIds().isEmpty() || entity.getTenderNumber() == null) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        try {
            // 准备更新中标状态的条件和新状态
            LambdaUpdateWrapper<PriceBiddingPO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(PriceBiddingPO::getId, entity.getIds());
            wrapper.set(PriceBiddingPO::getWinBidStatus, entity.getStatus());
            int update = priceBiddingMapper.update(null, wrapper);

            // 检查中标状态是否更新成功
            if (update > 0) {
                // 进一步检查是否需要更新销售投标的状态
                LambdaQueryWrapper<PriceBiddingPO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(PriceBiddingPO::getTenderNumber, entity.getTenderNumber());
                queryWrapper.eq(PriceBiddingPO::getWinBidStatus, "已中标");
                List<PriceBiddingPO> priceBiddingPOS = priceBiddingMapper.selectList(queryWrapper);

                // 根据查询结果决定是否需要更新销售投标的状态
                if (Collections.isEmpty(priceBiddingPOS)) {
                    if (!"未中标".equals(entity.getStatus())){
                        LambdaUpdateWrapper<SalesBiddingPO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SalesBiddingPO::getTenderNumber, entity.getTenderNumber());
                        updateWrapper.set(SalesBiddingPO::getStatus, entity.getStatus());
                        int response = salesBiddingMapper.update(null, updateWrapper);

                        // 检查销售投标状态是否更新成功
                        if (response > 0) {
                            return "修改成功";
                        } else {
                            throw new BusinessException("更新失败");
                        }
                    }
                }
                return "修改成功";
            }
            throw new BusinessException("更新失败");
        } catch (Exception e) {
            // 记录日志
            throw new BusinessException("系统错误: " + e.getMessage());
        }
    }


}
