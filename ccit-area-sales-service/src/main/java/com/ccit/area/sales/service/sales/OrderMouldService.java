package com.ccit.area.sales.service.sales;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.sales.OrderMouldPO;
import com.ccit.area.sales.dao.dto.sales.OrderMouldDTO;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;

/**
 *
  * 描述 : “订单模板”服务类
  * 创建人 : cf
  * 创建时间 : 2024年9月18日 上午9:26:20
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.service.sales
  * 类名 : OrderMouldService
 */
public interface OrderMouldService extends IService<OrderMouldPO> {



    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:51:11
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : edit
     *  String
     *  @throws
     */
    String edit(OrderMouldDTO entity);

    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:51:11
     * 描述 : 查询数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : select
     *  OrderMouldnoVO
     *  @throws
     */
    OrderMouldnoVO select(OrderMouldDTO entity);



}
