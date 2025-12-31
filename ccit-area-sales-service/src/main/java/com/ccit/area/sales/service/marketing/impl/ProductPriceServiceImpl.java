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
import com.ccit.area.sales.dao.domain.marketing.ProductPricePO;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.mapper.marketing.ProductPriceMapper;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceItemPageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPricePageListVO;
import com.ccit.area.sales.dao.vo.marketing.ProductPriceSelectVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.marketing.IProductPriceItemService;
import com.ccit.area.sales.service.marketing.IProductPriceService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.ISystemUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  * 描述 : “产品定价”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年08月13日 下午04:23:23
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.marketing.impl
 *  * 类名 : ProductPriceServiceImpl
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPricePO> implements IProductPriceService {

    /**
     * “序列号”服务类
     */
    @Autowired
    private ISystemSequenceService systemSequenceService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * “产品定价明细”服务类
     */
    @Autowired
    private IProductPriceItemService productPriceItemService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ISystemOrgService isSystemOrgService;

    /**
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : selectPageList
     * Result<Page<ProductPricePageListVO>>
     *
     * @throws
     */
    @Override
    public Page<ProductPricePageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<ProductPricePageListVO> pages = null;
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
            if (userName.equals("sysadmin")) {
                param.put("userName", "");
                param.put("orgNo", "");
            } else {
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
                List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
                param.put("orgNoList", existOrgNo);
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }
        Page<ProductPricePageListVO> productPricePageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
        List<ProductPricePageListVO> records = productPricePageListVOPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setCurrencyName(redisUtils.getDict("currency_", records.get(i).getCurrency()));
            records.get(i).setTradeTypeName(redisUtils.getDict("tradeType_", records.get(i).getTradeType()));
            records.get(i).setUnivalenceModelName(redisUtils.getDict("djms_", records.get(i).getUnivalenceModel()));
        }
        return pages;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : add
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String add(ProductPriceAddDTO entity) {
        LambdaQueryWrapper<ProductPricePO> wrapper = ProductPricePO.wrapper();
        wrapper.eq(ProductPricePO::getProductPriceNo, entity.getProductPriceNo());
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount > 0) {
            throw new BusinessException("产品定价编码已存在，请重新输入！");
        }
        String orgNo = entity.getOrgNo();
        String s = this.baseMapper.orgNoAbbreviation(orgNo);
        String[] split = s.split("_");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        String code = split[0] + "CDO" + formattedDate;
        String value = systemSequenceService.get(code, 4);
        ProductPricePO productPrice = new ProductPricePO();
        entity.setProductPriceNo(value);
        List<ProductPriceItemAddDTO> itemAddList = entity.getItemAddList();
        String productPriceNo1 = entity.getProductPriceNo();
        for (int i = 0; i < itemAddList.size(); i++) {
            String value1 = systemSequenceService.get(productPriceNo1, 4);
            itemAddList.get(i).setProductPriceItemNo(value1);
            String depotNo = itemAddList.get(i).getDepotNo();
            String s1 = this.baseMapper.depotName(depotNo);
            itemAddList.get(i).setDepotName(s1);
        }
        SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(orgNo);
        productPrice.setCompanyNo(fatherOrgNo.getOrgNo());
        productPrice.setCompanyName(fatherOrgNo.getOrgName());
        BeanUtils.copyProperties(entity, productPrice);
        int insertFlag = this.baseMapper.insert(productPrice);
        if (insertFlag > 0) {
            this.saveItem(entity.getProductPriceNo(), entity.getItemAddList());
            return "新增成功";
        }
        throw new BusinessException("新增失败");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : get
     * ProductPriceDetailsVO
     *
     * @throws
     */
    @Override
    public ProductPriceDetailsVO get(Long id) {
        if (null == id) {
            throw new BusinessException("产品定价ID不能为空");
        }
        ProductPricePO productPrice = this.baseMapper.selectById(id);
        if (null == productPrice) {
            throw new BusinessException("获取产品定价失败");
        }
        ProductPriceDetailsVO result = new ProductPriceDetailsVO();
        BeanUtils.copyProperties(productPrice, result);
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(ProductPriceEditDTO entity) {
        LambdaQueryWrapper<ProductPricePO> wrapper = ProductPricePO.wrapper();
        wrapper.eq(ProductPricePO::getProductPriceNo, entity.getProductPriceNo());
        wrapper.ne(ProductPricePO::getId, entity.getId());
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount > 0) {
            throw new BusinessException("产品定价编码已存在，请重新输入！");
        }
        ProductPricePO productPrice = new ProductPricePO();
        BeanUtils.copyProperties(entity, productPrice);
        int updateByIdFlag = this.baseMapper.updateById(productPrice);
        if (updateByIdFlag > 0) {
            List<ProductPriceItemListDTO> itemList = entity.getItemList();
            for (int i = 0; i < itemList.size(); i++) {
                Long id = itemList.get(i).getId();
                if (id == null) {
                    String productPriceNo1 = entity.getProductPriceNo();
                    String value = systemSequenceService.get(productPriceNo1, 4);
                    itemList.get(i).setProductPriceItemNo(value);
                    itemList.get(i).setGmtCreate(new Date());
                    itemList.get(i).setGmtModified(new Date());
                    ProductPriceItemPO productPriceItem = new ProductPriceItemPO();
                    String depotNo = itemList.get(i).getDepotNo();
                    String s1 = this.baseMapper.depotName(depotNo);
                    itemList.get(i).setDepotName(s1);
                    BeanUtils.copyProperties(itemList.get(i), productPriceItem);
                    this.baseMapper.insertItem(productPriceItem);
                } else {
                    ProductPriceItemPO productPriceItem = new ProductPriceItemPO();
                    BeanUtils.copyProperties(itemList.get(i), productPriceItem);
                    this.baseMapper.updateItem(productPriceItem);
                }
            }
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年08月13日 下午04:23:23
     * 描述 : 删除【一条&多条】数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : delete
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String delete(ProductPriceDeleteDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("产品定价ID不能为空");
        }
        LambdaQueryWrapper<ProductPricePO> wrapper = ProductPricePO.wrapper();
        wrapper.in(ProductPricePO::getId, entity.getIds());
        int updateFlag = this.baseMapper.update(new ProductPricePO(true, false), wrapper);
        if (updateFlag > 0) {
            return "删除成功";
        }
        throw new BusinessException("删除失败");
    }


    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月12日 下午04:23:23
     * 描述 : 提交数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : submit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String submit(ProductPriceSubmitDTO entity) {
//		if (CollectionUtils.isEmpty(entity.getIds())) {
//			throw new BusinessException("产品定价ID不能为空");
//		}
//		ExamineApprovalRequests examineApprovalRequests = new ExamineApprovalRequests();
//		RequestsData requestsData = new RequestsData();
//		requestsData.setIds(entity.getIds());
//		examineApprovalRequests.setData(requestsData);
//		examineApprovalRequests.setBusinessName("产品定价管理_提交");
//		examineApprovalRequests.setDatabaseName("t_product_price");
//		ResponseEntity<ResultResponse> resultResponseResponseEntity = examineFeign.examineApproval(examineApprovalRequests);
//		if (resultResponseResponseEntity.getBody().getCode().equals("200")){
////			this.baseMapper.serialNumber(resultResponseResponseEntity.getBody().getSerialNumber());
//			LambdaQueryWrapper<ProductPricePO> wrapper = ProductPricePO.wrapper();
//			wrapper.in(ProductPricePO::getId, entity.getIds());
//			ProductPricePO productPricePO = new ProductPricePO();
//			productPricePO.setShbz("审核中");
//			int updateFlag = this.baseMapper.update(productPricePO, wrapper);
//			if (updateFlag > 0) {
        return "提交成功";
//			}
//		}else {
//			return "提交失败";
//		}
//		throw new BusinessException("提交失败");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月12日 下午04:23:23
     * 描述 : 审核数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : submit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String examine(ProductPriceSubmitDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("定价方案ID不能为空");
        }
        String sign = entity.getSign();
        LambdaQueryWrapper<ProductPricePO> wrapper = ProductPricePO.wrapper();
        wrapper.eq(ProductPricePO::getId, entity.getIds());
        ProductPricePO productPricePO = new ProductPricePO();
        if (sign == "1") {
            productPricePO.setShbz("审核通过");
        } else {
            productPricePO.setShbz("审核拒绝");
        }
        int updateFlag = this.baseMapper.update(productPricePO, wrapper);
        if (updateFlag > 0) {
            return "审核成功";
        } else {
            return "审核失败";
        }
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月24日 下午04:05:05
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.marketing
     * 方法名 : select
     * ProductPriceSelectVO
     *
     * @throws
     */
    @Override
    public ProductPriceSelectVO select(Long id) {
        if (null == id) {
            throw new BusinessException("产品定价ID不能为空");
        }
        ProductPricePO productPrice = this.baseMapper.selectById(id);
        if (null == productPrice) {
            throw new BusinessException("获取产品定价失败");
        }
        ProductPriceSelectVO result = new ProductPriceSelectVO();
        BeanUtils.copyProperties(productPrice, result);
        result.setCurrencyName(redisUtils.getDict("currency_", productPrice.getCurrency()));
        result.setTradeTypeName(redisUtils.getDict("tradeType_", productPrice.getTradeType()));
        result.setUnivalenceModelName(redisUtils.getDict("djms_", productPrice.getUnivalenceModel()));
        result.setBusinessTypeName(redisUtils.getDict("businessType_", productPrice.getBusinessType()));
        String productPriceNo = productPrice.getProductPriceNo();
        List<ProductPriceItemPageListVO> productPriceItemPageListVOS = this.baseMapper.selectItem(productPriceNo);
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < productPriceItemPageListVOS.size(); i++) {
            productPriceItemPageListVOS.get(i).setDistributionWayName(redisUtils.getDict("psfs_", productPriceItemPageListVOS.get(i).getDistributionWay()));
            productPriceItemPageListVOS.get(i).setTransportWayName(redisUtils.getDict("ysfs_", productPriceItemPageListVOS.get(i).getTransportWay()));
            productPriceItemPageListVOS.get(i).setPayWayName(redisUtils.getDict("zhlx_", productPriceItemPageListVOS.get(i).getPayWay()));
            productPriceItemPageListVOS.get(i).setCurrencyName(redisUtils.getDict("currency_", productPriceItemPageListVOS.get(i).getCurrency()));
            productPriceItemPageListVOS.get(i).setCurrencyUnitName(redisUtils.getDict("priceUnit_", productPriceItemPageListVOS.get(i).getCurrencyUnit()));
            hashMap.put("区域名称:", productPriceItemPageListVOS.get(i).getAreaName());
            hashMap.put("提货地名称:", productPriceItemPageListVOS.get(i).getDeliveryPlaceName());
            hashMap.put("提货地分类名称:", productPriceItemPageListVOS.get(i).getDeliveryPlaceClassName());
            hashMap.put("配送方式:", productPriceItemPageListVOS.get(i).getDistributionWayName());
            hashMap.put("运输方式:", productPriceItemPageListVOS.get(i).getTransportWayName());
            hashMap.put("支付方式:", productPriceItemPageListVOS.get(i).getPayWayName());
            hashMap.put("含税单价:", productPriceItemPageListVOS.get(i).getTaxPrice().toString());
            hashMap.put("单位单位:", productPriceItemPageListVOS.get(i).getCurrencyUnitName());
            hashMap.put("仓库名称:", productPriceItemPageListVOS.get(i).getDepotName());
            String string = hashMap.toString();
            String s = string.replaceAll("=", "");
            list.add(s);
        }
        result.setItem(list.toString());
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月16日 下午2:32:41
     * 描述 : 保存产品定价明细（私有方法）
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : saveItem
     * void
     *
     * @throws
     */
    private void saveItem(String productPriceNo, List<ProductPriceItemAddDTO> itemAddList) {
        if (CollectionUtils.isNotEmpty(itemAddList)) {
            List<ProductPriceItemPO> entityList = new ArrayList<>();
            for (ProductPriceItemAddDTO entity : itemAddList) {
                ProductPriceItemPO productPriceItem = new ProductPriceItemPO();
                BeanUtils.copyProperties(entity, productPriceItem);
                productPriceItem.setId(null);
                productPriceItem.setProductPriceNo(productPriceNo);
                entityList.add(productPriceItem);
            }
            productPriceItemService.saveBatch(entityList);
        }
    }

}
