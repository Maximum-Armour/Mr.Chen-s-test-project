package com.ccit.area.sales.service.archives.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceClassPO;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlacePO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotAddDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotnoVO;
import com.ccit.common.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceDepotPO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotDeleteDTO;
import com.ccit.area.sales.dao.mapper.archives.DeliveryPlaceDepotMapper;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotPageListVO;
import com.ccit.area.sales.service.archives.IDeliveryPlaceDepotService;
import org.springframework.util.StringUtils;

/**
 * 
 * 描述 : “提货地仓库中间表”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午1:51:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : DeliveryPlaceDepotServiceImpl
 */
@Service
public class DeliveryPlaceDepotServiceImpl extends ServiceImpl<DeliveryPlaceDepotMapper, DeliveryPlaceDepotPO> implements IDeliveryPlaceDepotService {



	@Autowired
	private RedisUtils redisUtils;
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午1:53:16
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlaceDepotPageListVO>
	 *  @throws
	 */
	@Override
	public Page<DeliveryPlaceDepotPageListVO> selectPageList(Map<String, Object> param) {
			Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<DeliveryPlaceDepotPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		List<DeliveryPlaceDepotPageListVO> deliveryPlaceDepotPageListVOS = baseMapper.selectPageList(pages, param);
		if (deliveryPlaceDepotPageListVOS != null) {
			deliveryPlaceDepotPageListVOS.stream().forEach(t -> {
				t.setStatusName(redisUtils.getDict("status_",t.getStatus()));
			});
		}

		// 执行数据库查询
		pages.setRecords(deliveryPlaceDepotPageListVOS);
		return pages;
	}



	/**
	 *
	 * 创建人 : cf
	 * 创建时间 : 2024年11月5日 上午8:49:11
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(DeliveryPlaceDepotAddDTO entity) {

		// 检查传入的 depotNo 列表是否为空
		List<String> depotNoList = entity.getDepotNo();
		// 将 depotNo 列表转换为逗号分隔的字符串
		String depotNoStr = String.join(",", depotNoList);

		// 更新数据库
		int updateResult = baseMapper.updatedeliveryPlaceNo(entity.getDeliveryPlaceNo(), depotNoStr);
		if (updateResult > 0) {
			return "修改成功";
		}

		// 如果更新失败，尝试插入新记录
		DeliveryPlaceDepotPO deliveryPlaceDepot = new DeliveryPlaceDepotPO();
		BeanUtils.copyProperties(entity, deliveryPlaceDepot);
		deliveryPlaceDepot.setDepotNo(depotNoStr); // 假设 DeliveryPlaceDepotPO 也有 depotNo 字符串属性

		int insertFlag = this.baseMapper.insert(deliveryPlaceDepot);
		if (insertFlag > 0) {
			return "新增成功";
		}

		throw new BusinessException("修改失败");

	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:11:11
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(DeliveryPlaceDepotDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("提货地仓库ID不能为空");
		}
		LambdaQueryWrapper<DeliveryPlaceDepotPO> wrapper = DeliveryPlaceDepotPO.wrapper();
		wrapper.in(DeliveryPlaceDepotPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new DeliveryPlaceDepotPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}




	/**
	 *
	 * 创建人 : cf
	 * 创建时间 : 2024年11月5日 上午9:38:11
	 * 描述 : 查询数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectdepotNo
	 *  String
	 *  @throws
	 */
	@Override
	public List<String> selectdepotNo(DeliveryPlaceDepotAddDTO entity) {

		if(StringUtil.isBlank(entity.getDeliveryPlaceNo())){

			throw new BusinessException("提货地编码不能为空");
		}
		List<String> strings = this.baseMapper.selectdepotNo(entity);
		List<String> flattenedList = Optional.ofNullable(strings)
				.orElse(Collections.emptyList())
				.stream()
				.filter(Objects::nonNull)
				.flatMap(depotNoStr -> Arrays.stream(depotNoStr.split(",")))
				.distinct() // 去重
				.collect(Collectors.toList());

		return flattenedList;
	}

}
