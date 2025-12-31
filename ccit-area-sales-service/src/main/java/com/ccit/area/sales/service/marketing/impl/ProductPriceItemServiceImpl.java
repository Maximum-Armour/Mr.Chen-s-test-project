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
import com.ccit.area.sales.dao.domain.marketing.ProductPriceItemPO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ProductPriceItemPageListDTO;
import com.ccit.area.sales.dao.mapper.marketing.ProductPriceItemMapper;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.marketing.IProductPriceItemService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
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
 * 描述 : “产品定价明细”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:23
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing.impl
 * 类名 : ProductPriceItemServiceImpl
 */
@Service
public class ProductPriceItemServiceImpl extends ServiceImpl<ProductPriceItemMapper, ProductPriceItemPO> implements IProductPriceItemService {

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ISystemUserService iSystemUserService;

	@Autowired
	private ISystemOrgService isSystemOrgService;

	/**
	 * 
	 * 创建人 : tb
	 * 创建时间 : 2024年11月11日 下午04:23:23
	 * 描述 : 产品定价明细列表
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : selectPageList
	 *  List<ProductPriceItemPageListVO>
	 *  @throws
	 */
	@Override
	public List<ProductPriceItemPageListVO> selectPageList(ProductPriceItemPageListDTO entity) {
		List<ProductPriceItemPageListVO> productPriceItemPageListVOS = this.baseMapper.selectPageList(entity);
		for (int i = 0; i < productPriceItemPageListVOS.size(); i++) {
			productPriceItemPageListVOS.get(i).setListedModeName(redisUtils.getDict("listedMode_",productPriceItemPageListVOS.get(i).getListedMode()));
			productPriceItemPageListVOS.get(i).setCurrencyUnitName(redisUtils.getDict("priceUnit_",productPriceItemPageListVOS.get(i).getCurrencyUnit()));
			productPriceItemPageListVOS.get(i).setCurrencyName(redisUtils.getDict("currency_",productPriceItemPageListVOS.get(i).getCurrency()));
			productPriceItemPageListVOS.get(i).setTradeTypeName(redisUtils.getDict("tradeType_",productPriceItemPageListVOS.get(i).getTradeType()));
			productPriceItemPageListVOS.get(i).setUnivalenceModelName(redisUtils.getDict("djms_",productPriceItemPageListVOS.get(i).getUnivalenceModel()));
			productPriceItemPageListVOS.get(i).setBusinessTypeName(redisUtils.getDict("businessType_",productPriceItemPageListVOS.get(i).getBusinessType()));
			productPriceItemPageListVOS.get(i).setDistributionWayName(redisUtils.getDict("psfs_",productPriceItemPageListVOS.get(i).getDistributionWay()));
			productPriceItemPageListVOS.get(i).setTransportWayName(redisUtils.getDict("ysfs_",productPriceItemPageListVOS.get(i).getTransportWay()));
			productPriceItemPageListVOS.get(i).setPayWayName(redisUtils.getDict("zhlx_",productPriceItemPageListVOS.get(i).getPayWay()));
		}
		return productPriceItemPageListVOS;
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月13日 下午04:23:23
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : selectPageList
	 *  Result<Page<ProductPriceItemPageListVO>>
	 *  @throws
	 */
	@Override
	public Page<ProductPriceItemPageListVO> selectList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<ProductPriceItemPageListVO> pages = null;
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
		Page<ProductPriceItemPageListVO> productPriceItemPageListVOPage = pages.setRecords(this.baseMapper.selectList(pages, param));
		List<ProductPriceItemPageListVO> records = productPriceItemPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setListedModeName(redisUtils.getDict("listedMode_",records.get(i).getListedMode()));
			records.get(i).setCurrencyUnitName(redisUtils.getDict("priceUnit_",records.get(i).getCurrencyUnit()));
			records.get(i).setCurrencyName(redisUtils.getDict("currency_",records.get(i).getCurrency()));
			records.get(i).setTradeTypeName(redisUtils.getDict("tradeType_",records.get(i).getTradeType()));
			records.get(i).setUnivalenceModelName(redisUtils.getDict("djms_",records.get(i).getUnivalenceModel()));
			records.get(i).setBusinessTypeName(redisUtils.getDict("businessType_",records.get(i).getBusinessType()));
			records.get(i).setDistributionWayName(redisUtils.getDict("psfs_",records.get(i).getDistributionWay()));
			records.get(i).setTransportWayName(redisUtils.getDict("ysfs_",records.get(i).getTransportWay()));
			records.get(i).setPayWayName(redisUtils.getDict("zhlx_",records.get(i).getPayWay()));
		}
		return pages;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月16日 下午4:24:12
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(ProductPriceItemEditDTO entity) {
		ProductPriceItemPO productPriceItem = new ProductPriceItemPO();
		BeanUtils.copyProperties(entity, productPriceItem);
		int updateByIdFlag = this.baseMapper.updateById(productPriceItem);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:23
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : delete
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(ProductPriceItemDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品定价明细ID不能为空");
		}
		LambdaQueryWrapper<ProductPriceItemPO> wrapper = ProductPriceItemPO.wrapper();
		wrapper.in(ProductPriceItemPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new ProductPriceItemPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

}
