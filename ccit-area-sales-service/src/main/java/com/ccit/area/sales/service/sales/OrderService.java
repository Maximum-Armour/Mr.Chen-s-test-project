package com.ccit.area.sales.service.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.sales.OrderLinePO;
import com.ccit.area.sales.dao.dto.sales.OrderLineDTO;
import com.ccit.area.sales.dao.dto.sales.OrderLinesDTO;
import com.ccit.area.sales.dao.dto.sales.OrderSummaryDTO;
import com.ccit.area.sales.dao.dto.sales.OrderUpDTO;
import com.ccit.area.sales.dao.vo.sales.OrderLineListVO;
import com.ccit.area.sales.dao.vo.sales.OrderLineVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “订单”服务类
 *  * 创建人 : cf
 *  * 创建时间 : 2024年9月18日 上午9:26:20
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.sales
 *  * 类名 : OrderService
 */
public interface OrderService extends IService<OrderLinePO> {


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 上午9:10:03
     * 描述 : 根据listingcode查询相应数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : selectOrder
     * OrderLineVO
     *
     * @throws
     */
    List<OrderLineVO> selectOrder(OrderLineListVO entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:51:11
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : edit
     * String
     *
     * @throws
     */
    String edit(OrderUpDTO entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:51:11
     * 描述 : 汇总数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : summary
     * BigDecimal
     *
     * @throws
     */
    BigDecimal[] summary(OrderSummaryDTO entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月26日 上午10:24:11
     * 描述 : 拒接订单
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : refuse
     * String
     *
     * @throws
     */
    String refuse(OrderSummaryDTO entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月27日 上午10:17:11
     * 描述 : 提交订单
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : submit
     * String
     *
     * @throws
     */
    String submit(List<OrderLinesDTO> entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月29日 下午午14:39:03
     * 描述 : 根据map查询相应数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : queryReview
     * OrderLineVO
     *
     * @throws
     */
    Page<OrderLineVO> queryReview(Map<String, Object> param);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月27日 上午10:17:11
     * 描述 : 审核通过或者驳回
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : approvedorreject
     * String
     *
     * @throws
     */
    String approvedorreject(OrderLineDTO entity);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月30日 上午11:01:11
     * 描述 : 订单行审核全部
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : reviewAll
     * String
     *
     * @throws
     */
    String reviewAll(OrderSummaryDTO entity);

    /**
     * 创建人 : cf
     * 创建时间 : 2024年10月11日 上午9:10:03
     * 描述 : 根据map查询相应数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : orderDetails
     * OrderLineVO
     *
     * @throws
     */
    Page<OrderLineVO> orderDetails(Map<String, Object> param);


    /**
     * 创建人 : cf
     * 创建时间 : 2024年10月11日 上午10:11:11
     * 描述 : 订单行明细汇总单位（吨）
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : detailedSummary
     * BigDecimal
     *
     * @throws
     */
    BigDecimal detailedSummary(OrderSummaryDTO entity);

    OrderLineVO get(Long id);

    /**
     * 创建人 : cf
     * 创建时间 : 2024年10月11日 上午9:10:03
     * 描述 : 根据orderLineNo查询相应数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : orderDetails
     * OrderLineVO
     *
     * @throws
     */
    OrderLineVO orderDetail(OrderLineDTO entity);


    /**
     * 下载签章合同
     *
     * @param orderLineNo
     * @return 包含文件流的ResponseVO
     */
    byte[] downloadSignature(String orderLineNo);

    String updateStatus(List<Long> ids, String status);

    Page<OrderLineVO> querySHWCReview(Map<String, Object> param);
}
