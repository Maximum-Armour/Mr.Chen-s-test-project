package com.ccit.area.sales.service.quartz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.mapper.marketing.ApplyBiddingMapper;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BidJob implements Job {
    @Autowired
    private ISalesBiddingService salesBiddingService;

    @Autowired
    private ApplyBiddingMapper applyBiddingMapper;

    /**
     * 执行竞价校验任务
     * 该方法根据JobExecutionContext上下文信息，执行与竞价相关的校验任务
     * 主要包括检查竞价编号对应的信息，进行最小报名人数校验（针对特定竞价模式），
     * 更新竞价状态，并在满足条件时推送消息到化销系统开始竞拍
     *
     * @param context JobExecutionContext对象，包含执行任务所需的信息
     * @throws JobExecutionException 如果任务执行过程中发生错误
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取竞价编号
        String tenderNumber = context.getJobDetail().getKey().getName();
        // 记录任务执行时间
        log.info("执行竞价校验任务: [{}] 执行时间: [{}]", tenderNumber, LocalDateTime.now());

        // 检查竞价编号是否有效
        if (tenderNumber == null || tenderNumber.isEmpty()) {
            log.error("竞价任务的 tenderNumber 为空或无效: [{}]", tenderNumber);
            return;
        }

        // 查询数据库中对应的竞价信息
        LambdaQueryWrapper<SalesBiddingPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        SalesBiddingPO salesBiddingPO = salesBiddingService.getOne(queryWrapper);

        // 检查是否找到对应的竞价信息
        if (salesBiddingPO == null) {
            log.error("未找到对应的竞价信息: [{}]", tenderNumber);
            return;
        }

        try {
            // 如果是延时竞价和可选量竞价需要校验最小报名人.
            if ("YSJJ".equals(salesBiddingPO.getBiddingMode()) || "KXLYSJJ".equals(salesBiddingPO.getBiddingMode())) {
                int minBidders = Integer.parseInt(salesBiddingPO.getMinBidders());
                int signUpQuantity = applyBiddingMapper.getSignUpQuantity(tenderNumber);

                // 检查获取报名人数是否成功
                if (signUpQuantity < 0) {
                    log.error("获取报名人数失败: [{}]", tenderNumber);
                    return;
                }

                // 最小报名人数校验
                if (minBidders > signUpQuantity) {
                    // 不符合最少报名人.
                    salesBiddingService.updateStatus(tenderNumber, "流拍");
                    log.info("竞价任务 [{}] 因为报名人数不足而流拍", tenderNumber);
                    //批量将未报名客户的审批状态更新为审核拒绝.
                    applyBiddingMapper.batchUpdateApprovalStatus(tenderNumber);
                    return;
                }
            }
            //报名结束后批量将未审核的报名人状态修改为审核拒绝.
            applyBiddingMapper.batchUpdateApprovalStatus(tenderNumber);

            // 更新竞拍状态为“竞拍中”
            salesBiddingService.updateStatus(tenderNumber, "竞拍中");
            log.info("竞价任务 [{}] 更新状态为竞拍中", tenderNumber);

            // 推送化销系统开始竞拍.
            pushToSalesSystem(tenderNumber);

        } catch (Exception e) {
            log.error("执行竞价任务时发生未知错误: [{}], 错误信息: {}", tenderNumber, e.getMessage());
        }
    }

    private void pushToSalesSystem(String tenderNumber) {
        // 实现推送化销系统开始竞拍的逻辑
        log.info("推送化销系统开始竞拍: [{}]", tenderNumber);
        // 具体实现代码...
    }

}
