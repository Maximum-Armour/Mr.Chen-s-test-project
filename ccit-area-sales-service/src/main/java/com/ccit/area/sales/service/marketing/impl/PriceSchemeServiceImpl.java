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
import com.ccit.area.sales.dao.domain.marketing.PriceSchemePO;
import com.ccit.area.sales.dao.dto.marketing.*;
import com.ccit.area.sales.dao.mapper.marketing.PriceSchemeMapper;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeItemPageListVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemePageListVO;
import com.ccit.area.sales.dao.vo.marketing.PriceSchemeSelectVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.marketing.IPriceSchemeItemService;
import com.ccit.area.sales.service.marketing.IPriceSchemeService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
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
 *  * 描述 : “定价方案”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年8月8日 下午4:13:37
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.marketing.impl
 *  * 类名 : PriceSchemeServiceImpl
 */
@Service
@Slf4j
public class PriceSchemeServiceImpl extends ServiceImpl<PriceSchemeMapper, PriceSchemePO> implements IPriceSchemeService {

    /**
     * “序列号”服务类
     */
    @Autowired
    private ISystemSequenceService systemSequenceService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISystemUserService iSystemUserService;

    /**
     * “定价方案明细”服务类
     */
    @Autowired
    private IPriceSchemeItemService priceSchemeItemService;

