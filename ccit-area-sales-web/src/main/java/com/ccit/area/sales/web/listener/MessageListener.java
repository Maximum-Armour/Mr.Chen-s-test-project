package com.ccit.area.sales.web.listener;

import com.alibaba.fastjson.JSON;
import com.ccit.area.sales.common.dingding.constant.RabbitmqConstant;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.mapper.dingding.ApproverInformationMapper;
import com.ccit.area.sales.service.quartz.QuartzBidScheduler;
import com.ccit.area.sales.service.sales.OrderStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @Autowired
    private ApproverInformationMapper approvalsMapper;
    @Autowired
    private QuartzBidScheduler quartzBidScheduler;

    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @RabbitListener(queues = RabbitmqConstant.QUEUE_NAME)
    public void receiveMessage(String message) {
        log.info("RabbitMQ接受消息成功 queues:{} message: {}", RabbitmqConstant.QUEUE_NAME, message);
        RabbitApprovalRequest rabbitApprovalRequest = JSON.parseObject(message, RabbitApprovalRequest.class);

        try {
            if ("竞价结果管理_提交".equals(rabbitApprovalRequest.getBusinessName())) {
                approvalsMapper.updateApprovalStatus(rabbitApprovalRequest);
            } else if ("订单行管理_提交".equals(rabbitApprovalRequest.getBusinessName())) {
                approvalsMapper.updateOrderApprovalStatus(rabbitApprovalRequest);
            } else if ("竞价管理_提交".equals(rabbitApprovalRequest.getBusinessName())) {
                int i = approvalsMapper.updateStatus(rabbitApprovalRequest);
                if (i > 0 && "YTG".equals(rabbitApprovalRequest.getApprovalStatus())) {
                    quartzBidScheduler.scheduleBidTask(rabbitApprovalRequest.getId());
                }
            } else if("接单统计管理_提交".equals(rabbitApprovalRequest.getBusinessName())){
                int i = approvalsMapper.updateStatus(rabbitApprovalRequest);
                if (i > 0 && "YTG".equals(rabbitApprovalRequest.getApprovalStatus())) {
                    orderStatisticsService.updateStatus(rabbitApprovalRequest.getId());
                }
            }else {
                approvalsMapper.updateStatus(rabbitApprovalRequest);
            }
        } catch (Exception e) {
            log.error("消息处理失败，记录错误并丢弃或重试: {}", message, e);
            // 如果有重试机制，可以考虑抛出 AmqpRejectAndDontRequeueException
            throw new AmqpRejectAndDontRequeueException("消息处理失败，不重新入队", e);
        }
    }

}
