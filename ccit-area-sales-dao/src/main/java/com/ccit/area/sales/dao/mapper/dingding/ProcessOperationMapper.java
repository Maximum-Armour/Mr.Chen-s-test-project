package com.ccit.area.sales.dao.mapper.dingding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.dingding.po.TProcessOperation;
import com.ccit.area.sales.dao.dingding.po.ToBeReviewedPageRequest;
import com.ccit.area.sales.dao.dingding.vo.ProcessOperationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessOperationMapper extends BaseMapper<TProcessOperation> {
    List<ProcessOperationVO> getHaveDoneApproveDetail(@Param(value = "entity")ToBeReviewedPageRequest entity);
}
