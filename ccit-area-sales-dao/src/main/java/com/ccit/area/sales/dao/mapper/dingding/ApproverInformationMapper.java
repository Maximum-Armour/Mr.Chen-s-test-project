package com.ccit.area.sales.dao.mapper.dingding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.dingding.po.TApproverInformationPO;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.TApproverInformationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Baishangqianxue
 * @description 针对表【t_approver_information(OA审批人信息表)】的数据库操作Mapper
 * @createDate 2024-10-23 13:40:06
 * @Entity bean.bean.TApproverInformation
 */
public interface ApproverInformationMapper extends BaseMapper<TApproverInformationPO> {
    List<TApproverInformationVO> getApproverDetail(@Param("entity") ToBeReviewedPageRequest entity);

    List<TApproverInformationVO> getProcessedApproveDetail(@Param("entity") ToBeReviewedPageRequest entity);

    int updateApprovalStatus(@Param("entity") RabbitApprovalRequest entity);

    int updateStatus(@Param("entity") RabbitApprovalRequest entity);

    int updateOrderApprovalStatus(@Param("entity") RabbitApprovalRequest entity);
}




