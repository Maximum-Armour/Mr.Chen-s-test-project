package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.system.SystemLoginLogPO;
import com.ccit.area.sales.dao.mapper.system.SystemLoginLogMapper;
import com.ccit.area.sales.dao.vo.system.SystemLoginLogPageListVO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.area.sales.service.system.ISystemLoginLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 
 * 描述 : “登录日志”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:32:10
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemLoginLogServiceImpl
 */
@Service
public class SystemLoginLogServiceImpl extends ServiceImpl<SystemLoginLogMapper, SystemLoginLogPO> implements ISystemLoginLogService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:53:25
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemLoginLogAddDTO entity) {
		SystemLoginLogPO systemLoginLog = new SystemLoginLogPO();
		BeanUtils.copyProperties(entity, systemLoginLog);
		int insertFlag = this.baseMapper.insert(systemLoginLog);
		if (insertFlag > 0) {
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月28日 下午3:49:55
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectPageList
	 *  Page<SystemLoginLogPageListVO>
	 *  @throws
	 */
	@Override
	public Page<SystemLoginLogPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SystemLoginLogPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		pages.setRecords(this.baseMapper.selectPageList(pages, param));
		return pages;
	}

}
