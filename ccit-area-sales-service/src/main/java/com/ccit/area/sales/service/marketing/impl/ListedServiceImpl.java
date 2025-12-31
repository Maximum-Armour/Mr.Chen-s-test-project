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
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.domain.marketing.PriceSchemeItemPO;
import com.ccit.area.sales.dao.domain.marketing.ProductPriceItemPO;
import com.ccit.area.sales.dao.domain.marketing.ProductPricePO;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.mapper.marketing.ListedMapper;
import com.ccit.area.sales.dao.vo.marketing.ListedDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ListedPageListVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.dto.ExamineApprovalRequests;
import com.ccit.area.sales.dto.RequestsData;
import com.ccit.area.sales.feign.ExamineFeign;
import com.ccit.area.sales.service.marketing.IListedService;
import com.ccit.area.sales.service.marketing.IPriceSchemeItemService;
import com.ccit.area.sales.service.marketing.IProductPriceItemService;
import com.ccit.area.sales.service.marketing.IProductPriceService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.ISystemUserService;
import com.ccit.area.sales.vo.ResultResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 * 描述 : “挂牌表”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:22
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing.impl
 * 类名 : ListedServiceImpl
 */
@Service
public class ListedServiceImpl extends ServiceImpl<ListedMapper, ListedPO> implements IListedService {

	/**
	 * “序列号”服务类
	 */
	@Autowired
	private ISystemSequenceService systemSequenceService;

	@Autowired
	private ExamineFeign examineFeign;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * “产品定价明细”服务类
	 */
	@Autowired
	private IProductPriceItemService productPriceItemService;
	
	/**
	 * “产品定价”服务类
	 */
	@Autowired
	private IProductPriceService productPriceService;
	
	/**
	 * “定价方案明细”服务类
	 */
	@Autowired
	private IPriceSchemeItemService priceSchemeItemService;

	@Autowired
	private ISystemUserService iSystemUserService;

	@Autowired
	private ISystemOrgService isSystemOrgService;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:22
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : selectPageList
	 *  Result<Page<ListedPageListVO>>
	 *  @throws
	 */
	@Override
	public Page<ListedPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<ListedPageListVO> pages = null;
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
		Page<ListedPageListVO> listedPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
		List<ListedPageListVO> records = listedPageListVOPage.getRecords();
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setListedModeName(redisUtils.getDict("listedMode_", records.get(i).getListedMode()));
			records.get(i).setUseStatusName(redisUtils.getDict("useStatus_", records.get(i).getUseStatus()));
			records.get(i).setPayWayName(redisUtils.getDict("zhlx_", records.get(i).getPayWay()));
			records.get(i).setTradeTypeName(redisUtils.getDict("tradeType_", records.get(i).getTradeType()));
			records.get(i).setUnitWeightName(redisUtils.getDict("unit_", records.get(i).getUnitWeight()));
			records.get(i).setUnitName(redisUtils.getDict("priceUnit_", records.get(i).getUnit()));
		}
		return pages;
	}
	
/**
 *
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:23:22
 * 描述 : 新增一条数据
 * 包名 : com.ccit.area.sales.service.marketing.impl
 * 方法名 : add
 *  String
 *  @throws
 */
@Override
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
		Exception.class })
