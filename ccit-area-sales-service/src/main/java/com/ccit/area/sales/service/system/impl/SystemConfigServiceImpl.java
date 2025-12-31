package com.ccit.area.sales.service.system.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.system.SystemConfigPO;
import com.ccit.area.sales.dao.dto.system.SystemConfigAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigEditDTO;
import com.ccit.area.sales.dao.mapper.system.SystemConfigMapper;
import com.ccit.area.sales.dao.vo.system.SystemConfigDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemConfigPageListVO;
import com.ccit.area.sales.service.system.ISystemConfigService;

/**
 * 
 * 描述 : “参数配置”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:02:00
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemConfigServiceImpl
 */
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfigPO> implements ISystemConfigService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:15:19
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectPageList
	 *  Page<SystemConfigPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SystemConfigPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SystemConfigPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		pages.setRecords(this.baseMapper.selectPageList(pages, param));
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:15:23
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemConfigAddDTO entity) {
		LambdaQueryWrapper<SystemConfigPO> wrapper = SystemConfigPO.wrapper();
		wrapper.eq(SystemConfigPO::getConfigKey, entity.getConfigKey());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("参数键名已存在");
		}
		SystemConfigPO systemConfig = new SystemConfigPO();
		BeanUtils.copyProperties(entity, systemConfig);
		int insertFlag = this.baseMapper.insert(systemConfig);
		if (insertFlag > 0) {
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:15:26
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : get
	 *  SystemConfigDetailsVO  
	 *  @throws
	 */
	@Override
	public SystemConfigDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("参数配置ID不能为空");
		}
		SystemConfigPO systemConfig = this.baseMapper.selectById(id);
		if (null == systemConfig) {
			throw new BusinessException("获取参数配置失败");
		}
		SystemConfigDetailsVO result = new SystemConfigDetailsVO();
		BeanUtils.copyProperties(systemConfig, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:15:29
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SystemConfigEditDTO entity) {
		LambdaQueryWrapper<SystemConfigPO> wrapper = SystemConfigPO.wrapper();
		wrapper.eq(SystemConfigPO::getConfigKey, entity.getConfigKey());
		wrapper.ne(SystemConfigPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("参数键名已存在");
		}
		SystemConfigPO systemConfig = new SystemConfigPO();
		BeanUtils.copyProperties(entity, systemConfig);
		int updateByIdFlag = this.baseMapper.updateById(systemConfig);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:15:35
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SystemConfigDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("参数配置ID不能为空");
		}
		LambdaQueryWrapper<SystemConfigPO> wrapper = SystemConfigPO.wrapper();
		wrapper.in(SystemConfigPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new SystemConfigPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
			
}
