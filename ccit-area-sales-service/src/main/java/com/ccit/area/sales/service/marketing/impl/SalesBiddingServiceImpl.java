package com.ccit.area.sales.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.domain.marketing.TargetInfoPO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingAddDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingEditDTO;
import com.ccit.area.sales.dao.mapper.marketing.SalesBiddingMapper;
import com.ccit.area.sales.dao.mapper.marketing.TargetInfoMapper;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “产品竞价”服务实现类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月06日 上午11:00:24
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding.impl
 *  * 类名 : SalesBiddingServiceImpl
 */
@Service
@Slf4j
public class SalesBiddingServiceImpl extends ServiceImpl<SalesBiddingMapper, SalesBiddingPO> implements ISalesBiddingService {

    /**
     * “序列号”服务类
     */
    @Autowired
    private ISystemSequenceService systemSequenceService;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ISystemOrgService isSystemOrgService;

    @Autowired
    private TargetInfoMapper targetInfoMapper;

    public SalesBiddingServiceImpl() {
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月06日 上午11:01:23
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : add
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String add(SalesBiddingAddDTO entity) {
        // 检查招标编号是否已存在
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.eq(SalesBiddingPO::getTenderNumber, entity.getTenderNumber());
        Integer i1 = this.baseMapper.selectCount(wrapper);
        if (i1 > 0) {
            throw new BusinessException("招标编号已存在，请进行检查！");
        }

        // 初始化销售招标实体并复制属性
        SalesBiddingPO salesBidding = new SalesBiddingPO();
        BeanUtils.copyProperties(entity, salesBidding);

        // 解析日期
        Date StartingTime = null;
        Date signEndTime = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StartingTime = simpleDateFormat.parse(entity.getStartingTime());
            signEndTime = simpleDateFormat.parse(entity.getSignUpEndTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        salesBidding.setStartingTime(StartingTime);
        salesBidding.setSignUpEndTime(signEndTime);

        // 设置初始状态
        salesBidding.setStatus("未提交");
        salesBidding.setEnableStatus("启用");

        // 生成招标编号
        String orgNo = entity.getOrgNo();
        String s = this.baseMapper.orgNoAbbreviation(orgNo);
        String[] split = s.split("_");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        String code = split[0] + "JJ" + formattedDate;
        String value = systemSequenceService.get(code, 4);
        salesBidding.setBiddingNo(value);

        // 设置公司编号和名称
        SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(orgNo);
        salesBidding.setCompanyNo(fatherOrgNo.getOrgNo());
        salesBidding.setCompanyName(fatherOrgNo.getOrgName());

        // 插入数据库
        int insertFlag = this.baseMapper.insert(salesBidding);
        if (insertFlag > 0) {
            String status = biddingInfo(salesBidding);
            if ("同步成功".equals(status)){
                return "新增成功";
            }
        }
        throw new BusinessException("新增失败");
    }

    /**
     * 同步竞价信息
     *
     * @param salesBidding
     */
    private String biddingInfo(SalesBiddingPO salesBidding) {

        LambdaQueryWrapper<SalesBiddingPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SalesBiddingPO::getTenderNumber, salesBidding.getTenderNumber());
        SalesBiddingPO biddingPO = this.baseMapper.selectOne(wrapper);

        TargetInfoPO targetInfoPO = new TargetInfoPO();
        targetInfoPO.setId(biddingPO.getId().toString());
        //TODO 产品ID待确认
        targetInfoPO.setProductid(salesBidding.getMaterielNo());
        targetInfoPO.setHtype(1);
        targetInfoPO.setQuantity(salesBidding.getQuantity());
        targetInfoPO.setPrice(salesBidding.getLowPrice());
        targetInfoPO.setMinmarkup(salesBidding.getMinMarkup().toString());
        String payment = redisUtils.getDict("zhlx_", salesBidding.getPaymentMethod());
        targetInfoPO.setPayment(payment);
        targetInfoPO.setDeposit(salesBidding.getBond().toString());
        targetInfoPO.setDepositendtime(salesBidding.getBondEndTime());
        Date startingTime = salesBidding.getStartingTime();
        BigDecimal biddingDuration = salesBidding.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        targetInfoPO.setBegintime(startingTime);
        targetInfoPO.setEndtime(endTime);
//        //TODO 交割地待确认
        targetInfoPO.setDelivery(salesBidding.getDeliveryPlaceName());
        String deliveryDetail = redisUtils.getDict("ysfs_", salesBidding.getDeliveryMethod());
        targetInfoPO.setDeliverydetail(deliveryDetail);
        targetInfoPO.setMinquantity(salesBidding.getIncreaseAmplitude().toString());
        targetInfoPO.setMinbquantity(salesBidding.getStartingQuantity().toString());
        targetInfoPO.setRule(salesBidding.getBiddingMode());
        targetInfoPO.setBiddingmode("0");
        targetInfoPO.setLapsetime(salesBidding.getDelayDuration().toString());
        targetInfoPO.setTSalesBiddingId(biddingPO.getId().toString());

        //启动最低人数限制
        targetInfoPO.setMinpeopleflg("1");
        //竞拍最低人数
        targetInfoPO.setMinpeoplecount(salesBidding.getMinBidders());
        //允许延拍次数
        targetInfoPO.setLapsecount("100");
        //竞拍次数
        targetInfoPO.setOutbidstate("4");

        int insert = targetInfoMapper.insert(targetInfoPO);
        if (insert < 0) {
            throw new BusinessException("同步竞价标的信息失败!");
        }else {
            return "同步成功";
        }
    }


    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午08:55:23
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : selectPageList
     * Result<Page<SalesBiddingPageListVO>>
     *
     * @throws
     */
    @Override
    public Page<SalesBiddingPageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<SalesBiddingPageListVO> pages = null;
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
            if ("sysadmin".equals(userName)) {
                param.put("orgNoList", "");
            } else {
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
                List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
                param.put("orgNoList", existOrgNo);
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }
        Page<SalesBiddingPageListVO> salesBiddingPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
        List<SalesBiddingPageListVO> records = salesBiddingPageListVOPage.getRecords();
        for (SalesBiddingPageListVO record : records) {
            record.setBiddingModeName(redisUtils.getDict("biddingMode_", record.getBiddingMode()));
            record.setShippingTypeName(redisUtils.getDict("ysfs_", record.getShippingType()));
            record.setDeliveryMethodName(redisUtils.getDict("psfs_", record.getDeliveryMethod()));
            if (record.getStartingTime() != null) {
                Date startingTime = record.getStartingTime();
                BigDecimal biddingDuration = record.getBiddingDuration();
                // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
                BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
                // 计算竞价结束时间
                Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
                record.setEndTime(endTime);
            }
        }
        return pages;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午09:11:23
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : get
     * SalesBiddingDetailsVO
     *
     * @throws
     */
    @Override
    public SalesBiddingDetailsVO get(Long id) {
        if (null == id) {
            throw new BusinessException("产品竞价ID不能为空");
        }
        SalesBiddingPO salesBiddingPO = this.baseMapper.selectById(id);
        if (null == salesBiddingPO) {
            throw new BusinessException("获取产品竞价失败");
        }
        SalesBiddingDetailsVO result = new SalesBiddingDetailsVO();
        BeanUtils.copyProperties(salesBiddingPO, result);
        Date startingTime = result.getStartingTime();
        BigDecimal biddingDuration = result.getBiddingDuration();
        // 将竞价时长转换为毫秒 (1 分钟 = 60 * 1000 毫秒)
        BigDecimal biddingDurationMillis = biddingDuration.multiply(BigDecimal.valueOf(60 * 1000));
        // 计算竞价结束时间
        Date endTime = new Date(startingTime.getTime() + biddingDurationMillis.longValue());
        result.setBiddingEndTime(endTime);
        return result;
    }


    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:23:23
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(SalesBiddingEditDTO entity) {
        SalesBiddingPO salesBiddingPO = new SalesBiddingPO();
        BeanUtils.copyProperties(entity, salesBiddingPO);
        int updateByIdFlag = this.baseMapper.updateById(salesBiddingPO);
        if (updateByIdFlag > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:55:22
     * 描述 : 删除数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : delete
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String delete(SalesBiddingDeleteDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("产品竞价ID不能为空");
        }
        LambdaQueryWrapper<SalesBiddingPO> wrapper = SalesBiddingPO.wrapper();
        wrapper.in(SalesBiddingPO::getId, entity.getIds());
        int updateFlag = this.baseMapper.update(new SalesBiddingPO(true, false), wrapper);
        if (updateFlag > 0) {
            return "删除成功";
        }
        throw new BusinessException("删除失败");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateStatus(String tenderNumber, String status) {
        LambdaUpdateWrapper<SalesBiddingPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        wrapper.set(SalesBiddingPO::getStatus, status);
        int update = this.baseMapper.update(null, wrapper);
        if (update > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }
}
