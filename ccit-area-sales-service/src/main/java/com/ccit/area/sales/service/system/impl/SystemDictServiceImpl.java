package com.ccit.area.sales.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.dao.domain.system.SystemDictPO;
import com.ccit.area.sales.dao.dto.system.SystemDictAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemDictEditDTO;
import com.ccit.area.sales.dao.mapper.system.SystemDictMapper;
import com.ccit.area.sales.dao.vo.system.SystemDictChildrenVO;
import com.ccit.area.sales.dao.vo.system.SystemDictDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemDictListVO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;
import com.ccit.area.sales.service.system.ISystemDictService;
import com.ccit.area.sales.service.system.ISystemTreeNodeService;
import com.ccit.common.utils.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * 描述 : “字典”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年6月16日 上午9:34:52
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemDictServiceImpl
 */
@Service
public class SystemDictServiceImpl extends ServiceImpl<SystemDictMapper, SystemDictPO> implements ISystemDictService {
	@Autowired
	private RedisUtils redisUtils;
	/**
	 * “树节点”服务类
	 */
	@Autowired
	private ISystemTreeNodeService systemTreeNodeService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:35:21
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectTreeList33
	 *  List<SystemDictTreeVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<SystemDictTreeVO> selectTreeList(Map<String, Object> param) {
		List<SystemDictTreeVO> result = new ArrayList<>();
		LambdaQueryWrapper<SystemDictPO> wrapper = SystemDictPO.wrapper();
		wrapper.eq(SystemDictPO::getDeleted, GlobalConstants.DELETE_NO);
		wrapper.orderByAsc(SystemDictPO::getDictOrders);
		String dictCode = (String) param.get("dictCode");
		if (StringUtils.isNoneBlank(dictCode)) {
			wrapper.like(SystemDictPO::getDictCode, dictCode);
		}
		String dictName = (String) param.get("dictName");
		if (StringUtils.isNoneBlank(dictName)) {
			wrapper.like(SystemDictPO::getDictName, dictName);
		}
		Integer status = (Integer) param.get("status");
		if (status != null) {
			wrapper.eq(SystemDictPO::getStatus, status);
		}
		List<SystemDictPO> systemDictList = this.baseMapper.selectList(wrapper);
		if (!CollectionUtils.isEmpty(systemDictList)) {
			List<SystemDictTreeVO> list = ListUtil.arrayCopyTo(systemDictList, SystemDictTreeVO.class);
			list.stream().forEach(v -> {
				v.setStatusName(v.getStatus() == 0 ? "正常" : "禁用");
			});
			for (int i = 0; i < list.size(); i++) {
				Long id = list.get(i).getId();
				Long parentId = list.get(i).getParentId();
				Long l = this.baseMapper.childrenCount(id);
				if (l > 0){
					list.get(i).setDictIsLeaf(true);
					List<SystemDictTreeVO> systemDictTreeVOS = this.baseMapper.selectChildren(id);
					list.get(i).setChildren(systemDictTreeVOS);
					result.add(list.get(i));
				}else if (parentId == 0L){
					result.add(list.get(i));
				}
			}
			//result = systemTreeNodeService.changeDataDictTree(list);
		}
		return result;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 上午11:16:36
	 * 描述 : 列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectList
	 *  List<SystemDictListVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<SystemDictListVO> selectList(Map<String, Object> param) {
		List<SystemDictListVO> result = new ArrayList<>();
		LambdaQueryWrapper<SystemDictPO> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SystemDictPO::getIsDeleted,0);
		wrapper.orderByAsc(SystemDictPO::getDictOrders);
		List<SystemDictPO> systemDictList = this.baseMapper.selectList(wrapper);
		if (!CollectionUtils.isEmpty(systemDictList)) {
			result = ListUtil.arrayCopyTo(systemDictList, SystemDictListVO.class);
		}
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:35:28
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemDictAddDTO entity) {
		LambdaQueryWrapper<SystemDictPO> wrapper = SystemDictPO.wrapper();
		wrapper.eq(SystemDictPO::getDictCode, entity.getDictCode());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("字典编码已存在");
		}
		SystemDictPO systemDict = new SystemDictPO();
		BeanUtils.copyProperties(entity, systemDict);
		int insertFlag = this.baseMapper.insert(systemDict);
		if (insertFlag > 0) {
			this.setDictSeq(systemDict);
			this.baseMapper.updateById(systemDict);
			List<SystemDictPO> systemDictPOS = this.baseMapper.selectList(null);
			List<SystemDictListVO> list = ListUtil.arrayCopyTo(systemDictPOS, SystemDictListVO.class);
			Map<String, String> map = list.stream()
					.filter(t -> t.getDictValue() != null)
					.collect(Collectors.toMap(
							SystemDictListVO::getDictCode,
							SystemDictListVO::getDictName
					));
			redisUtils.remove("dictionary:dictionaryName");
			String encrypt = AESUtil.encrypt(JSON.toJSONString(map), GlobalConstants.AES_PARAM_KEY);
			redisUtils.set("dictionary:dictionaryName", encrypt);
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:43:10
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : get
	 *  SystemDictDetailsVO  
	 *  @throws
	 */
	@Override
	public SystemDictDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("字典ID不能为空");
		}
		SystemDictPO systemDict = this.baseMapper.selectById(id);
		if (null == systemDict) {
			throw new BusinessException("获取字典失败");
		}
		SystemDictDetailsVO systemDictDetailsVO = new SystemDictDetailsVO();
		BeanUtils.copyProperties(systemDict, systemDictDetailsVO);
		return systemDictDetailsVO;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:43:55
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SystemDictEditDTO entity) {
		SystemDictPO systemDict = new SystemDictPO();
		BeanUtils.copyProperties(entity, systemDict);
		this.setDictSeq(systemDict);
		int updateByIdFlag = this.baseMapper.updateById(systemDict);
		if (updateByIdFlag > 0) {
			List<SystemDictPO> systemDictPOS = this.baseMapper.selectList(null);
			List<SystemDictListVO> list = ListUtil.arrayCopyTo(systemDictPOS, SystemDictListVO.class);
			Map<String, String> map = list.stream()
					.filter(t -> t.getDictValue() != null)
					.collect(Collectors.toMap(
							SystemDictListVO::getDictCode,
							SystemDictListVO::getDictName
					));
			redisUtils.remove("dictionary:dictionaryName");
			String encrypt = AESUtil.encrypt(JSON.toJSONString(map), GlobalConstants.AES_PARAM_KEY);
			redisUtils.set("dictionary:dictionaryName", encrypt);
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:44:30
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	public String delete(Long id) {
		if (null == id) {
			throw new BusinessException("字典ID不能为空");
		}
		LambdaQueryWrapper<SystemDictPO> wrapper = SystemDictPO.wrapper();
		wrapper.in(SystemDictPO::getId, id);
		int updateFlag = this.baseMapper.update(new SystemDictPO(true, false), wrapper);
		if (updateFlag > 0) {
			List<SystemDictPO> systemDictPOS = this.baseMapper.selectList(null);
			List<SystemDictListVO> list = ListUtil.arrayCopyTo(systemDictPOS, SystemDictListVO.class);
			Map<String, String> map = list.stream()
					.filter(t -> t.getDictValue() != null)
					.collect(Collectors.toMap(
							SystemDictListVO::getDictCode,
							SystemDictListVO::getDictName
					));
			redisUtils.remove("dictionary:dictionaryName");
			String encrypt = AESUtil.encrypt(JSON.toJSONString(map), GlobalConstants.AES_PARAM_KEY);
			redisUtils.set("dictionary:dictionaryName", encrypt);
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:45:13
	 * 描述 : 根据字典编码获取子节点数组
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : getChildrenList
	 *  List<SystemDictChildrenVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	@Cacheable(value = "getChildrenList")
	public List<SystemDictChildrenVO> getChildrenList(String dictCode) {
		if (StringUtils.isBlank(dictCode)) {
			throw new BusinessException("字典编码不能为空");
		}
		List<SystemDictChildrenVO> result = new ArrayList<>();
		LambdaQueryWrapper<SystemDictPO> wrapper = SystemDictPO.wrapper();
		wrapper.eq(SystemDictPO::getDictCode, dictCode);
		SystemDictPO systemDict = this.baseMapper.selectOne(wrapper);
		if (null == systemDict) {
			throw new BusinessException("字典编码不存在，请重新操作！");
		}
		wrapper = SystemDictPO.wrapper();
		wrapper.eq(SystemDictPO::getParentId, systemDict.getId());
		List<SystemDictPO> systemDictList = this.baseMapper.selectList(wrapper);
		if (!CollectionUtils.isEmpty(systemDictList)) {
			result = ListUtil.arrayCopyTo(systemDictList, SystemDictChildrenVO.class);
		}
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:43:00
	 * 描述 : 设置字典序列号(私有方法)
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : setDictSeq
	 *  void  
	 *  @throws
	 */
	private void setDictSeq(SystemDictPO systemDict) {
		SystemDictPO parent = this.getById(systemDict.getParentId());
		if (parent != null) {
			systemDict.setDictSeq(parent.getDictSeq() + systemDict.getId() + ".");
		} else {
			systemDict.setDictSeq("." + systemDict.getId() + ".");
		}
	}

}
