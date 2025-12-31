package com.ccit.area.sales.dao.mapper.sales;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.sales.OrderMouldPO;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;
import org.apache.ibatis.annotations.Param;


/**
 *
  * 描述 : “订单模板行表”接口类
  * 创建人 : cf
  * 创建时间 : 2024年9月26日 下午16:26:16
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.dao.mapper.sales
  * 类名 : OrderMouldMapper
 */
public interface OrderMouldMapper  extends BaseMapper<OrderMouldPO> {

    OrderMouldnoVO selectorderLineNo(@Param("orderLineNo") String orderLineNo);

    int  updateorderLineNo (OrderMouldPO orderMouldPO);

}
