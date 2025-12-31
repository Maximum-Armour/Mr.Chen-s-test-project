package com.ccit.area.sales.service.quartz.Impl;

import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import com.ccit.area.sales.service.quartz.BidJob;
import com.ccit.area.sales.service.quartz.QuartzBidScheduler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Slf4j
@Service
public class QuartzBidSchedulerImpl implements QuartzBidScheduler {

    @Autowired
    private ISalesBiddingService salesBiddingService;
    @Autowired
    private Scheduler scheduler; // 使用 Spring 注入 Scheduler

    @Override
    public void scheduleBidTask(Long businessId) {
        SalesBiddingPO salesBiddingPO = salesBiddingService.getById(businessId);
        if (salesBiddingPO == null) {
            log.error("竞价任务创建失败：未找到竞价信息, businessId: {}", businessId);
            return;
        }

        String tenderNumber = salesBiddingPO.getTenderNumber();
        Date signUpEndTime = salesBiddingPO.getSignUpEndTime();

        if (signUpEndTime == null || signUpEndTime.before(new Date())) {
            log.warn("竞价任务创建失败: 无效的截止时间, tenderNumber: {}, 截止时间: {}", tenderNumber, signUpEndTime);
            return;
        }

        try {
            // 定义 Job
            JobDetail job = JobBuilder.newJob(BidJob.class)
                    .withIdentity(tenderNumber)
                    .build();

            // 创建 Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_" + tenderNumber)
                    .startAt(signUpEndTime)
                    .build();

            // 绑定 Job 和 Trigger
            scheduler.scheduleJob(job, trigger);
            log.info("竞价任务 [{}] 计划在 [{}] 触发.", tenderNumber, signUpEndTime);
        } catch (SchedulerException e) {
            log.error("创建竞价任务失败, tenderNumber: {}, 错误信息: {}", tenderNumber, e.getMessage(), e);
        }
    }
}
