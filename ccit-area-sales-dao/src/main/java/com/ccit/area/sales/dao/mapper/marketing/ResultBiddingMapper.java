package com.ccit.area.sales.dao.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingResultPageListDTO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingResultPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
  * 描述 : “竞价结果”接口类
  * 创建人 : tb
  * 创建时间 : 2024年09月18日 下午10:43:30
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.mapper.bidding
  * 类名 : ResultBiddingMapper
 */
public interface ResultBiddingMapper extends BaseMapper<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月18日 上午08:49:35
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectPageList
     *  List<SalesAreaPageListVO>
     *  @throws
     */
    List<SalesBiddingResultPageListVO> selectPageList(@Param(value ="param") SalesBiddingResultPageListDTO  param);
}