public String add(ListedAddDTO entity) {
	// 获取产品定价明细数组
	List<ListedNoneAddDTO> nodeList = entity.getNodeList();
//	LambdaQueryWrapper<ProductPriceItemPO> wrapper = ProductPriceItemPO.wrapper();
//	wrapper.in(ProductPriceItemPO::getId, nodeList.stream().map(obj -> obj.getId()).collect(Collectors.toList()));
//	List<ProductPriceItemPO> productPriceItemList = productPriceItemService.list(wrapper);
	List<Long> ids = nodeList.stream().map(obj -> obj.getId()).collect(Collectors.toList());
	List<ProductPriceItemPO> productPriceItemList = productPriceItemService.listByIds(ids);
	// 转化{产品定价明细ID, 产品定价编码}集合
	Map<Long, String> productPriceNoMap = productPriceItemList.stream().collect(Collectors
			.toMap(ProductPriceItemPO::getId, ProductPriceItemPO::getProductPriceNo, (key1, key2) -> key1));
	// 转化{产品定价明细ID, 定价方案明细编码}集合
	Map<Long, String> schemeItemNoMap = productPriceItemList.stream().collect(
			Collectors.toMap(ProductPriceItemPO::getId, ProductPriceItemPO::getSchemeItemNo, (key1, key2) -> key1));
	// 转化{产品定价明细编码, 产品定价明细对象}集合
	Map<Long, ProductPriceItemPO> productPriceItemMap = productPriceItemList.stream().collect(Collectors
			.toMap(ProductPriceItemPO::getId, Function.identity(), (oldValue, newValue) -> newValue));
	// 转化产品定价编码数组
	List<String> productPriceNoList = productPriceItemList.stream().map(obj -> obj.getProductPriceNo())
			.collect(Collectors.toList());
	// 转化定价方案明细编码数组
	List<String> schemeItemNoList = productPriceItemList.stream().map(obj -> obj.getSchemeItemNo())
			.collect(Collectors.toList());
	// 获取产品定价数组
	LambdaQueryWrapper<ProductPricePO> productPriceWrapper = ProductPricePO.wrapper();
	productPriceWrapper.in(ProductPricePO::getProductPriceNo, productPriceNoList);
	List<ProductPricePO> productPriceList = productPriceService.list(productPriceWrapper);
	// 转化{产品定价编码, 产品定价对象}集合
	Map<String, ProductPricePO> productPriceMap = productPriceList.stream().collect(Collectors
			.toMap(ProductPricePO::getProductPriceNo, Function.identity(), (oldValue, newValue) -> newValue));
	// 获取定价方案明细数组
	LambdaQueryWrapper<PriceSchemeItemPO> priceSchemeWrapper = PriceSchemeItemPO.wrapper();
	priceSchemeWrapper.in(PriceSchemeItemPO::getSchemeItemNo, schemeItemNoList);
	List<PriceSchemeItemPO> priceSchemeItemList = priceSchemeItemService.list(priceSchemeWrapper);
	// 转化{定价方案明细编码, 定价方案明细对象}集合
	Map<String, PriceSchemeItemPO> priceSchemeItemMap = priceSchemeItemList.stream().collect(Collectors
			.toMap(PriceSchemeItemPO::getSchemeItemNo, Function.identity(), (oldValue, newValue) -> newValue));
	// 执行批量新增挂牌信息
	List<ListedPO> entityList = new ArrayList<>();
	String orgNo = entity.getOrgNo();
	String s = this.baseMapper.orgNoAbbreviation(orgNo);
	String[] split = s.split("_");
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	String formattedDate = dateFormat.format(date);
	String code = split[0]+"GPO"+formattedDate;
	for (int i = 0; i < nodeList.size(); i++) {
		String value = systemSequenceService.get(code, 4);
		ListedPO listed = new ListedPO();
		listed.setListedNo(value);
		listed.setListedMode(nodeList.get(i).getListedMode());
		String productPriceNo = productPriceNoMap.get(nodeList.get(i).getId());
		ProductPricePO productPrice = productPriceMap.get(productPriceNo);
		if (productPrice != null) {
			BeanUtils.copyProperties(productPrice, listed);
			listed.setPriceModel(productPrice.getUnivalenceModel());
		}
		String schemeItemNo = schemeItemNoMap.get(nodeList.get(i).getId());
		PriceSchemeItemPO priceSchemeItem = priceSchemeItemMap.get(schemeItemNo);
		if (priceSchemeItem != null) {
			BeanUtils.copyProperties(priceSchemeItem, listed);
			listed.setUnitWeight(priceSchemeItem.getUnit());
		}
		ProductPriceItemPO productPriceItem = productPriceItemMap.get(nodeList.get(i).getId());
		if (productPriceItem != null) {
			BeanUtils.copyProperties(productPriceItem, listed);
			listed.setListedPrice(productPriceItem.getPrice());
			listed.setListedTaxPrice(productPriceItem.getTaxPrice());
			listed.setUnit(productPriceItem.getCurrencyUnit());
		}
		SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(orgNo);
		listed.setCompanyNo(fatherOrgNo.getOrgNo());
		listed.setCompanyName(fatherOrgNo.getOrgName());
		listed.setId(null);
		listed.setStatus("未提交");
		entityList.add(listed);
	}
	Boolean saveBatch = this.saveBatch(entityList);
	if (saveBatch) {
		return "新增成功";
	}
	throw new BusinessException("新增失败");
}

	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:22
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : get
	 *  ListedDetailsVO
	 *  @throws
	 */
	@Override
	public ListedDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("挂牌ID不能为空");
		}
		ListedPO listed = this.baseMapper.selectById(id);
		if (null == listed) {
			throw new BusinessException("获取挂牌失败");
		}
		ListedDetailsVO result = new ListedDetailsVO();
		BeanUtils.copyProperties(listed, result);
		return result;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:22
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : edit
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(ListedEditDTO entity) {
		ListedPO listed = new ListedPO();
		BeanUtils.copyProperties(entity, listed);
		int updateByIdFlag = this.baseMapper.updateById(listed);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:23:22
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : delete
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(ListedDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("挂牌ID不能为空");
		}
		LambdaQueryWrapper<ListedPO> wrapper = ListedPO.wrapper();
		wrapper.in(ListedPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new ListedPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 下午04:23:22
	 * 描述 : 挂牌提交
	 * 包名 : com.ccit.area.sales.service.marketing.impl
	 * 方法名 : submit
	 *  String
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String submit(ListedSubmitDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品挂牌ID不能为空");
		}
		LambdaQueryWrapper<ListedPO> wrapper = ListedPO.wrapper();
		wrapper.in(ListedPO::getId, entity.getIds());
		ListedPO listedPO = new ListedPO();
		if (entity.getDifference().equals("0")){
			ExamineApprovalRequests examineApprovalRequests = new ExamineApprovalRequests();
			RequestsData requestsData = new RequestsData();
			requestsData.setIds(entity.getIds());
			examineApprovalRequests.setData(requestsData);
			examineApprovalRequests.setBusinessName("挂牌管理_提交");
			examineApprovalRequests.setDatabaseName("t_listed");
			ResponseEntity<ResultResponse> resultResponseResponseEntity = examineFeign.examineApproval(examineApprovalRequests);
			if (resultResponseResponseEntity.getBody().getCode().equals("200")){
				listedPO.setListedStatus("审核中");
				int updateFlag = this.baseMapper.update(listedPO, wrapper);
				if (updateFlag > 0) {
					return "提交成功";
				}
			}else {
				return "提交失败";
			}
			throw new BusinessException("提交失败");
		}else if (entity.getDifference().equals("1")){
			listedPO.setUseStatus("YSJ");
			int updateFlag = this.baseMapper.update(listedPO, wrapper);
			if (updateFlag > 0) {
					return "上架成功";
			}
			throw new BusinessException("上架失败");
		}else {
			listedPO.setUseStatus("YXJ");
			int updateFlag = this.baseMapper.update(listedPO, wrapper);
			if (updateFlag > 0) {
				return "下架成功";
			}
			throw new BusinessException("下架失败");
		}
	}

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月18日 下午03:46:35
	 * 描述 : 挂牌管理提交钉钉成功后续操作
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : listedStatus
	 *  void
	 *  @throws
	 */
	@Override
	public void listedStatus(RabbitApprovalRequest rabbitApprovalRequest) {
		this.baseMapper.listedStatus(rabbitApprovalRequest);
	}
}
