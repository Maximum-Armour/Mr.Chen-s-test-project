package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.marketing.PriceBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface PriceBiddingMapper extends BaseMapper<PriceBiddingPO> {
    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月22日 下午3:49:35
     * 描述 : 竞价报名
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : addApplyBidding
     * void
     *
     * @throws
     */
    void addApplyBidding(String tenderNumber, String customerId, String customer, String customerIp, String materielNo, String materielName, BigDecimal initialQuotation, BigDecimal latestQuotation, Date quotationTime, BigDecimal quantity, BigDecimal markUp, String createBy, String createByName, String updateBy, String updateByName,String companyNo,String companyName);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月19日 下午3:49:35
     * 描述 : 根据招标编号获取信息
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectBidding
     * SalesBiddingPageListVO
     *
     * @throws
     */
    SalesBiddingPageListVO selectBidding(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月23日 下午3:49:35
     * 描述 : 获取最大出价
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectMax
     * String
     *
     * @throws
     */
    String selectMax(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月23日 下午3:49:35
     * 描述 : 获取出价
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectQuote
     * List
     *
     * @throws
     */
    List<BigDecimal> selectQuote(String tenderNumber);


    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月26日 下午3:49:35
     * 描述 : 竞价延时次数
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectDelayFrequency
     * String
     *
     * @throws
     */
    String selectDelayFrequency(String tenderNumber);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月26日 下午3:49:35
     * 描述 : 更新竞价信息
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : updateBidding
     * void
     *
     * @throws
     */
    void updateBidding(BigDecimal biddingDuration, int delayFrequency, String tenderNumber);

    int updateStatus(ApplyBiddingSubmitDTO entity);
}
