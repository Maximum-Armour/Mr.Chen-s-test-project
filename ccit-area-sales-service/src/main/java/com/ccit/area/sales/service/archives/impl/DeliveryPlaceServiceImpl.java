package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceDepotPO;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlacePO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceEditDTO;
import com.ccit.area.sales.dao.mapper.archives.DeliveryPlaceMapper;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDetailsVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlacePageListVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceDepotService;
import com.ccit.area.sales.service.archives.IDeliveryPlaceService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.common.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “提货地档案”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:51:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : DeliveryPlaceServiceImpl
 */
@Service
public class DeliveryPlaceServiceImpl extends ServiceImpl<DeliveryPlaceMapper, DeliveryPlacePO> implements IDeliveryPlaceService {

	/**
	 * “提货地仓库中间表”服务类
	 */
	@Autowired
	private IDeliveryPlaceDepotService deliveryPlaceDepotService;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * “序列号”服务类
	 */
	@Autowired
	private ISystemSequenceService systemSequenceService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午10:16:00
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlacePageListVO>  
	 *  @throws
	 */
	@Override
	public Page<DeliveryPlacePageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<DeliveryPlacePageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		List<DeliveryPlacePageListVO> deliveryPlacePageListVOPage = baseMapper.selectPageList(pages, param);
		if (deliveryPlacePageListVOPage != null) {
			deliveryPlacePageListVOPage.stream().forEach(t -> {
				t.setStatusName(redisUtils.getDict("status_",t.getStatus()));
			});
		}

		// 执行数据库查询
		pages.setRecords(deliveryPlacePageListVOPage);

		return pages;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午10:14:23
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(DeliveryPlaceAddDTO entity) {
		// 检查是否已存在相同编码
		LambdaQueryWrapper<DeliveryPlacePO> checkWrapper = DeliveryPlacePO.wrapper();
		checkWrapper.eq(DeliveryPlacePO::getDeliveryPlaceNo, entity.getDeliveryPlaceNo());
		Integer selectCount = this.baseMapper.selectCount(checkWrapper);
		if (selectCount > 0) {
			throw new BusinessException("提货地编码已存在");
		}
		String code = "THD";
		String value = systemSequenceService.get(code, 6);
		DeliveryPlacePO deliveryPlace = new DeliveryPlacePO();
        entity.setDeliveryPlaceNo(value);
		BeanUtils.copyProperties(entity, deliveryPlace);
		// 插入数据库
		int insertFlag = this.baseMapper.insert(deliveryPlace);
		if (insertFlag <= 0) {
			throw new BusinessException("新增失败");
		}

		return "新增成功";
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午10:13:22
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  DeliveryPlaceDetailsVO  
	 *  @throws
	 */
	@Override
	public DeliveryPlaceDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("提货地档案ID不能为空");
		}
		DeliveryPlacePO deliveryPlace = this.baseMapper.selectById(id);
		if (null == deliveryPlace) {
			throw new BusinessException("获取提货地档案失败");
		}
		DeliveryPlaceDetailsVO result = new DeliveryPlaceDetailsVO();
		BeanUtils.copyProperties(deliveryPlace, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:52:52
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(DeliveryPlaceEditDTO entity) {
		LambdaQueryWrapper<DeliveryPlacePO> wrapper = DeliveryPlacePO.wrapper();
		wrapper.eq(DeliveryPlacePO::getDeliveryPlaceNo, entity.getDeliveryPlaceNo());
		wrapper.ne(DeliveryPlacePO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("提货地编码已存在");
		}
		DeliveryPlacePO deliveryPlace = new DeliveryPlacePO();
		BeanUtils.copyProperties(entity, deliveryPlace);
		int updateByIdFlag = this.baseMapper.updateById(deliveryPlace);
		if (updateByIdFlag > 0) {
			saveDepot(entity.getDeliveryPlaceNo(), entity.getDepotNos());
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:33:43
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(DeliveryPlaceDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("提货地档案ID不能为空");
		}
		LambdaQueryWrapper<DeliveryPlacePO> wrapper = DeliveryPlacePO.wrapper();
		wrapper.in(DeliveryPlacePO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new DeliveryPlacePO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:48:24
	 * 描述 : 保存仓库档案（私有方法）
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : saveDepot
	 *  void  
	 *  @throws
	 */
	private void saveDepot(String deliveryPlaceNo, String depotNos) {
		if (StringUtil.isNotBlank(depotNos)) {
			List<DeliveryPlaceDepotPO> entityList = new ArrayList<>();
			List<String> depotNoList = Arrays.asList(depotNos.split(","));
			for (String depotNo : depotNoList) {
				DeliveryPlaceDepotPO entity = new DeliveryPlaceDepotPO();
				entity.setDepotNo(depotNo);
				entity.setDeliveryPlaceNo(deliveryPlaceNo);
				entityList.add(entity);
			}
			deliveryPlaceDepotService.saveBatch(entityList);
		}
	}
	
}
