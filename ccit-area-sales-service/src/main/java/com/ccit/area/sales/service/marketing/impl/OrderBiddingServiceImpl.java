package com.ccit.area.sales.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.marketing.PriceBiddingPO;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.domain.sales.OrderLinePO;
import com.ccit.area.sales.dao.domain.system.SystemOrgPO;
import com.ccit.area.sales.dao.mapper.marketing.OrderBiddingMapper;
import com.ccit.area.sales.dao.mapper.marketing.PriceBiddingMapper;
import com.ccit.area.sales.dao.mapper.marketing.SalesBiddingMapper;
import com.ccit.area.sales.dao.mapper.sales.OrderLineMapper;
import com.ccit.area.sales.dao.mapper.sales.QuantityManagementMapper;
import com.ccit.area.sales.dao.mapper.system.SystemOrgMapper;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingPageListVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingSubmitVO;
import com.ccit.area.sales.feign.ExamineFeign;
import com.ccit.area.sales.service.marketing.IOrderBiddingService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.impl.SystemOrgServiceImpl;
import io.jsonwebtoken.lang.Collections;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  * 描述 : “竞价订单”服务实现类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月24日 上午11:00:24
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding.impl
 *  * 类名 : OrderBiddingServiceImpl
 */
@Service
public class OrderBiddingServiceImpl extends ServiceImpl<OrderBiddingMapper, SalesBiddingPO> implements IOrderBiddingService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PriceBiddingMapper priceBiddingMapper;

    @Autowired
    private SalesBiddingMapper salesBiddingMapper;

    @Autowired
    private QuantityManagementMapper quantityMapper;

    @Autowired
    private OrderLineMapper orderLineMapper;

    @Autowired
    private SystemOrgMapper systemOrgMapper;

    @Autowired
    private ISystemSequenceService sequenceService;

    public OrderBiddingServiceImpl() {
    }

    public OrderBiddingServiceImpl(RedisUtils redisUtils, ISystemSequenceService systemSequenceService, ExamineFeign examineFeign, PriceBiddingMapper priceBiddingMapper, SalesBiddingMapper salesBiddingMapper, OrderLineMapper orderLineMapper, SystemOrgServiceImpl systemOrgServiceImpl, ServerProperties serverProperties, SqlSessionFactory sqlSessionFactory) {
        this.redisUtils = redisUtils;
        this.priceBiddingMapper = priceBiddingMapper;
        this.salesBiddingMapper = salesBiddingMapper;
        this.orderLineMapper = orderLineMapper;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月24日 上午09:11:23
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : get
     * OrderBiddingDetailsVO
     *
     * @throws
     */
    /**
     * 根据ID获取订单竞价详情
     *
     * @param tenderNumber 竞价编号，用于查询特定的竞价详情
     * @return 返回OrderBiddingDetailsVO对象，包含竞价详细信息
     * @throws BusinessException 当ID为空或查询失败时抛出业务异常
     */
    @Override
    public OrderBiddingDetailsVO get(String tenderNumber) {
        // 检查传入的ID是否为空，为空则抛出异常
        if (tenderNumber == null) {
            throw new BusinessException("产品竞价编号不能为空");
        }

        // 根据ID查询数据库中的竞价信息
        LambdaQueryWrapper<SalesBiddingPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectOne(wrapper);
        // 检查查询结果是否为空，为空则抛出异常
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }

        // 创建OrderBiddingDetailsVO对象用于存储和返回竞价详情
        OrderBiddingDetailsVO result = new OrderBiddingDetailsVO();

        // 将salesBiddingPO对象的属性复制到结果对象中
        BeanUtils.copyProperties(salesBiddingPO, result);

        // 查询与当前竞价相关的元数据
        List<OrderBiddingPageListVO> metadata = this.baseMapper.metadata(salesBiddingPO.getTenderNumber());

        // 如果元数据为空，则不执行任何操作
        if (Collections.isEmpty(metadata)) {
        } else {
            for (OrderBiddingPageListVO metadatum : metadata) {
                metadatum.setTransactionPrice(metadatum.getLatestQuotation());
                metadatum.setTransactionQuantity(metadatum.getQuantity());
            }
            // 计算元数据中的交易数量总和，并设置到salesBiddingPO对象中
            BigDecimal mathSumBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getTransactionQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
            salesBiddingPO.setTransactionQuantityTotal(mathSumBigDecimal);
            // 找出元数据中的最高和最低交易价格，并设置到salesBiddingPO对象中
            BigDecimal maxBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getLatestQuotation).max((x1, x2) -> x1.compareTo(x2)).get();
            BigDecimal minBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getLatestQuotation).min((x1, x2) -> x1.compareTo(x2)).get();
            salesBiddingPO.setTransactionPriceMax(maxBigDecimal);
            salesBiddingPO.setTransactionPriceMin(minBigDecimal);
            // 统计创建订单的客户数量，并设置到salesBiddingPO对象中
            Integer i = this.baseMapper.countCreate(salesBiddingPO.getTenderNumber());
            salesBiddingPO.setTransactionCustomer(i.toString());
            // 查询参与创建订单的客户列表，并统计总数设置到salesBiddingPO对象中
            List<OrderBiddingPageListVO> orderBiddingPageListVOS = this.baseMapper.participationCreate(salesBiddingPO.getTenderNumber());
            Integer size = orderBiddingPageListVOS.size();
            salesBiddingPO.setTotalCustomer(size.toString());
            // 将元数据设置到结果对象中
            result.setListVO(metadata);
            // 设置结果对象中的竞价编号
            String orderBiddingNo = salesBiddingPO.getOrderBiddingNo();
            result.setOrderBiddingNo(orderBiddingNo);
        }
        //折线图对象
        LambdaQueryWrapper<PriceBiddingPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PriceBiddingPO::getTenderNumber, tenderNumber);
        List<PriceBiddingPO> selectList = priceBiddingMapper.selectList(queryWrapper);
        List<List<Object>> orderBiddingDataVOList = new ArrayList<>();
        for (PriceBiddingPO priceBiddingPO : selectList) {
            List<Object> objectArrayList = new ArrayList<>();
            objectArrayList.add(priceBiddingPO.getQuotationTime());
            objectArrayList.add(priceBiddingPO.getLatestQuotation().setScale(0, RoundingMode.DOWN));
            objectArrayList.add(priceBiddingPO.getCustomer());
            objectArrayList.add(priceBiddingPO.getQuantity().setScale(0, RoundingMode.DOWN));
            objectArrayList.add(priceBiddingPO.getCustomerId());
            orderBiddingDataVOList.add(objectArrayList);
        }
        // 将处理后的数据设置到结果对象中
        result.setData(orderBiddingDataVOList);
        // 设置结果对象中的开始时间
        Date startingTime = salesBiddingPO.getStartingTime();
        result.setStartingTime(startingTime);
        // 从Redis中获取并设置竞价模式、运输方式和配送方式的名称
        result.setBiddingMode(redisUtils.getDict("biddingMode_", result.getBiddingMode()));
        result.setShippingType(redisUtils.getDict("ysfs_", result.getShippingType()));
        result.setDeliveryMethod(redisUtils.getDict("psfs_", result.getDeliveryMethod()));
        BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        result.setSignUpEndTime(endTime);
        // 返回填充了竞价详情的结果对象
        return result;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月25日 上午09:11:23
     * 描述 : 根据主键ID修改结果确认
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : get
     * String
     *
     * @throws
     */
    @Override
    public String edit(Long id) {
        if (null == id) {
            throw new BusinessException("产品竞价ID不能为空");
        }
        LambdaUpdateWrapper<SalesBiddingPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SalesBiddingPO::getId, id);
        wrapper.set(SalesBiddingPO::getOrderBiddingNo, "已确认");
        int update = salesBiddingMapper.update(null, wrapper);
        if (update >= 1) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月9日 上午09:10:05
     * 描述 : 提交钉钉审核审核通过后操作
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : apply
     * String
     *
     * @param id 竞价ID
     * @return 审核结果信息
     * @throws BusinessException 当用户不存在或获取产品竞价失败时抛出
     */
    @Override
    @Transactional()
    public String createOrder(Long id) {

        // 根据ID查询竞价信息
        SalesBiddingPO salesBiddingPO = salesBiddingMapper.selectById(id);
        //检索订单行方式重复生成订单
        LambdaQueryWrapper<OrderLinePO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderLinePO::getListedNo, salesBiddingPO.getTenderNumber());
        List<OrderLinePO> orderLinePOS = orderLineMapper.selectList(lambdaQueryWrapper);
        //如果订单行中订单存在则直接返回
        if (!orderLinePOS.isEmpty()) {
            log.error("未找到与竞价编号对应的订单行记录,竞价编号为:{" + salesBiddingPO.getTenderNumber() + "}");
            return null;
        }

        //获取已中标的列表
        LambdaQueryWrapper<PriceBiddingPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceBiddingPO::getTenderNumber, salesBiddingPO.getTenderNumber());
        wrapper.eq(PriceBiddingPO::getWinBidStatus, "已中标");
        List<PriceBiddingPO> priceBiddingPOList = priceBiddingMapper.selectList(wrapper);

        for (PriceBiddingPO priceBiddingPO : priceBiddingPOList) {
            //生成订单
            //获取公司编码
            String companyNo = priceBiddingPO.getCompanyNo();
            //获取客户编码
            String customerId = priceBiddingPO.getCustomerId();

            LambdaQueryWrapper<SystemOrgPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SystemOrgPO::getOrgNo, companyNo);
            SystemOrgPO systemOrgPO = systemOrgMapper.selectOne(queryWrapper);
            String[] split = systemOrgPO.getOrgNoAbbreviation().split("_");
            String orgNoAbbreviation = split[0];
            // 根据公司编码生成不同的订单编号
            // 中煤蒙陕能源销售有限公司定制化订单编码规则【MSGS-MHXS-QRD-年-订单序号】
            String orderNoKey = ""; // 订单编码KEY
            if ("000002".equals(companyNo)) {
                orderNoKey = orgNoAbbreviation + "-" + "MHXS-QRD" + LocalDate.now().getYear()
                        + "-";
                // 中煤销售太原有限公司定制化订单编码规则[DD-TYMHG-客户编码-年月日-4位序列号]
            } else if ("000008".equals(companyNo) || "00009".equals(companyNo)) {
                orderNoKey = "DD-TYMHG-" + customerId + "-" + LocalDate.now().toString().replaceAll("-", "") + "-";
                // 其他公司
            } else {
                orderNoKey = "DD-" + orgNoAbbreviation + "-" + customerId + "-"
                        + LocalDate.now().toString().replaceAll("-", "") + "-";
            }
            String orderNo = sequenceService.get(orderNoKey, 6);

            String orderLineNo = sequenceService.get(orderNo + "-", 4);

            // 创建订单明细对象并设置属性
            OrderLinePO orderLinePO = new OrderLinePO();
            orderLinePO.setOrderNo(orderNo);
            orderLinePO.setOrderLineNo(orderLineNo);
            orderLinePO.setListedNo(priceBiddingPO.getTenderNumber());
            orderLinePO.setOrderType("JJ");
            orderLinePO.setOrderSubmissionPrice(priceBiddingPO.getLatestQuotation());
            orderLinePO.setOrderSubmissionQuantity(priceBiddingPO.getQuantity());
            orderLinePO.setTransactionPrice(priceBiddingPO.getLatestQuotation());
            orderLinePO.setTurnoverQuantity(priceBiddingPO.getQuantity());
            orderLinePO.setCustomerNo(priceBiddingPO.getCustomerId());
            orderLinePO.setCustomerName(priceBiddingPO.getCustomer());
            orderLinePO.setCompanyNo(priceBiddingPO.getCompanyNo());
            orderLinePO.setCompanyName(priceBiddingPO.getCompanyName());
            orderLinePO.setStatus("待审核");
            // 插入订单明细记录
            int insert = orderLineMapper.insert(orderLinePO);
            if (insert < 1) {
                log.error("创建订单失败!");
                throw new BusinessException("创建订单失败");
            }
        }
        return "创建订单成功";
    }


    @Override
    public OrderBiddingSubmitVO submit(Long id) {
        // 检查传入的ID是否为空，为空则抛出异常
        if (id == null) {
            throw new BusinessException("产品竞价id不能为空");
        }
        // 根据ID查询数据库中的竞价信息
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectById(id);
        // 检查查询结果是否为空，为空则抛出异常
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }
        // 创建OrderBiddingDetailsVO对象用于存储和返回竞价详情
        OrderBiddingSubmitVO result = new OrderBiddingSubmitVO();
        // 查询与当前竞价相关的元数据
        List<OrderBiddingPageListVO> metadata = this.baseMapper.metadata(salesBiddingPO.getTenderNumber());
        for (OrderBiddingPageListVO metadatum : metadata) {
            metadatum.setTransactionPrice(metadatum.getLatestQuotation());
            metadatum.setTransactionQuantity(metadatum.getQuantity());
        }
        // 计算元数据中的交易数量总和，并设置到salesBiddingPO对象中
        BigDecimal mathSumBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getTransactionQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
        salesBiddingPO.setTransactionQuantityTotal(mathSumBigDecimal);
        // 找出元数据中的最高和最低交易价格，并设置到salesBiddingPO对象中
        BigDecimal maxBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getLatestQuotation).max((x1, x2) -> x1.compareTo(x2)).get();
        BigDecimal minBigDecimal = metadata.stream().map(OrderBiddingPageListVO::getLatestQuotation).min((x1, x2) -> x1.compareTo(x2)).get();
        salesBiddingPO.setTransactionPriceMax(maxBigDecimal);
        salesBiddingPO.setTransactionPriceMin(minBigDecimal);
        // 统计创建订单的客户数量，并设置到salesBiddingPO对象中
        Integer i = this.baseMapper.countCreate(salesBiddingPO.getTenderNumber());
        salesBiddingPO.setTransactionCustomer(i.toString());
        // 查询参与创建订单的客户列表，并统计总数设置到salesBiddingPO对象中
        List<OrderBiddingPageListVO> orderBiddingPageListVOS = this.baseMapper.participationCreate(salesBiddingPO.getTenderNumber());
        Integer size = orderBiddingPageListVOS.size();
        salesBiddingPO.setTotalCustomer(size.toString());

        // 将salesBiddingPO对象的属性复制到结果对象中
        BeanUtils.copyProperties(salesBiddingPO, result);

        //设置竞价结果
        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (OrderBiddingPageListVO metadatum : metadata) {
            //计算总价
            BigDecimal totalPrice = metadatum.getLatestQuotation().multiply(metadatum.getQuantity()).setScale(0, RoundingMode.DOWN);
            String description = "竞价排名第" + atomicInteger.incrementAndGet() + "的" + metadatum.getCustomer() + "最终竞价获得" + metadatum.getQuantity() + "吨的本产品成交价为" + totalPrice + "; ";
            stringBuilder.append(description);
        }
        result.setBiddingDescription(stringBuilder.toString());
        // 设置结果对象中的开始时间
        Date startingTime = salesBiddingPO.getStartingTime();
        result.setStartingTime(startingTime);
        // 从Redis中获取并设置竞价模式、运输方式和配送方式的名称
        result.setBiddingMode(redisUtils.getDict("biddingMode_", result.getBiddingMode()));
        result.setShippingType(redisUtils.getDict("ysfs_", result.getShippingType()));
        result.setDeliveryMethod(redisUtils.getDict("psfs_", result.getDeliveryMethod()));
        BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        result.setSignUpEndTime(endTime);
        // 返回填充了竞价详情的结果对象
        return result;
    }


}
