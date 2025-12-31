package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.marketing.ApplyBiddingPO;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingEndorseVO;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingPageListVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
  * 描述 : “竞价审核”接口类
  * 创建人 : tb
  * 创建时间 : 2024年09月12日 下午3:43:30
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.mapper.bidding
  * 类名 : ApplyBiddingMapper
 */
public interface ApplyBiddingMapper extends BaseMapper<ApplyBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午3:49:35
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectPageList
     *  List<ApplyBiddingPageListVO>
     *  @throws
     */
    List<ApplyBiddingPageListVO> selectPageList(Page<ApplyBiddingPageListVO> pages,
                                                @Param(value = "param") Map<String, Object> param);


    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 下午3:49:35
     * 描述 : 根据id查询ParentId
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectPI
     *  ApplyBiddingDetailsVO
     *  @throws
     */
    ApplyBiddingDetailsVO selectPI(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月17日 下午3:49:35
     * 描述 : 根据报价查询客户
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectC
     *  ApplyBiddingDetailsVO
     *  @throws
     */
    ApplyBiddingDetailsVO selectC(int latestQuotation);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 下午3:49:35
     * 描述 : 根据id查询数据
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectI
     *  List<ApplyBiddingDetailsVO>
     *  @throws
     */
    List<ApplyBiddingDetailsVO> selectI(String tenderNumber);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 下午3:49:35
     * 描述 : 根据id修改竞价说明
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : updateBdY
     *  void
     *  @throws
     */
    void updateBdY(ApplyBiddingDetailsVO parentId);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 下午3:49:35
     * 描述 : 根据id修改竞价说明
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : updateBdW
     *  void
     *  @throws
     */
    void updateBdW(ApplyBiddingDetailsVO parentId);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月22日 下午3:49:35
     * 描述 : 竞价报名
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : addApplyBidding
     *  void
     *  @throws
     */
    void addApplyBidding(String tenderNumber, String  customerId, String customer, String customerIp, String materielNo,String materielName, String biddingMode,String status,String companyNo,String companyName,String createBy,String createByName);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月22日 下午3:49:35
     * 描述 : 竞价报名个数
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectCount
     *  Long
     *  @throws
     */
    Long selectCount(String tenderNumber, Date signUpTime, Date signEndTime);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月27日 下午3:49:35
     * 描述 : 最新报价
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectLatestQuotation
     *  BigDecimal
     *  @throws
     */
    BigDecimal selectLatestQuotation(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月27日 下午3:49:35
     * 描述 : 最新报价
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectLatestQuotation
     *  BigDecimal
     *  @throws
     */
    BigDecimal selectQuantity(Long id);


    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 下午3:49:35
     * 描述 : 客户审核通过
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectStatus
     *  List<ApplyBiddingEndorseVO>
     *  @throws
     */
    ApplyBiddingEndorseVO selectStatus(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 下午3:49:35
     * 描述 : 报名校验
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectCustomerIp
     *  Long
     *  @throws
     */
    Long selectCustomerIp(String tenderNumber, String customerIp);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月27日 下午3:49:35
     * 描述 : 报名校验
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectCustomerId
     *  Long
     *  @throws
     */
    Long selectCustomerId(String tenderNumber, String customerId);

    /**
     * 获取报名通过人数
     * @param tenderNumber
     * @return
     */
    int getSignUpQuantity(String tenderNumber);

    int batchUpdateApprovalStatus(String tenderNumbe);
}
