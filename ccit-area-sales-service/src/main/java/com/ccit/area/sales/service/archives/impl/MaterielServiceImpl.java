package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.MaterielPO;
import com.ccit.area.sales.dao.dto.archives.MaterielAddDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.MaterielEditDTO;
import com.ccit.area.sales.dao.mapper.archives.MaterielMapper;
import com.ccit.area.sales.dao.vo.archives.MaterielDetailsVO;
import com.ccit.area.sales.dao.vo.archives.MaterielPageListVO;
import com.ccit.area.sales.service.archives.IMaterielService;
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
 * 描述 : “产品档案”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:52:21
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : MaterielServiceImpl
 */
@Service
public class MaterielServiceImpl extends ServiceImpl<MaterielMapper, MaterielPO> implements IMaterielService {

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
	 * 创建时间 : 2024年7月16日 上午10:53:26
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<MaterielPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<MaterielPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<MaterielPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		Page<MaterielPageListVO> materielPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<MaterielPageListVO> records = materielPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setStatusName(redisUtils.getDict("status_",records.get(i).getStatus()));
			records.get(i).setUnitName(redisUtils.getDict("unit_",records.get(i).getUnit()));
			records.get(i).setMaterielLevelName(redisUtils.getDict("level_",records.get(i).getMaterielLevel()));
			records.get(i).setExecutiveStandardName(redisUtils.getDict("zxbz_",records.get(i).getExecutiveStandard()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:53:29
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(MaterielAddDTO entity) {
		LambdaQueryWrapper<MaterielPO> wrapper = MaterielPO.wrapper();
		wrapper.eq(MaterielPO::getMaterielNo, entity.getMaterielNo());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("产品编码已存在");
		}
		String code = "WL";
		String value = systemSequenceService.get(code, 6);
		MaterielPO materiel = new MaterielPO();
		entity.setMaterielNo(value);
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
	 * 创建时间 : 2024年7月16日 上午10:53:32
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  MaterielDetailsVO  
	 *  @throws
	 */
	@Override
	public MaterielDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("产品档案ID不能为空");
		}
		MaterielPO materiel = this.baseMapper.selectById(id);
		if (null == materiel) {
			throw new BusinessException("获取产品档案失败");
		}
		MaterielDetailsVO result = new MaterielDetailsVO();
		BeanUtils.copyProperties(materiel, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 上午10:53:38
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(MaterielEditDTO entity) {
		LambdaQueryWrapper<MaterielPO> wrapper = MaterielPO.wrapper();
		wrapper.eq(MaterielPO::getMaterielNo, entity.getMaterielNo());
		wrapper.ne(MaterielPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("产品编码已存在");
		}
		MaterielPO materiel = new MaterielPO();
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
	 * 创建时间 : 2024年7月16日 上午10:53:42
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(MaterielDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品档案ID不能为空");
		}
		LambdaQueryWrapper<MaterielPO> wrapper = MaterielPO.wrapper();
		wrapper.in(MaterielPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new MaterielPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
			
}
