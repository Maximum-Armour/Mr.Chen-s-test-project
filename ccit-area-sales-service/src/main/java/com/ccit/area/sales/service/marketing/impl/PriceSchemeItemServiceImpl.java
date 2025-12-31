package com.ccit.area.sales.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemeItemPO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.PriceSchemeItemPageListDTO;
import com.ccit.area.sales.dao.mapper.marketing.PriceSchemeItemMapper;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.marketing.IPriceSchemeItemService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “定价方案明细”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年8月8日 下午4:14:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing.impl
 * 类名 : PriceSchemeItemServiceImpl
 */
@Service
public class PriceSchemeItemServiceImpl extends ServiceImpl<PriceSchemeItemMapper, PriceSchemeItemPO> implements IPriceSchemeItemService {

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ISystemUserService iSystemUserService;

	@Autowired
	private ISystemOrgService isSystemOrgService;

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 上午8:59:41
	 * 描述 : 定价方案明细列表
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : selectPageList
	 *  List<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	@Override
	public List<PriceSchemeItemPageListVO> selectPageList(PriceSchemeItemPageListDTO entity) {
		List<PriceSchemeItemPageListVO> priceSchemeItemPageListVOS = this.baseMapper.selectPageList(entity);
		for (int i = 0; i < priceSchemeItemPageListVOS.size(); i++) {
			priceSchemeItemPageListVOS.get(i).setDistributionWayName(redisUtils.getDict("psfs_",priceSchemeItemPageListVOS.get(i).getDistributionWay()));
			priceSchemeItemPageListVOS.get(i).setTransportWayName(redisUtils.getDict("ysfs_",priceSchemeItemPageListVOS.get(i).getTransportWay()));
			priceSchemeItemPageListVOS.get(i).setPayWayName(redisUtils.getDict("zhlx_",priceSchemeItemPageListVOS.get(i).getPayWay()));
			priceSchemeItemPageListVOS.get(i).setCurrencyName(redisUtils.getDict("currency_",priceSchemeItemPageListVOS.get(i).getCurrency()));
			priceSchemeItemPageListVOS.get(i).setUnitName(redisUtils.getDict("unit_",priceSchemeItemPageListVOS.get(i).getUnit()));
		}
		return priceSchemeItemPageListVOS;
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 上午8:59:41
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : selectPageList
	 *  Page<PriceSchemeItemPageListVO>
	 *  @throws
	 */
	@Override
	public Page<PriceSchemeItemPageListVO> selectItemList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<PriceSchemeItemPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		SystemCurrentUserVO systemUser = null;
		try {
			JSONObject userJson = CurrentUserUtil.getCurrentUser();
			systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
			String userName = systemUser.getUserName();
			if (userName.equals("sysadmin")){
				param.put("userName", "");
				param.put("orgNo", "");
			}else {
				SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
				List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
				param.put("orgNoList", existOrgNo);
			}
		} catch (Exception e) {
			throw new BusinessException("用户不存在!");
		}
		Page<PriceSchemeItemPageListVO> priceSchemeItemPageListVOPage = pages.setRecords(this.baseMapper.selectItemList(pages, param));
		List<PriceSchemeItemPageListVO> records = priceSchemeItemPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setDistributionWayName(redisUtils.getDict("psfs_",records.get(i).getDistributionWay()));
			records.get(i).setTransportWayName(redisUtils.getDict("ysfs_",records.get(i).getTransportWay()));
			records.get(i).setPayWayName(redisUtils.getDict("zhlx_",records.get(i).getPayWay()));
			records.get(i).setCurrencyName(redisUtils.getDict("currency_",records.get(i).getCurrency()));
			records.get(i).setUnitName(redisUtils.getDict("unit_",records.get(i).getUnit()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 上午8:59:47
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(PriceSchemeItemDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("方案明细ID不能为空");
		}
		LambdaQueryWrapper<PriceSchemeItemPO> wrapper = PriceSchemeItemPO.wrapper();
		wrapper.in(PriceSchemeItemPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new PriceSchemeItemPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月04日 下午03:23:23
	 * 描述 : 修改/新增数据
	 * 包名 : com.ccit.area.sales.service.bidding.impl
	 * 方法名 : edit
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(PriceSchemeItemEditDTO entity) {
		Long id = entity.getId();
		if (id == null) {
			String schemeNo = entity.getSchemeNo();
			String schemeItemNo = entity.getSchemeItemNo();
			String areaName = entity.getAreaName();
			String deliveryPlaceName = entity.getDeliveryPlaceName();
			String deliveryPlaceClassName = entity.getDeliveryPlaceClassName();
			String distributionWay = entity.getDistributionWay();
			String transportWay = entity.getTransportWay();
			String payWay = entity.getPayWay();
			String currency = entity.getCurrency();
			String unit = entity.getUnit();
			this.baseMapper.insertPriceSchemeItem(schemeNo,schemeItemNo,areaName,deliveryPlaceName,deliveryPlaceClassName,distributionWay,transportWay,payWay,currency,unit,new Date(),new Date());
		}else {
			PriceSchemeItemPO priceSchemeItemPO = new PriceSchemeItemPO();
			BeanUtils.copyProperties(entity, priceSchemeItemPO);
			this.baseMapper.updateById(priceSchemeItemPO);
		}
		return "保存成功";
	}
}
