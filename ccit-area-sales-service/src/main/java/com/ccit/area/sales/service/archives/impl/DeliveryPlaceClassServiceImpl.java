package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceClassPO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceClassEditDTO;
import com.ccit.area.sales.dao.mapper.archives.DeliveryPlaceClassMapper;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassPageListVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceClassService;
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
 * 描述 : “提货地分类”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:51:18
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : DeliveryPlaceClassServiceImpl
 */
@Service
public class DeliveryPlaceClassServiceImpl extends ServiceImpl<DeliveryPlaceClassMapper, DeliveryPlaceClassPO> implements IDeliveryPlaceClassService {


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
	 * 创建时间 : 2024年8月1日 上午9:29:45
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlaceClassPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<DeliveryPlaceClassPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<DeliveryPlaceClassPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		List<DeliveryPlaceClassPageListVO> deliveryPlaceClassPageListVOS = baseMapper.selectPageList(pages, param);
		if (deliveryPlaceClassPageListVOS != null) {
			deliveryPlaceClassPageListVOS.stream().forEach(t -> {
				t.setStatusName(redisUtils.getDict("status_",t.getStatus()));
			});
		}
		// 执行数据库查询
		pages.setRecords(deliveryPlaceClassPageListVOS);
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:29:48
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(DeliveryPlaceClassAddDTO entity) {
		// 检查是否已存在相同编码
		LambdaQueryWrapper<DeliveryPlaceClassPO> checkWrapper = DeliveryPlaceClassPO.wrapper();
		checkWrapper.eq(DeliveryPlaceClassPO::getDeliveryPlaceClassNo, entity.getDeliveryPlaceClassNo());
		Integer selectCount = this.baseMapper.selectCount(checkWrapper);
		if (selectCount > 0) {
			throw new BusinessException("提货地分类编码已存在");
		}
		String code = "THDFL";
		String value = systemSequenceService.get(code, 6);
		DeliveryPlaceClassPO deliveryPlaceClass = new DeliveryPlaceClassPO();
		entity.setDeliveryPlaceClassNo(value);
		BeanUtils.copyProperties(entity, deliveryPlaceClass);
		// 插入数据库
		int insertFlag = this.baseMapper.insert(deliveryPlaceClass);
		if (insertFlag <= 0) {
			throw new BusinessException("新增失败");
		}

		return "新增成功";
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:29:51
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  DeliveryPlaceClassDetailsVO  
	 *  @throws
	 */
	@Override
	public DeliveryPlaceClassDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("提货地分类ID不能为空");
		}
		DeliveryPlaceClassPO deliveryPlaceClass = this.baseMapper.selectById(id);
		if (null == deliveryPlaceClass) {
			throw new BusinessException("获取提货地分类失败");
		}
		DeliveryPlaceClassDetailsVO result = new DeliveryPlaceClassDetailsVO();
		BeanUtils.copyProperties(deliveryPlaceClass, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:29:53
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(DeliveryPlaceClassEditDTO entity) {
		LambdaQueryWrapper<DeliveryPlaceClassPO> wrapper = DeliveryPlaceClassPO.wrapper();
		wrapper.eq(DeliveryPlaceClassPO::getDeliveryPlaceClassNo, entity.getDeliveryPlaceClassNo());
		wrapper.ne(DeliveryPlaceClassPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("提货地分类编码已存在");
		}
		DeliveryPlaceClassPO deliveryPlaceClass = new DeliveryPlaceClassPO();
		BeanUtils.copyProperties(entity, deliveryPlaceClass);
		int updateByIdFlag = this.baseMapper.updateById(deliveryPlaceClass);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:29:55
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(DeliveryPlaceClassDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("提货地分类ID不能为空");
		}
		LambdaQueryWrapper<DeliveryPlaceClassPO> wrapper = DeliveryPlaceClassPO.wrapper();
		wrapper.in(DeliveryPlaceClassPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new DeliveryPlaceClassPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
	
}
