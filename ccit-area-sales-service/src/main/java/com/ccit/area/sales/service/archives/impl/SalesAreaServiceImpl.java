package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.SalesAreaPO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaAddDTO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.SalesAreaEditDTO;
import com.ccit.area.sales.dao.mapper.archives.SalesAreaMapper;
import com.ccit.area.sales.dao.vo.archives.SalesAreaDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SalesAreaPageListVO;
import com.ccit.area.sales.service.archives.ISalesAreaService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import org.apache.commons.collections4.CollectionUtils;
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
 * 描述 : “销售区域”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:40:16
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : SalesAreaServiceImpl
 */
@Service
public class SalesAreaServiceImpl extends ServiceImpl<SalesAreaMapper, SalesAreaPO> implements ISalesAreaService {

	/**
	 * “序列号”服务类
	 */
	@Autowired
	private ISystemSequenceService systemSequenceService;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:49:09
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<SalesAreaPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SalesAreaPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SalesAreaPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		Page<SalesAreaPageListVO> salesAreaPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<SalesAreaPageListVO> records = salesAreaPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setStatusName(redisUtils.getDict("status_",records.get(i).getStatus()));
			records.get(i).setTradeTypeName(redisUtils.getDict("tradeType_",records.get(i).getTradeType()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:49:13
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SalesAreaAddDTO entity) {
		LambdaQueryWrapper<SalesAreaPO> wrapper = SalesAreaPO.wrapper();
		wrapper.eq(SalesAreaPO::getAreaNo, entity.getAreaNo());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("区域编码已存在");
		}
		String code = "QY";
		String value = systemSequenceService.get(code, 6);
		SalesAreaPO materiel = new SalesAreaPO();
		entity.setAreaNo(value);
		BeanUtils.copyProperties(entity, materiel);
		int insertFlag = this.baseMapper.insert(materiel);
		if (insertFlag > 0) {
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:49:15
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  SalesAreaDetailsVO  
	 *  @throws
	 */
	@Override
	public SalesAreaDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("销售区域ID不能为空");
		}
		SalesAreaPO materiel = this.baseMapper.selectById(id);
		if (null == materiel) {
			throw new BusinessException("获取销售区域失败");
		}
		SalesAreaDetailsVO result = new SalesAreaDetailsVO();
		BeanUtils.copyProperties(materiel, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:49:18
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SalesAreaEditDTO entity) {
		LambdaQueryWrapper<SalesAreaPO> wrapper = SalesAreaPO.wrapper();
		wrapper.eq(SalesAreaPO::getAreaNo, entity.getAreaNo());
		wrapper.ne(SalesAreaPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("区域编码已存在");
		}
		SalesAreaPO materiel = new SalesAreaPO();
		BeanUtils.copyProperties(entity, materiel);
		int updateByIdFlag = this.baseMapper.updateById(materiel);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午2:49:20
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SalesAreaDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品档案ID不能为空");
		}
		LambdaQueryWrapper<SalesAreaPO> wrapper = SalesAreaPO.wrapper();
		wrapper.in(SalesAreaPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new SalesAreaPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

//	/**
//	 *
//	 * 创建人 : tb
//	 * 创建时间 : 2024年10月18日 下午2:32:51
//	 * 描述 : 树列表，支持高级查询
//	 * 包名 : com.ccit.area.sales.service.archives.impl
//	 * 方法名 : selectTreeList
//	 *  List<SalesAreaPageListVO>
//	 *  @throws
//	 */
//	@Override
//	public List<SalesAreaPageListVO> selectTreeList(SalesAreaPageListVO param) {
//		List<SalesAreaPageListVO> childrenList = this.baseMapper.selectList(param.getStatus());
//		for (int i = 0; i < childrenList.size(); i++) {
//			SalesAreaPageListVO entity = childrenList.get(i);
//			String productClassNo = entity.getProductClassNo();
//			List<SalesAreaPageListVO> childrenList1 = this.baseMapper.getChildrenList(productClassNo);
//			entity.setChildren(childrenList1);
//			for (int j = 0; j < childrenList1.size(); j++) {
//				String tradeType = childrenList1.get(j).getTradeType();
//				childrenList1.get(j).setProductClassName((String) DictionaryInitializer.dictionaryMap.get("tradeType_" + tradeType.toLowerCase()));
//				List<SalesAreaPageListVO> childrensList = this.baseMapper.getChildrensList(tradeType);
//				childrenList1.get(j).setChildren(childrensList);
//				for (int k = 0; k < childrensList.size(); k++) {
//					String areaName = childrensList.get(k).getAreaName();
//					childrensList.get(k).setProductClassName(areaName);
//				}
//			}
//		}
//		return childrenList;
//	}
}