    @Autowired
    private ISystemOrgService isSystemOrgService;

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:44:38
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : selectPageList
     * Page<PriceSchemePageListVO>
     *
     * @throws
     */
    @Override
    public Page<PriceSchemePageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<PriceSchemePageListVO> pages = null;
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
        Page<PriceSchemePageListVO> priceSchemePageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
        List<PriceSchemePageListVO> records = priceSchemePageListVOPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setTradeTypeName(redisUtils.getDict("tradeType_", records.get(i).getTradeType()));
        }
        return pages;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:44:41
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
    public String add(PriceSchemeAddDTO entity) {
        LambdaQueryWrapper<PriceSchemePO> wrapper = PriceSchemePO.wrapper();
        wrapper.eq(PriceSchemePO::getSchemeNo, entity.getSchemeNo());
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount > 0) {
            throw new BusinessException("方案编码已存在");
        }
        String orgNo = entity.getOrgNo();
        String s = this.baseMapper.orgNoAbbreviation(orgNo);
        String[] split = s.split("_");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        String code = split[0] + "DJO" + formattedDate;
        String value = systemSequenceService.get(code, 4);
        PriceSchemePO priceScheme = new PriceSchemePO();
        entity.setSchemeNo(value);
        List<PriceSchemeItemAddDTO> itemAddList = entity.getItemAddList();
        String schemeNo1 = entity.getSchemeNo();
        for (int i = 0; i < itemAddList.size(); i++) {
            String value1 = systemSequenceService.get(schemeNo1, 4);
            itemAddList.get(i).setSchemeItemNo(value1);
        }
        SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(orgNo);
        priceScheme.setCompanyNo(fatherOrgNo.getOrgNo());
        priceScheme.setCompanyName(fatherOrgNo.getOrgName());
        BeanUtils.copyProperties(entity, priceScheme);
        int insertFlag = this.baseMapper.insert(priceScheme);
        if (insertFlag > 0) {
            saveItem(entity.getSchemeNo(), entity.getItemAddList());
            return "新增成功";
        }
        throw new BusinessException("新增失败");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:44:44
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : get
     * PriceSchemeDetailsVO
     *
     * @throws
     */
    @Override
    public PriceSchemeDetailsVO get(Long id) {
        if (null == id) {
            throw new BusinessException("方案ID不能为空");
        }
        PriceSchemePO priceScheme = this.baseMapper.selectById(id);
        if (null == priceScheme) {
            throw new BusinessException("获取定价方案失败");
        }
        PriceSchemeDetailsVO result = new PriceSchemeDetailsVO();
        BeanUtils.copyProperties(priceScheme, result);
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:44:47
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
    public String edit(PriceSchemeEditDTO entity) {
//		LambdaQueryWrapper<PriceSchemePO> wrapper = PriceSchemePO.wrapper();
//		wrapper.eq(PriceSchemePO::getSchemeNo, entity.getSchemeNo());
//		wrapper.ne(PriceSchemePO::getId, entity.getId());
//		Integer selectCount = this.baseMapper.selectCount(wrapper);
//		if (selectCount > 0) {
//			throw new BusinessException("方案编码已存在");
//		}
        PriceSchemePO priceScheme = new PriceSchemePO();
        BeanUtils.copyProperties(entity, priceScheme);
        int updateByIdFlag = this.baseMapper.updateById(priceScheme);
        if (updateByIdFlag > 0) {
            List<PriceSchemeItemEditDTO> itemList = entity.getItemList();
            for (int i = 0; i < itemList.size(); i++) {
                Long id = itemList.get(i).getId();
                if (id == null) {
                    String schemeNo1 = entity.getSchemeNo();
                    String value = systemSequenceService.get(schemeNo1, 4);
                    itemList.get(i).setSchemeItemNo(value);
                    itemList.get(i).setGmtCreate(new Date());
                    itemList.get(i).setGmtModified(new Date());
                    PriceSchemeItemEditDTO priceSchemeItemEditDTO = itemList.get(i);
                    PriceSchemeItemPO priceSchemeItem = new PriceSchemeItemPO();
                    BeanUtils.copyProperties(priceSchemeItemEditDTO, priceSchemeItem);
                    this.baseMapper.insertItem(priceSchemeItem);
                } else {
                    PriceSchemeItemEditDTO priceSchemeItemEditDTO = itemList.get(i);
                    PriceSchemeItemPO priceSchemeItem = new PriceSchemeItemPO();
                    BeanUtils.copyProperties(priceSchemeItemEditDTO, priceSchemeItem);
                    this.baseMapper.updateItem(priceSchemeItem);
                }
            }
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月9日 下午2:44:49
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
    public String delete(PriceSchemeDeleteDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("方案ID不能为空");
        }
        LambdaQueryWrapper<PriceSchemePO> wrapper = PriceSchemePO.wrapper();
        wrapper.in(PriceSchemePO::getId, entity.getIds());
        int updateFlag = this.baseMapper.update(new PriceSchemePO(true, false), wrapper);
        if (updateFlag > 0) {
            return "删除成功";
        }
        throw new BusinessException("删除失败");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月7日 下午2:44:47
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
    public String submit(PriceSchemeSubmitDTO entity) {
//		if (CollectionUtils.isEmpty(entity.getIds())) {
//			throw new BusinessException("定价方案ID不能为空");
//		}
//		ExamineApprovalRequests examineApprovalRequests = new ExamineApprovalRequests();
//		RequestsData requestsData = new RequestsData();
//		requestsData.setIds(entity.getIds());
//		examineApprovalRequests.setData(requestsData);
//		examineApprovalRequests.setBusinessName("定价方案管理_提交");
//		examineApprovalRequests.setDatabaseName("t_price_scheme");
//		ResponseEntity<ResultResponse> resultResponseResponseEntity = examineFeign.examineApproval(examineApprovalRequests);
//		if (resultResponseResponseEntity.getBody().getCode().equals("200")){
//			this.baseMapper.workflowid(resultResponseResponseEntity.getBody().getApprovalId());
//			LambdaQueryWrapper<PriceSchemePO> wrapper = PriceSchemePO.wrapper();
//			wrapper.in(PriceSchemePO::getId, entity.getIds());
//			PriceSchemePO priceSchemePO = new PriceSchemePO();
//			priceSchemePO.setShbz("审核中");
//			int updateFlag = this.baseMapper.update(priceSchemePO, wrapper);
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
     * 创建时间 : 2024年11月11日 下午2:44:47
     * 描述 : 审核数据
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : examine
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String examine(PriceSchemeSubmitDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("定价方案ID不能为空");
        }
        String sign = entity.getSign();
        LambdaQueryWrapper<PriceSchemePO> wrapper = PriceSchemePO.wrapper();
        wrapper.in(PriceSchemePO::getId, entity.getIds());
        PriceSchemePO priceSchemePO = new PriceSchemePO();
        if (sign == "1") {
            priceSchemePO.setShbz("审核通过");
        } else {
            priceSchemePO.setShbz("审核拒绝");
        }
        int updateFlag = this.baseMapper.update(priceSchemePO, wrapper);
        if (updateFlag > 0) {
            return "审核成功";
        } else {
            return "审核失败";
        }
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月24日 下午2:42:08
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.marketing
     * 方法名 : select
     * PriceSchemeSelectVO
     *
     * @throws
     */
    @Override
    public PriceSchemeSelectVO select(Long id) {
        if (null == id) {
            throw new BusinessException("方案ID不能为空");
        }
        PriceSchemePO priceScheme = this.baseMapper.selectById(id);
        if (null == priceScheme) {
            throw new BusinessException("获取定价方案失败");
        }
        PriceSchemeSelectVO result = new PriceSchemeSelectVO();
        BeanUtils.copyProperties(priceScheme, result);
        result.setTradeTypeName(redisUtils.getDict("tradeType_", priceScheme.getTradeType()));
        String schemeNo = priceScheme.getSchemeNo();
        List<PriceSchemeItemPageListVO> priceSchemeItemPageListVOS = this.baseMapper.selectItem(schemeNo);
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < priceSchemeItemPageListVOS.size(); i++) {
            priceSchemeItemPageListVOS.get(i).setDistributionWayName(redisUtils.getDict("psfs_", priceSchemeItemPageListVOS.get(i).getDistributionWay()));
            priceSchemeItemPageListVOS.get(i).setTransportWayName(redisUtils.getDict("ysfs_", priceSchemeItemPageListVOS.get(i).getTransportWay()));
            priceSchemeItemPageListVOS.get(i).setPayWayName(redisUtils.getDict("zhlx_", priceSchemeItemPageListVOS.get(i).getPayWay()));
            priceSchemeItemPageListVOS.get(i).setCurrencyName(redisUtils.getDict("currency_", priceSchemeItemPageListVOS.get(i).getCurrency()));
            priceSchemeItemPageListVOS.get(i).setUnitName(redisUtils.getDict("unit_", priceSchemeItemPageListVOS.get(i).getUnit()));
            hashMap.put("区域名称:", priceSchemeItemPageListVOS.get(i).getAreaName());
            hashMap.put("提货地名称:", priceSchemeItemPageListVOS.get(i).getDeliveryPlaceName());
            hashMap.put("提货地分类名称:", priceSchemeItemPageListVOS.get(i).getDeliveryPlaceClassName());
            hashMap.put("配送方式:", priceSchemeItemPageListVOS.get(i).getDistributionWayName());
            hashMap.put("运输方式:", priceSchemeItemPageListVOS.get(i).getTransportWayName());
            hashMap.put("支付方式:", priceSchemeItemPageListVOS.get(i).getPayWayName());
            hashMap.put("币种:", priceSchemeItemPageListVOS.get(i).getCurrencyName());
            hashMap.put("单位:", priceSchemeItemPageListVOS.get(i).getUnitName());
            String string = hashMap.toString();
            String s = string.replaceAll("=", "");
            list.add(s);
        }
        result.setItem(list.toString());
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年8月12日 上午11:10:01
     * 描述 : 保存定价方案明细（私有方法）
     * 包名 : com.ccit.area.sales.service.marketing.impl
     * 方法名 : saveItem
     * void
     *
     * @throws
     */
    private void saveItem(String schemeNo, List<PriceSchemeItemAddDTO> itemAddList) {
        if (CollectionUtils.isNotEmpty(itemAddList)) {
            List<PriceSchemeItemPO> entityList = new ArrayList<>();
            for (PriceSchemeItemAddDTO entity : itemAddList) {
                PriceSchemeItemPO priceSchemeItem = new PriceSchemeItemPO();
                BeanUtils.copyProperties(entity, priceSchemeItem);
                priceSchemeItem.setId(null);
                priceSchemeItem.setSchemeNo(schemeNo);
                entityList.add(priceSchemeItem);
            }
            priceSchemeItemService.saveBatch(entityList);
        }
    }

}
