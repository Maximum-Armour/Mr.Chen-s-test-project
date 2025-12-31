package com.ccit.area.sales.service.marketing.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.dao.domain.marketing.TaskBiddingPO;
import com.ccit.area.sales.dao.mapper.marketing.TaskBiddingMapper;
import com.ccit.area.sales.service.marketing.ITaskBiddingService;
import org.springframework.stereotype.Service;

@Service
public class TaskBiddingServiceImpl extends ServiceImpl<TaskBiddingMapper, TaskBiddingPO> implements ITaskBiddingService {
}
