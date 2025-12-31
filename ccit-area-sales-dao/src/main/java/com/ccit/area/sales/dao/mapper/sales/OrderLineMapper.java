package com.ccit.area.sales.dao.mapper.sales;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.sales.OrderLinePO;
import com.ccit.area.sales.dao.dto.sales.OrderLineDTO;
import com.ccit.area.sales.dao.dto.sales.OrderSummaryDTO;
import com.ccit.area.sales.dao.dto.sales.OrderUpDTO;
import com.ccit.area.sales.dao.vo.sales.OrderLineListVO;
import com.ccit.area.sales.dao.vo.sales.OrderLineVO;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “订单表”接口类
 *  * 创建人 : cf
 *  * 创建时间 : 2024年9月14日 下午14:08:46
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.mapper.sales
 *  * 类名 : OrderLineMapper
 */
public interface OrderLineMapper extends BaseMapper<OrderLinePO> {

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : selectOrder
     * List<OrderLineVO>
     *
     * @throws
     */
    List<OrderLineVO> selectOrder(@Param(value = "entity") OrderLineListVO entity);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 汇总信息
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : summary
     * List<OrderLineVO>
     *
     * @throws
     */
    List<OrderLineVO> summary(@Param(value = "entity") OrderSummaryDTO entity);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月26日 上午10:54:46
     * 描述 : 拒接订单
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : uprefuse
     * int
     *
     * @throws
     */
    int uprefuse(@Param(value = "entity") OrderSummaryDTO entity);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月26日 上午10:54:46
     * 描述 : 提交订单
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : upsubmit
     * int
     *
     * @throws
     */
    int upsubmit(@Param(value = "entity") List<Long> ids);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月26日 上午10:54:46
     * 描述 : 订单查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : selectlist
     * OrderLineVO
     *
     * @throws
     */
    List<OrderLineVO> selectlist(@Param(value = "id") List<Long> id);

    List<OrderLineVO> selectBystatus(@Param(value = "entity") OrderSummaryDTO entity);

    List<OrderLineVO> selectOrderLine(@Param("orderLineNo") List<String> orderLineNo);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : queryReview
     * List<OrderLineVO>
     *
     * @throws
     */
    List<OrderLineVO> queryReview(Page<OrderLineVO> pages,
                                  @Param(value = "param") Map<String, Object> param);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月26日 上午10:54:46
     * 描述 : 批量审核
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : reviewAll
     * int
     *
     * @throws
     */
    int reviewAll(@Param(value = "entity") OrderSummaryDTO entity);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : orderDetails
     * List<OrderLineVO>
     *
     * @throws
     */
    List<OrderLineVO> orderDetails(Page<OrderLineVO> pages,
                                   @Param(value = "param") Map<String, Object> param);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : detailedSummary
     * List<OrderLineVO>
     *
     * @throws
     */
    List<OrderLineVO> detailedSummary(@Param(value = "entity") OrderSummaryDTO entity);

    int uplist(@Param(value = "entity") List<OrderUpDTO> entity);

    List<OrderMouldnoVO> selectMaterielno(@Param("orderNo") List<String> orderNo);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : orderDetails
     * List<OrderLineVO>
     *
     * @throws
     */
    OrderLineVO orderDetail(@Param("orderLineNo") String orderLineNo);

    OrderLineVO orderID(OrderLineDTO entity);

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月18日 上午10:35:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : orderDetails
     * List<OrderLineVO>
     *
     * @throws
     */
    OrderLineVO getPdf(@Param("orderLineNo") String orderLineNo);

    OrderLineVO getDing(Long id);

    OrderLineVO getlineVo(Long id);

    List<OrderLineVO> querySHWCReview(Page<OrderLineVO> pages, Map<String, Object> param);
}
