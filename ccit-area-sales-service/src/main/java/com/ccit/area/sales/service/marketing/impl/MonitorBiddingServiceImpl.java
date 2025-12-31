package com.ccit.area.sales.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingAddDTO;
import com.ccit.area.sales.dao.mapper.marketing.MonitorBiddingMapper;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingPageListVO;
import com.ccit.area.sales.dao.vo.marketing.MonitorBiddingVO;
import com.ccit.area.sales.dao.vo.marketing.OrderBiddingPageListVO;
import com.ccit.area.sales.service.marketing.IMonitorBiddingService;
import com.ccit.common.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  * 描述 : “竞价监控”服务实现类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年10月9日 上午11:00:24
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding.impl
 *  * 类名 : MonitorBiddingServiceImpl
 */
@Service
@Slf4j
public class MonitorBiddingServiceImpl extends ServiceImpl<MonitorBiddingMapper, SalesBiddingPO> implements IMonitorBiddingService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 根据招标编号获取监控竞价详情
     *
     * @param tenderNumber 招标编号，用于查询竞价详情
     * @return 返回监控竞价详情对象，包含竞价相关信息
     * @throws BusinessException 当招标编号为空或查询不到竞价信息时抛出业务异常
     */
    @Override
    public MonitorBiddingDetailsVO get(String tenderNumber) {
        // 检查招标编号是否为空，为空则抛出异常
        if (null == tenderNumber) {
            throw new BusinessException("招标编号不能为空");
        }
        // 创建查询条件，根据招标编号查询销售竞价信息
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectOne(wrapper);
        // 如果查询不到竞价信息，抛出异常
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }
        // 初始化监控竞价详情对象
        MonitorBiddingDetailsVO monitorBiddingDetailsVO = new MonitorBiddingDetailsVO();
        // 获取当前服务器时间并格式化
        Clock serverClock = Clock.systemUTC();
        Instant serverTime = Instant.now(serverClock);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        String formattedDate = serverTime.atZone(ZoneId.systemDefault()).format(formatter);
        monitorBiddingDetailsVO.setLocalhostTime(formattedDate);

        // 初始化存储竞价信息的列表
        List<List<String>> dataList = new ArrayList<>();
        // 查询并添加竞价详情信息
        List<OrderBiddingPageListVO> orderBiddingPageListVOS1 = this.baseMapper.bidDetails(tenderNumber);
        if (!orderBiddingPageListVOS1.isEmpty()) {
            orderBiddingPageListVOS1.forEach(t -> {
                List<String> list = new ArrayList<>();
                list.add(t.getQuotationTime());
                list.add(t.getLatestQuotation().setScale(0, RoundingMode.DOWN).toString());
                list.add(t.getCustomer());
                dataList.add(list);
            });
        }
        // 将竞价信息列表添加到详情对象中
        monitorBiddingDetailsVO.setData(dataList);

        // 将销售竞价信息复制到监控竞价详情对象中
        BeanUtils.copyProperties(salesBiddingPO, monitorBiddingDetailsVO);
        // 从Redis中获取并设置竞价模式、运输方式和配送方式的名称
        monitorBiddingDetailsVO.setBiddingModeName(redisUtils.getDict("biddingMode_", salesBiddingPO.getBiddingMode()));
        monitorBiddingDetailsVO.setShippingTypeName(redisUtils.getDict("ysfs_", salesBiddingPO.getShippingType()));
        monitorBiddingDetailsVO.setDeliveryMethodName(redisUtils.getDict("psfs_", salesBiddingPO.getDeliveryMethod()));
        // 查询并设置地址和配送仓库的名称
        monitorBiddingDetailsVO.setDeliveryPlaceNo(salesBiddingPO.getDeliveryPlaceNo());
        monitorBiddingDetailsVO.setDeliveryPlaceName(salesBiddingPO.getDeliveryPlaceName());
        monitorBiddingDetailsVO.setDeliveryPlaceClassNo(salesBiddingPO.getDeliveryPlaceClassNo());
        monitorBiddingDetailsVO.setDeliveryPlaceClassName(salesBiddingPO.getDeliveryPlaceClassName());
        monitorBiddingDetailsVO.setDepotNo(salesBiddingPO.getDepotNo());
        monitorBiddingDetailsVO.setDepotName(salesBiddingPO.getDepotName());
        monitorBiddingDetailsVO.setProductDescription(salesBiddingPO.getRemarks());

        // 查询并设置与创建相关的竞价页面列表信息
        List<MonitorBiddingPageListVO> monitorBiddingPageListVOS = this.baseMapper.metaCreate(tenderNumber, null);
        if (!monitorBiddingPageListVOS.isEmpty()) {
            monitorBiddingPageListVOS.forEach(t -> {
                if (t.getLatestQuotation() != null || t.getQuantity() != null) {
                    t.setLatestQuotation(t.getLatestQuotation().setScale(0, RoundingMode.DOWN));
                    t.setQuantity(t.getQuantity().setScale(0, RoundingMode.DOWN));
                    t.setInitialQuotation(t.getInitialQuotation().setScale(0, RoundingMode.DOWN));
                }
            });
        }

        // 计算竞标结束时间
        Date startingTime = salesBiddingPO.getStartingTime();
        BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        monitorBiddingDetailsVO.setEndTime(endTime);

        monitorBiddingDetailsVO.setListVO(monitorBiddingPageListVOS);
        // 返回监控竞价详情对象
        return monitorBiddingDetailsVO;
    }

    /**
     * 处理竞标添加申请
     * <p>
     * 此方法主要负责处理竞标添加请求，将申请信息转换为竞标详情视图对象
     * 它首先验证竞标编号和客户ID，然后根据竞标模式获取相关数据，
     * 并计算竞标结束时间最后，它将销售竞标数据复制到视图对象中，
     * 并设置其他必要属性
     *
     * @param entity 申请竞标添加的数据传输对象
     * @return 竞标详情视图对象，包含处理后的竞标信息
     * @throws BusinessException 当没有公司报名通过、报名审核未通过、招标编号或客户账号为空，或获取产品竞价失败时
     */
    @Override
    public MonitorBiddingDetailsVO process(ApplyBiddingAddDTO entity) {
        // 加密招标编号
        // 验证招标编号和客户账号是否为空
        String tenderNumber = entity.getTenderNumber();
        if (null == tenderNumber) {
            throw new BusinessException("招标编号不能为空");
        }

        Object redisValue = redisUtils.get(tenderNumber);
        if (redisValue == null) {
            throw new BusinessException("没有公司报名通过，3秒后退出竞价！");
        }
        List<String> stringList = JSON.parseArray(AESUtil.decrypt(redisValue.toString(), GlobalConstants.AES_PARAM_KEY), String.class);

        // 验证客户ID是否在通过审核的公司列表中
        boolean found = false;
        for (String s : stringList) {
            if (entity.getCustomerId().equals(s)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new BusinessException("报名审核未通过，3秒后退出竞价！");
        }

        String customerId = entity.getCustomerId();
        if (null == customerId) {
            throw new BusinessException("客户账号不能为空");
        }
        // 查询销售竞标信息
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.eq(SalesBiddingPO::getTenderNumber, entity.getTenderNumber());
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectOne(wrapper);
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }
        // 初始化监控竞标详情视图对象
        MonitorBiddingDetailsVO monitorBiddingDetailsVO = new MonitorBiddingDetailsVO();
        // 设置服务器时间
        Clock serverClock = Clock.systemUTC();
        Instant serverTime = Instant.now(serverClock);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        String formattedDate = serverTime.atZone(ZoneId.systemDefault()).format(formatter);
        monitorBiddingDetailsVO.setLocalhostTime(formattedDate);
        // 准备竞标数据
        List<List<String>> dataList = new ArrayList<>();
        // 根据竞标模式获取详情
        String biddingMode = salesBiddingPO.getBiddingMode();
        if (biddingMode.equals("YSJJ") || biddingMode.equals("KXLYSJJ")) {
            List<OrderBiddingPageListVO> orderBiddingPageListVOS1 = this.baseMapper.bidDetails(tenderNumber);
            if (!orderBiddingPageListVOS1.isEmpty()) {
                orderBiddingPageListVOS1.forEach(t -> {
                    List<String> list = new ArrayList<>();
                    list.add(t.getQuotationTime());
                    list.add(t.getLatestQuotation().setScale(0, RoundingMode.DOWN).toString());
                    list.add(t.getCustomer());
                    dataList.add(list);
                });
            }
        } else {
            List<OrderBiddingPageListVO> orderBiddingPageListVOS = this.baseMapper.bidDetailsJJ(tenderNumber, customerId);
            if (!orderBiddingPageListVOS.isEmpty()) {
                orderBiddingPageListVOS.forEach(t -> {
                    List<String> list = new ArrayList<>();
                    list.add(t.getQuotationTime());
                    list.add(t.getLatestQuotation().setScale(0, RoundingMode.DOWN).toString());
                    list.add(t.getCustomer());
                    dataList.add(list);
                });
            }
        }
        // 设置竞标数据到视图对象
        monitorBiddingDetailsVO.setData(dataList);

        BeanUtils.copyProperties(salesBiddingPO, monitorBiddingDetailsVO);
        // 设置其他必要属性
        monitorBiddingDetailsVO.setBiddingModeName(redisUtils.getDict("biddingMode_", salesBiddingPO.getBiddingMode()));
        monitorBiddingDetailsVO.setShippingTypeName(redisUtils.getDict("ysfs_", salesBiddingPO.getShippingType()));
        monitorBiddingDetailsVO.setDeliveryMethodName(redisUtils.getDict("psfs_", salesBiddingPO.getDeliveryMethod()));

        monitorBiddingDetailsVO.setDeliveryPlaceNo(salesBiddingPO.getDeliveryPlaceNo());
        monitorBiddingDetailsVO.setDeliveryPlaceName(salesBiddingPO.getDeliveryPlaceName());
        monitorBiddingDetailsVO.setDeliveryPlaceClassNo(salesBiddingPO.getDeliveryPlaceClassNo());
        monitorBiddingDetailsVO.setDeliveryPlaceClassName(salesBiddingPO.getDeliveryPlaceClassName());
        monitorBiddingDetailsVO.setDepotNo(salesBiddingPO.getDepotNo());
        monitorBiddingDetailsVO.setDepotName(salesBiddingPO.getDepotName());
        monitorBiddingDetailsVO.setProductDescription(salesBiddingPO.getRemarks());


        if (biddingMode.equals("YSJJ") || biddingMode.equals("KXLYSJJ")) {
            List<MonitorBiddingPageListVO> monitorBiddingPageListVOS = this.baseMapper.bidList(tenderNumber);
            if (!monitorBiddingPageListVOS.isEmpty()) {
                monitorBiddingPageListVOS.forEach(t -> {
                        t.setLatestQuotation(t.getLatestQuotation().setScale(0, RoundingMode.DOWN));
                        t.setQuantity(t.getQuantity().setScale(0, RoundingMode.DOWN));
                        t.setInitialQuotation(t.getInitialQuotation().setScale(0, RoundingMode.DOWN));
                });
            }
            // 计算竞标结束时间
            Date startingTime = salesBiddingPO.getStartingTime();
            BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
            // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
            BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
            // 计算竞价结束时间
            Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
            monitorBiddingDetailsVO.setEndTime(endTime);
            monitorBiddingDetailsVO.setListVO(monitorBiddingPageListVOS);
        } else {
            List<MonitorBiddingPageListVO> monitorBiddingPageListVOS = this.baseMapper.metaCreate(tenderNumber, customerId);
            if (!monitorBiddingPageListVOS.isEmpty()) {
                monitorBiddingPageListVOS.forEach(t -> {
                        t.setLatestQuotation(t.getLatestQuotation().setScale(0, RoundingMode.DOWN));
                        t.setQuantity(t.getQuantity().setScale(0, RoundingMode.DOWN));
                        t.setInitialQuotation(t.getInitialQuotation().setScale(0, RoundingMode.DOWN));
                });
            }

            // 计算竞标结束时间
            Date startingTime = salesBiddingPO.getStartingTime();
            BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
            // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
            BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
            // 计算竞价结束时间
            Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
            monitorBiddingDetailsVO.setEndTime(endTime);
            monitorBiddingDetailsVO.setListVO(monitorBiddingPageListVOS);
        }
        monitorBiddingDetailsVO.setMinBidders(salesBiddingPO.getMinBidders());
        return monitorBiddingDetailsVO;
    }

    @Override
    public MonitorBiddingVO supervisoryControl(String tenderNumber) {

        // 查询销售竞标信息
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectOne(wrapper);
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }
        // 初始化监控竞标详情视图对象
        MonitorBiddingVO monitorBiddingVO = new MonitorBiddingVO();
        // 设置服务器时间
        Clock serverClock = Clock.systemUTC();
        Instant serverTime = Instant.now(serverClock);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        String formattedDate = serverTime.atZone(ZoneId.systemDefault()).format(formatter);
        monitorBiddingVO.setLocalhostTime(formattedDate);
        // 准备竞标数据
        List<List<String>> dataList = new ArrayList<>();
        // 根据竞标模式获取详情
        List<OrderBiddingPageListVO> orderBiddingPageListVOS = this.baseMapper.bidDetails(tenderNumber);
        if (!orderBiddingPageListVOS.isEmpty()) {
            orderBiddingPageListVOS.forEach(t -> {
                List<String> list = new ArrayList<>();
                list.add(t.getQuotationTime());
                list.add(t.getLatestQuotation().setScale(0, RoundingMode.DOWN).toString());
                list.add(t.getCustomer());
                dataList.add(list);
            });
        }
        // 设置竞标数据到视图对象
        monitorBiddingVO.setData(dataList);

        BeanUtils.copyProperties(salesBiddingPO, monitorBiddingVO);
        // 设置其他必要属性
        monitorBiddingVO.setBiddingModeName(redisUtils.getDict("biddingMode_", salesBiddingPO.getBiddingMode()));
        monitorBiddingVO.setShippingTypeName(redisUtils.getDict("ysfs_", salesBiddingPO.getShippingType()));
        monitorBiddingVO.setDeliveryMethodName(redisUtils.getDict("psfs_", salesBiddingPO.getDeliveryMethod()));
        monitorBiddingVO.setDeliveryPlaceNo(salesBiddingPO.getDeliveryPlaceNo());
        monitorBiddingVO.setDeliveryPlaceName(salesBiddingPO.getDeliveryPlaceName());
        monitorBiddingVO.setDeliveryPlaceClassNo(salesBiddingPO.getDeliveryPlaceClassNo());
        monitorBiddingVO.setDeliveryPlaceClassName(salesBiddingPO.getDeliveryPlaceClassName());
        monitorBiddingVO.setDepotNo(salesBiddingPO.getDepotNo());
        monitorBiddingVO.setDepotName(salesBiddingPO.getDepotName());
        monitorBiddingVO.setProductDescription(salesBiddingPO.getRemarks());

        List<MonitorBiddingPageListVO> monitorBiddingPageListVOS = this.baseMapper.bidList(tenderNumber);
        if (!monitorBiddingPageListVOS.isEmpty()) {
            monitorBiddingPageListVOS.forEach(t -> {
                if (t.getLatestQuotation() != null || t.getQuantity() != null) {
                    t.setLatestQuotation(t.getLatestQuotation().setScale(0, RoundingMode.DOWN));
                    t.setQuantity(t.getQuantity().setScale(0, RoundingMode.DOWN));
                    t.setInitialQuotation(t.getInitialQuotation().setScale(0, RoundingMode.DOWN));
                }
            });
        }

        // 计算竞标结束时间
        Date startingTime = salesBiddingPO.getStartingTime();
        BigDecimal biddingDuration = salesBiddingPO.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        monitorBiddingVO.setEndTime(endTime);
        monitorBiddingVO.setListVO(monitorBiddingPageListVOS);
        monitorBiddingVO.setMinBidders(salesBiddingPO.getMinBidders());

        monitorBiddingVO.setCompaniesList(this.baseMapper.getCompaniesList(tenderNumber));
        return monitorBiddingVO;
    }
}
