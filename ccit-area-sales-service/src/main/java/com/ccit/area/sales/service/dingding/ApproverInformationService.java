package com.ccit.area.sales.service.dingding;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.dingding.po.TApproverInformationPO;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.TApproverInformationVO;

import java.util.List;

/**
 * @author Baishangqianxue
 * @description 针对表【t_approver_information(OA审批人信息表)】的数据库操作Service
 * @createDate 2024-10-23 13:40:06
 */
public interface ApproverInformationService extends IService<TApproverInformationPO> {
    String addApproverInformation(TApproverInformationPO tApproverInformationPO);

    List<TApproverInformationVO> getApproverDetail(ToBeReviewedPageRequest entity);

    List<TApproverInformationVO> getProcessedApproveDetail(ToBeReviewedPageRequest entity);

    String updateApprovalStatus(RabbitApprovalRequest rabbitApprovalRequest);

    String updateStatus(RabbitApprovalRequest rabbitApprovalRequest);

    String updateOrderApprovalStatus(RabbitApprovalRequest rabbitApprovalRequest);
}
