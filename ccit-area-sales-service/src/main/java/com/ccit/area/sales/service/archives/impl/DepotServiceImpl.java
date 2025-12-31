package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.dao.domain.archives.DepotPO;
import com.ccit.area.sales.dao.dto.archives.DepotAddDTO;
import com.ccit.area.sales.dao.dto.archives.DepotDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DepotEditDTO;
import com.ccit.area.sales.dao.mapper.archives.DepotMapper;
import com.ccit.area.sales.dao.vo.archives.DepotDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DepotPageListVO;
import com.ccit.area.sales.dao.vo.archives.DepotTreeVO;
import com.ccit.area.sales.service.archives.IDepotService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.common.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * 描述 : “仓库档案”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:52:54
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : DepotServiceImpl
 */
@Service
public class DepotServiceImpl extends ServiceImpl<DepotMapper, DepotPO> implements IDepotService {

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
	 * 创建时间 : 2024年8月1日 上午9:05:11
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectTreeList
	 *  List<DepotTreeVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<DepotTreeVO> selectTreeList(Map<String, Object> param) {
		List<DepotTreeVO> result = new ArrayList<>();
		LambdaQueryWrapper<DepotPO> wrapper = DepotPO.wrapper();
		wrapper.orderByAsc(DepotPO::getDepotOrders);
		String notId = (String) param.get("notId");
		String parentId = (String) param.get("parentId");
		String depotName = (String) param.get("depotName");
		String status = (String) param.get("status");
		String difference = (String) param.get("difference");
		if (StringUtil.isNotBlank(notId)) {
			wrapper.ne(DepotPO::getId, Long.parseLong(notId));
		}
		if (StringUtil.isNotBlank(parentId)) {
			wrapper.eq(DepotPO::getParentId, "root".equals(parentId) ? 0L : Long.parseLong(parentId));
		}
		if (StringUtil.isNotBlank(status)) {
			wrapper.eq(DepotPO::getStatus, status);
		}
		List<DepotPO> depotList = this.baseMapper.selectList(wrapper);
		if (CollectionUtils.isNotEmpty(depotList)) {
			result = ListUtil.arrayCopyTo(depotList, DepotTreeVO.class);
			result.stream().forEach(entity -> {
				entity.setStatusName(redisUtils.getDict("status_",entity.getStatus()));
				entity.setIsPortName(redisUtils.getDict("port_",entity.getIsPort()));
				entity.setIsTransitName(redisUtils.getDict("transit_",entity.getIsTransit()));
			});
			List<Long> productClassIdList = result.stream().distinct().map(entity -> entity.getId())
					.collect(Collectors.toList());
			param = new HashMap<>(2);
			param.put("notId", notId);
			param.put("list", productClassIdList);
			List<Map<String, Object>> childrenCountList = this.baseMapper.getChildrenCount(param);
			if (CollectionUtils.isNotEmpty(childrenCountList)) {
				Map<Long, Long> childrenCountMap = childrenCountList.stream().collect(Collectors
						.toMap(map -> (Long) map.get("id"), map -> (Long) map.get("count"), (key1, key2) -> key1));
				for (DepotTreeVO entity : result) {
					Long count = childrenCountMap.get(entity.getId());
					if (count != null && count > 0) {
						entity.setLeaf(false);
					}
				}
			}
			for (int i1 = 0; i1 < result.size(); i1++) {
				DepotTreeVO depotTreeVO = result.get(i1);
				Long id = depotTreeVO.getId();
				if(difference.equals("0")){
					List<DepotTreeVO> childrenList = this.baseMapper.getChildrenList(id);
					for (int i = 0; i < childrenList.size(); i++) {
						childrenList.get(i).setStatusName(redisUtils.getDict("status_",childrenList.get(i).getStatus()));
						childrenList.get(i).setIsPortName(redisUtils.getDict("port_",childrenList.get(i).getIsPort()));
						childrenList.get(i).setIsTransitName(redisUtils.getDict("transit_",childrenList.get(i).getIsTransit()));
					}
					depotTreeVO.setChildren(childrenList);
				}else {
					List<DepotTreeVO> childrenList = this.baseMapper.getChildrenLists(id,depotName,status);
					for (int i = 0; i < childrenList.size(); i++) {
						childrenList.get(i).setStatusName(redisUtils.getDict("status_",childrenList.get(i).getStatus()));
						childrenList.get(i).setIsPortName(redisUtils.getDict("port_",childrenList.get(i).getIsPort()));
						childrenList.get(i).setIsTransitName(redisUtils.getDict("transit_",childrenList.get(i).getIsTransit()));
					}
					depotTreeVO.setChildren(childrenList);
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 下午4:33:43
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<DepotPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<DepotPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<DepotPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		String notIdList = (String) param.get("notIdList");
		if (StringUtil.isNotBlank(notIdList)) {
			param.put("notIdList", Arrays.asList(notIdList.split(",")));
		}
		Page<DepotPageListVO> depotPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<DepotPageListVO> records = depotPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setStatusName(redisUtils.getDict("status_",records.get(i).getStatus()));
			records.get(i).setIsPortName(redisUtils.getDict("port_",records.get(i).getIsPort()));
			records.get(i).setIsTransitName(redisUtils.getDict("transit_",records.get(i).getIsTransit()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:05:32
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(DepotAddDTO entity) {
		LambdaQueryWrapper<DepotPO> wrapper = DepotPO.wrapper();
		wrapper.eq(DepotPO::getDepotName, entity.getDepotName());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("仓库名称已存在");
		}
		String code = "CK";
		String value = systemSequenceService.get(code, 6);
		DepotPO depot = new DepotPO();
		entity.setDepotNo(value);
		BeanUtils.copyProperties(entity, depot);
		int insertFlag = this.baseMapper.insert(depot);
		if (insertFlag > 0) {
			this.setDepotSeq(depot, true);
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:06:04
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  DepotDetailsVO  
	 *  @throws
	 */
	@Override
	public DepotDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("仓库档案ID不能为空");
		}
		DepotPO depot = this.baseMapper.selectById(id);
		if (null == depot) {
			throw new BusinessException("获取仓库档案失败");
		}
		DepotDetailsVO result = new DepotDetailsVO();
		String parentName = depot.getParentId() == 0L ? "根目录" : "";
		if (depot.getParentId() != null && depot.getParentId() != 0L) {
			DepotPO parent = this.baseMapper.selectById(depot.getParentId());
			parentName = parent.getDepotName();
		}
		result.setParentName(parentName);
		BeanUtils.copyProperties(depot, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:06:07
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(DepotEditDTO entity) {
		LambdaQueryWrapper<DepotPO> wrapper = DepotPO.wrapper();
		wrapper.eq(DepotPO::getDepotName, entity.getDepotName());
		wrapper.ne(DepotPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("仓库名称已存在");
		}
		Long id = entity.getId();
		String status = entity.getStatus();
		Long parentId = entity.getParentId();
		if (parentId == 0){
			this.baseMapper.updateChildren(status,id);
		}
		DepotPO depot = new DepotPO();
		BeanUtils.copyProperties(entity, depot);
		this.setDepotSeq(depot, false);
		int updateByIdFlag = this.baseMapper.updateById(depot);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:06:09
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(DepotDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品档案ID不能为空");
		}
		LambdaQueryWrapper<DepotPO> wrapper = DepotPO.wrapper();
		wrapper.in(DepotPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new DepotPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:24:06
	 * 描述 : 设置仓库序列号(私有方法)
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : setDepotSeq
	 *  void  
	 *  @throws
	 */
	private void setDepotSeq(DepotPO depot, Boolean isUpdate) {
		DepotPO parent = this.getById(depot.getParentId());
		if (parent != null) {
			depot.setDepotSeq(parent.getDepotSeq() + depot.getId() + ".");
		} else {
			depot.setDepotSeq("." + depot.getId() + ".");
		}
		if (isUpdate) {
			this.updateById(depot);
		}
	}

}
