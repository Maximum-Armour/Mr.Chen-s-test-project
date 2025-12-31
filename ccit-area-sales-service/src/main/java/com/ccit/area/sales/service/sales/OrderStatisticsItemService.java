package com.ccit.area.sales.service.sales;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsItemPO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsItemUpdateDTO;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsItemVO;

import java.util.List;

/**
 * @author Baishangqianxue
 * @description 针对表【t_order_statistics_item(接单统计明细表)】的数据库操作Service
 * @createDate 2025-02-24 16:55:48
 */
public interface OrderStatisticsItemService extends IService<OrderStatisticsItemPO> {

    List<OrderStatisticsItemVO> getItem(String takeOrderNo);

    String deleteItem(List<Long> ids);

    byte[] deriveExcel();

    String updateItem(List<OrderStatisticsItemUpdateDTO> entity);


}
