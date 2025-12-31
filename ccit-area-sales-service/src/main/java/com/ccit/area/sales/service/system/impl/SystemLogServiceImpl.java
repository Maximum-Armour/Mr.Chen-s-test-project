package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.system.SystemLogPO;
import com.ccit.area.sales.dao.dto.system.SystemLogAddDTO;
import com.ccit.area.sales.dao.mapper.system.SystemLogMapper;
import com.ccit.area.sales.dao.vo.system.SystemLogPageListVO;
import com.ccit.area.sales.service.system.ISystemLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “操作日志记录”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:02:49
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemLogServiceImpl
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogPO> implements ISystemLogService {

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午3:49:55
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectPageList
	 *  Page<SystemLogPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SystemLogPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SystemLogPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		Page<SystemLogPageListVO> systemLogPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<SystemLogPageListVO> records = systemLogPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setBusinessTypeName(redisUtils.getDict("businessOperationType_", records.get(i).getBusinessType()));
			records.get(i).setStatusName(redisUtils.getDict("logStatus_", records.get(i).getStatus()));
		}
		return pages;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午4:18:59
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemLogAddDTO entity) {
		SystemLogPO systemLog = new SystemLogPO();
		BeanUtils.copyProperties(entity, systemLog);
		int insertFlag = this.baseMapper.insert(systemLog);
		if (insertFlag > 0) {
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

}
