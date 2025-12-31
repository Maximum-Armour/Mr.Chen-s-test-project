package com.ccit.area.sales.service.dingding.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.dingding.po.TApproverInformationPO;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.TApproverInformationVO;
import com.ccit.area.sales.dao.mapper.dingding.ApproverInformationMapper;
import com.ccit.area.sales.service.dingding.ApproverInformationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Baishangqianxue
 * @description 针对表【t_approver_information(OA审批人信息表)】的数据库操作Service实现
 * @createDate 2024-10-23 13:40:06
 */
@Service
public class TApproverInformationServiceImpl extends ServiceImpl<ApproverInformationMapper, TApproverInformationPO>
        implements ApproverInformationService {

    @Override
    public String addApproverInformation(TApproverInformationPO tApproverInformationPO) {
        int insert = this.baseMapper.insert(tApproverInformationPO);
        if (insert > 0) {
            return "新增成功";
        } else {
            return "新增失败";
        }
    }

    @Override
    public List<TApproverInformationVO> getApproverDetail(ToBeReviewedPageRequest entity) {
        return this.baseMapper.getApproverDetail(entity);
    }

    @Override
    public List<TApproverInformationVO> getProcessedApproveDetail(ToBeReviewedPageRequest entity) {
        return this.baseMapper.getProcessedApproveDetail(entity);
    }

    /**
     * 流程结束后更新审批状态
     *
     * @param rabbitApprovalRequest
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateApprovalStatus(RabbitApprovalRequest rabbitApprovalRequest) {

        try {
            int approvalStatus = this.baseMapper.updateApprovalStatus(rabbitApprovalRequest);
            if (approvalStatus >= 1) {
                return "修改业务状态成功";
            } else {
                throw new BusinessException("修改业务状态失败");
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateStatus(RabbitApprovalRequest rabbitApprovalRequest) {
        try {
            int approvalStatus = this.baseMapper.updateStatus(rabbitApprovalRequest);
            if (approvalStatus >= 1) {
                return "修改业务状态成功";
            } else {
                throw new BusinessException("修改业务状态失败");
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateOrderApprovalStatus(RabbitApprovalRequest rabbitApprovalRequest) {

        try {
            int approvalStatus = this.baseMapper.updateOrderApprovalStatus(rabbitApprovalRequest);
            if (approvalStatus >= 1) {
                return "修改业务状态成功";
            } else {
                throw new BusinessException("修改业务状态失败");
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}




