package com.ccit.area.sales.service.marketing.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.marketing.PriceBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingQuoteDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;
import com.ccit.area.sales.dao.mapper.marketing.PriceBiddingMapper;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;
import com.ccit.area.sales.service.marketing.IPriceBiddingService;
import com.ccit.area.sales.service.websocket.WebSocketServiceImpl;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.ip.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PriceBiddingServiceImpl extends ServiceImpl<PriceBiddingMapper, PriceBiddingPO> implements IPriceBiddingService {

    @Autowired
    private WebSocketServiceImpl websocketService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月20日 下午03:23:23
     * 描述 : 客户报价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : quote
     * ResponseVO<String>
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String quote(ApplyBiddingQuoteDTO biddingQuote) {
        // 获取招标编号
        String tenderNumber = biddingQuote.getTenderNumber();
        //执行分布式锁
        String lockKey = "bidding_lock:" + tenderNumber;
        boolean isLocked = redisUtils.setNX(lockKey, tenderNumber, 5L, TimeUnit.SECONDS);
        if (!isLocked) {
            return "当前有其他用户正在出价，请稍后再试!";
        }
        try {
            // 获取当前用户账号
            String userName = biddingQuote.getUserName();
            // 获取当前用户名
            String realName = biddingQuote.getRealName();
            // 获取客户ID
            String customerId = biddingQuote.getCustomerId();
            // 获取当前HTTP请求
            HttpServletRequest request = ServletUtil.getRequest();
            // 获取客户IP地址
            String customerIp = IpUtil.getIpAddr(request);
            //获取报价时间
            Date quotationTime = stringToDate(biddingQuote.getQuotationTime());
            //获取公司编码
            String companyNo = biddingQuote.getCompanyNo();
            String companyName = biddingQuote.getCompanyName();
            // 根据招标编号查询招标信息
            SalesBiddingPageListVO biddingInfo = this.baseMapper.selectBidding(tenderNumber);
            String materialNumber = biddingInfo.getMaterielNo();
            String materialName = biddingInfo.getMaterielName();
            // 获取招标模式
            String biddingMode = biddingInfo.getBiddingMode();
            // 将数量转换为BigDecimal类型
            BigDecimal quantity = new BigDecimal(biddingInfo.getQuantity());
            // 获取客户名称
            String customerName = biddingQuote.getCustomer();
            // 获取竞价开始时间
            Date startTime = biddingInfo.getStartingTime();
            // 获取竞价时长
            BigDecimal biddingDuration = biddingInfo.getBiddingDuration();
            // 计算招标结束时间
            // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
            BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
            // 计算竞价结束时间
            Date endTime = new Date(startTime.getTime() + biddingDurationMillis.longValue());
            // 检查当前时间是否在招标结束时间之前
            if (quotationTime.before(endTime) || quotationTime.equals(endTime)) {
                // 获取竞拍底价
                BigDecimal lowestPrice = biddingInfo.getLowPrice();
                // 获取客户最新报价
                BigDecimal latestQuotation = biddingQuote.getLatestQuotation();
                // 比较最新报价和最低价格
                if (latestQuotation.compareTo(lowestPrice) < 0) {
                    // 报价无效，抛出异常
                    throw new BusinessException("出价不能小于竞价底价,请重新报价！");
                }
                // 获取报价数量
                BigDecimal bidQuantity = biddingQuote.getQuantity();
                if (bidQuantity.compareTo(quantity) > 0) {
                    // 报价无效，抛出异常
                    throw new BusinessException("出价数量不能大于竞价数量,请重新报价！");
                }
                // 根据招标模式处理报价逻辑
                if (biddingMode.equals("YSJJ") || biddingMode.equals("KXLYSJJ")) {
                    // 获取当前招标编号的最大出价者
                    String maxBidder = this.baseMapper.selectMax(tenderNumber);
                    //如果当前竞拍未出价过则直接新增出价表.
                    if (maxBidder.equals("0")) {
                        this.baseMapper.addApplyBidding(tenderNumber, customerId, customerName, customerIp, materialNumber, materialName, lowestPrice, latestQuotation, quotationTime, bidQuantity, BigDecimal.ZERO, userName, realName, userName, realName, companyNo, companyName);
                    } else {
                        // 获取所有报价
                        List<BigDecimal> allQuotes = this.baseMapper.selectQuote(tenderNumber);
                        // 获取最高出价
                        BigDecimal highestBid = Collections.max(allQuotes).setScale(0, RoundingMode.DOWN);
                        // 获取最小加价
                        BigDecimal minMarkup = biddingInfo.getMinMarkup();
                        // 计算最高出价加最小加价
                        BigDecimal highestBidWithMarkup = highestBid.add(minMarkup);
                        // 比较最新报价和最高出价加最小加价
                        if (latestQuotation.compareTo(highestBidWithMarkup) < 0) {
                            throw new BusinessException("当前出价小于最小出价幅度,请重新出价!");
                        }
                        // 计算加价
                        BigDecimal markupAmount = latestQuotation.subtract(highestBid);
                        // 插入新的出价记录
                        this.baseMapper.addApplyBidding(tenderNumber, customerId, customerName, customerIp, materialNumber, materialName, lowestPrice, latestQuotation, quotationTime, bidQuantity, markupAmount, userName, realName, userName, realName, companyNo, companyName);
                    }
                    // 如果是延时竞价模式，发送WebSocket消息
                    try {
                        String topic = "/topic/bidding/" + tenderNumber;
                        String message = "招标编号为" + tenderNumber + "的竞价,客户为" + customerName + "的出价" + biddingQuote.getLatestQuotation() + "元，请注意!";
                        websocketService.sendToTopic(topic, message);
                        log.info("WebSocket消息发送成功: topic={}, message={}", topic, message);
                    } catch (Exception e) {
                        log.error("发送消息失败!");
                    }

                    // 获取距离竞价结束时间 (单位: 秒)
                    BigDecimal distanceEndDuration = biddingInfo.getDistanceEndDuration();
                    // 计算距离竞价结束时间的毫秒数
                    long distanceEndDurationMillis = distanceEndDuration.multiply(BigDecimal.valueOf(1000)).longValue();
                    // 计算延迟开始时间 (精确到毫秒)
                    Date endDistanceTime = new Date(endTime.getTime() - distanceEndDurationMillis);

                    // 检查当前报价时间是否在竞价结束时间之前或等于结束时间
                    if (quotationTime.compareTo(endDistanceTime) >= 0 && quotationTime.compareTo(endTime) <= 0) {
                        // 报价时间有效
                        //竞价延迟时长(单位: 秒)
                        BigDecimal delayDuration = biddingInfo.getDelayDuration();
                        // 竞价延迟时长（单位: 秒），转换为毫秒
                        BigDecimal delayDurationMillis = delayDuration.multiply(BigDecimal.valueOf(1000));
                        // 计算出增加延迟时间后的竞价时长（单位：毫秒）
                        BigDecimal updatedBiddingDurationMillis = biddingDurationMillis.add(delayDurationMillis);
                        // 转换回 **分钟（带小数）**
                        BigDecimal updatedBiddingDuration = updatedBiddingDurationMillis.divide(BigDecimal.valueOf(60000), 4, RoundingMode.HALF_UP);
                        //竞价延长次数
                        int delayFrequency = Integer.parseInt(this.baseMapper.selectDelayFrequency(tenderNumber));
                        int updatedDelayFrequency = delayFrequency + 1;
                        this.baseMapper.updateBidding(updatedBiddingDuration, updatedDelayFrequency, tenderNumber);
                    }
                    return "报价成功";
                } else {
                    // 插入新的招标申请记录
                    this.baseMapper.addApplyBidding(tenderNumber, customerId, customerName, customerIp, materialNumber, materialName, lowestPrice, latestQuotation, quotationTime, bidQuantity, BigDecimal.ZERO, userName, realName, userName, realName, companyNo, companyName);
                    return "报价成功";
                }
            } else {
                // 招标时间已结束，抛出异常
                throw new BusinessException("竞价时间已结束!");
            }
        } finally {
            redisUtils.remove(lockKey);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String updateStatus(ApplyBiddingSubmitDTO entity) {
        int result = this.baseMapper.updateStatus(entity);
        if (result > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    public Date stringToDate(String dateString) {
        Date parse = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parse = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new BusinessException("转换时间失败!");
        }
        return parse;
    }

}
