package com.ccit.area.sales.service.dingding;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.dingding.po.TProcessOperation;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.ProcessOperationVO;

import java.util.List;

public interface ProcessOperationService extends IService<TProcessOperation> {

    void insert(TProcessOperation tProcessOperation);

   List<ProcessOperationVO> getHaveDoneApproveDetail(ToBeReviewedPageRequest entity);
}
