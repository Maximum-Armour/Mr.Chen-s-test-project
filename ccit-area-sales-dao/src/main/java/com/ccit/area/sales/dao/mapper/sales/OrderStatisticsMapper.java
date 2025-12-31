package com.ccit.area.sales.dao.mapper.sales;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsPO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_order_statistics(接单统计表)】的数据库操作Mapper
* @createDate 2025-02-24 16:55:41
* @Entity com.ccit.area.sales.dao.domain.sales.TOrderStatistics
*/
public interface OrderStatisticsMapper extends BaseMapper<OrderStatisticsPO> {

    List<OrderStatisticsVO> selectPageList(Page<OrderStatisticsVO> pages,
                                           @Param(value = "param") Map<String, Object> param);

   Integer updateStatus(String orderLineNo);
}




