package com.ccit.area.sales.service.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsPO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsSaveDTO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsSubmitVO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsVO;

import java.util.List;
import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_order_statistics(接单统计表)】的数据库操作Service
 * @createDate 2025-02-24 16:55:41
 */
public interface OrderStatisticsService extends IService<OrderStatisticsPO> {

    Page<OrderStatisticsVO> selectPageList(Map<String, Object> param);

    OrderStatisticsVO get(String takeOrderNo);

    String delete(List<Long> ids);

    String saveOrderStatistics(List<OrderStatisticsSaveDTO> orderStatisticsSaveDTO);

    OrderStatisticsSubmitVO submit(Long id);

    String updateStatus(Long id);
}
