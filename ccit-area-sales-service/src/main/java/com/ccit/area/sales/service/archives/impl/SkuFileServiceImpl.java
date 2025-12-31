package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.SkuFilePO;
import com.ccit.area.sales.dao.dto.archives.SkuFileAddDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.SkuFileEditDTO;
import com.ccit.area.sales.dao.mapper.archives.SkuFileMapper;
import com.ccit.area.sales.dao.vo.archives.SkuFileDetailsVO;
import com.ccit.area.sales.dao.vo.archives.SkuFilePageListVO;
import com.ccit.area.sales.service.archives.ISkuFileService;
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
 * 描述 : “牌号档案”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:51:16
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : SkuFileServiceImpl
 */
@Service
public class SkuFileServiceImpl extends ServiceImpl<SkuFileMapper, SkuFilePO> implements ISkuFileService {

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
	 * 创建时间 : 2024年7月17日 下午1:52:06
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<SkuFilePageListVO>  
	 *  @throws
	 */
	@Override
	public Page<SkuFilePageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<SkuFilePageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		Page<SkuFilePageListVO> skuFilePageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<SkuFilePageListVO> records = skuFilePageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setStatusName(redisUtils.getDict("status_",records.get(i).getStatus()));
			records.get(i).setMainOrByproductName(redisUtils.getDict("zfproduct_",records.get(i).getMainOrByproduct()));
			records.get(i).setExecutiveStandardName(redisUtils.getDict("zxbz_",records.get(i).getExecutiveStandard()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:52:10
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SkuFileAddDTO entity) {
		LambdaQueryWrapper<SkuFilePO> wrapper = SkuFilePO.wrapper();
		wrapper.eq(SkuFilePO::getSkuName, entity.getSkuName());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("牌号名称已存在");
		}
		String code = "PH";
		String value = systemSequenceService.get(code, 6);
		SkuFilePO materiel = new SkuFilePO();
		entity.setSkuNo(value);
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
	 * 创建时间 : 2024年7月17日 下午1:52:12
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  SkuFileDetailsVO  
	 *  @throws
	 */
	@Override
	public SkuFileDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("牌号ID不能为空");
		}
		SkuFilePO skuFile = this.baseMapper.selectById(id);
		if (null == skuFile) {
			throw new BusinessException("获取牌号失败");
		}
		SkuFileDetailsVO result = new SkuFileDetailsVO();
		BeanUtils.copyProperties(skuFile, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午1:52:15
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SkuFileEditDTO entity) {
		LambdaQueryWrapper<SkuFilePO> wrapper = SkuFilePO.wrapper();
		wrapper.eq(SkuFilePO::getSkuName, entity.getSkuName());
		wrapper.ne(SkuFilePO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("牌号名称已存在");
		}
		SkuFilePO materiel = new SkuFilePO();
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
	 * 创建时间 : 2024年7月17日 下午1:52:17
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SkuFileDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("牌号ID不能为空");
		}
		LambdaQueryWrapper<SkuFilePO> wrapper = SkuFilePO.wrapper();
		wrapper.in(SkuFilePO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new SkuFilePO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
			
}
