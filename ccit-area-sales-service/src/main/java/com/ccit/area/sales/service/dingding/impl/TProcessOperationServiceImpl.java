package com.ccit.area.sales.service.dingding.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.dao.dingding.po.TProcessOperation;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.ProcessOperationVO;
import com.ccit.area.sales.dao.mapper.dingding.ProcessOperationMapper;
import com.ccit.area.sales.service.dingding.ProcessOperationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TProcessOperationServiceImpl extends ServiceImpl<ProcessOperationMapper, TProcessOperation> implements ProcessOperationService {

    @Override
    public void insert(TProcessOperation tProcessOperation) {
        this.baseMapper.insert(tProcessOperation);
    }

    @Override
    public List<ProcessOperationVO> getHaveDoneApproveDetail(ToBeReviewedPageRequest entity) {
        return this.baseMapper.getHaveDoneApproveDetail(entity);
    }


}
