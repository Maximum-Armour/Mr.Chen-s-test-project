package com.ccit.area.sales.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.marketing.ApplyBiddingPO;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingAddDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;
import com.ccit.area.sales.dao.mapper.marketing.ApplyBiddingMapper;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingEndorseVO;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingPageListVO;
import com.ccit.area.sales.service.marketing.IApplyBiddingService;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import com.ccit.common.utils.AESUtil;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.ip.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  * 描述 : “竞价审核”服务实现类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月12日 下午3:00:24
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding.impl
 *  * 类名 : ApplyBiddingServiceImpl
 */
@Service
@Slf4j
public class ApplyBiddingServiceImpl extends ServiceImpl<ApplyBiddingMapper, ApplyBiddingPO> implements IApplyBiddingService {
    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private ISalesBiddingService iSalesBiddingService;

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午3:55:23
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : selectPageList
     * Result<Page<ApplyBiddingPageListVO>>
     *
     * @throws
     */
    @Override
    public Page<ApplyBiddingPageListVO> selectPageList(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<ApplyBiddingPageListVO> pages = null;
        if (page == null && size == null || page <= 0 && size <= 0) {
            pages = new Page<>(0, Integer.MAX_VALUE);
        } else {
            pages = new Page<>(page, size);
        }
        param.put("deleted", GlobalConstants.DELETE_NO);
        Page<ApplyBiddingPageListVO> applyBiddingPageListVOPage = pages.setRecords(this.baseMapper.selectPageList(pages, param));
        List<ApplyBiddingPageListVO> records = applyBiddingPageListVOPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setBiddingModeName(redisUtils.getDict("biddingMode_", records.get(i).getBiddingMode()));
            records.get(i).setShippingTypeName(redisUtils.getDict("ysfs_", records.get(i).getShippingType()));
            records.get(i).setDeliveryMethodName(redisUtils.getDict("psfs_", records.get(i).getDeliveryMethod()));
        }
        return pages;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午4:43:14
     * 描述 : 审核数据
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : submit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String submit(ApplyBiddingSubmitDTO entity) {

        // 根据审核状态处理数据
        if ("审核通过".equals(entity.getStatus())) {
            //为0则为报名审核
            // 更新数据库
            LambdaUpdateWrapper<ApplyBiddingPO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ApplyBiddingPO::getId, entity.getId());
            wrapper.set(ApplyBiddingPO::getStatus, entity.getStatus());
            int updateByIdFlag = this.baseMapper.update(null, wrapper);
            // 更新成功后处理Redis数据
            if (updateByIdFlag > 0) {
                ApplyBiddingEndorseVO applyBiddingEndorseVO = this.baseMapper.selectStatus(entity.getId());
                String tenderNumber = applyBiddingEndorseVO.getTenderNumber();
                Object redisValue = redisUtils.get(tenderNumber);
                if (redisValue != null) {
                    // 用户存在就追加
                    List<String> stringList = JSON.parseArray(AESUtil.decrypt(redisValue.toString(), GlobalConstants.AES_PARAM_KEY), String.class);
                    stringList.add(applyBiddingEndorseVO.getCustomerId());
                    redisUtils.set(tenderNumber, AESUtil.encrypt(JSON.toJSONString(stringList), GlobalConstants.AES_PARAM_KEY));
                } else {
                    // 用户不存在就新增
                    List<String> list = new ArrayList<>();
                    list.add(applyBiddingEndorseVO.getCustomerId());
                    redisUtils.set(tenderNumber, AESUtil.encrypt(JSON.toJSONString(list), GlobalConstants.AES_PARAM_KEY));
                }
                return "审核通过";
            } else {
                throw new BusinessException("审核失败!");
            }
        } else if ("审核拒绝".equals(entity.getStatus())) {
            // 更新数据库
            LambdaUpdateWrapper<ApplyBiddingPO> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ApplyBiddingPO::getId, entity.getId());
            wrapper.set(ApplyBiddingPO::getStatus, entity.getStatus());
            int updateByIdFlag = this.baseMapper.update(null, wrapper);
            if (updateByIdFlag > 0) {
                return "审核成功";
            } else {
                throw new BusinessException("审核失败!");
            }
        }
        throw new BusinessException("审核失败!");
    }


    /**
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 上午09:11:23
     * 描述 : 根据主键ID结果编辑
     * 包名 : com.ccit.area.sales.service.bidding.impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(ApplyBiddingEditDTO entity) {
        ApplyBiddingPO applyBiddingPO = new ApplyBiddingPO();
        BeanUtils.copyProperties(entity, applyBiddingPO);
        int updateByIdFlag = this.baseMapper.updateById(applyBiddingPO);
        if (updateByIdFlag > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月19日 上午09:10:05
     * 描述 : 报名新增
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : add
     * ApplyBiddingAddDTO
     *
     * @throws
     */
    @Override
    public String add(ApplyBiddingAddDTO entity) {
        // 获取当前用户账号
        String userName = entity.getUserName();
        // 获取当前用户名
        String realName = entity.getRealName();
        // 获取当前请求对象
        HttpServletRequest request = ServletUtil.getRequest();
        // 获取客户端IP地址
        String customerIp = IpUtil.getIpAddr(request);
        // 获取招标编号
        String tenderNumber = entity.getTenderNumber();
        // 获取客户ID
        String customerId = entity.getCustomerId();
        // 获取客户名称
        String customer = entity.getCustomer();
        // 查询招标信息
        LambdaUpdateWrapper<SalesBiddingPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SalesBiddingPO::getTenderNumber, tenderNumber);
        SalesBiddingPO salesBiddingPO = iSalesBiddingService.getOne(wrapper);
        // 获取物料编号
        String materielNo = salesBiddingPO.getMaterielNo();
        // 获取物料名称
        String materielName = salesBiddingPO.getMaterielName();
        // 获取招标模式
        String biddingMode = salesBiddingPO.getBiddingMode();
        // 获取公司编码
        String companyNo = entity.getCompanyNo();
        // 获取公司名称
        String companyName = entity.getCompanyName();
        // 获取报名截止时间
        Date signUpEndTime = salesBiddingPO.getSignUpEndTime();
        //获取报名时间
        Date signUpDate = parseSignTime(entity.getSignTime());
        // 判断当前时间是否在报名截止时间之前
        if (!signUpDate.after(signUpEndTime)) {
            // 查询当前招标编号和客户IP是否已经报名
            Long l = this.baseMapper.selectCustomerIp(tenderNumber, customerIp);
            // 查询当前招标编号和客户ID是否已经报名
            Long l1 = this.baseMapper.selectCustomerId(tenderNumber, customerId);
            // 如果该IP已经报名，则抛出异常
            if (l != 0) {
                throw new BusinessException("该电脑已经报名过客户，请换电脑重新报名！");
            } else if (l1 != 0) {
                // 如果该客户已经报名，则抛出异常
                throw new BusinessException("该客户已经报名，请换客户重新报名！");
            } else {
                // 否则，插入报名信息到数据库
                this.baseMapper.addApplyBidding(tenderNumber, customerId, customer, customerIp, materielNo, materielName, biddingMode, "已报名", companyNo, companyName, userName, realName);
            }
        } else {
            // 如果当前时间不在报名截止时间之前，则抛出异常
            throw new BusinessException("报名截止时间已结束，请刷新页面重新报名！");
        }
        // 返回报名成功消息
        return "报名成功";
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateApprovalStatus(Long id, String tenderNumber, String status) {
        LambdaUpdateWrapper<ApplyBiddingPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ApplyBiddingPO::getId, id);
        wrapper.eq(ApplyBiddingPO::getTenderNumber, tenderNumber);
        wrapper.set(ApplyBiddingPO::getStatus, status);
        int update = this.baseMapper.update(null, wrapper);
        if (update > 0) {
            if ("审核通过".equals(status)) {
                //审核通过后将用户加入到缓存中
                LambdaQueryWrapper<ApplyBiddingPO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ApplyBiddingPO::getTenderNumber, tenderNumber);
                queryWrapper.eq(ApplyBiddingPO::getStatus, "审核通过");
                List<ApplyBiddingPO> applyBiddingPOList = this.baseMapper.selectList(queryWrapper);
                List<String> strings = applyBiddingPOList.stream().map(
                        ApplyBiddingPO::getCustomerId
                ).collect(Collectors.toList());
                String encrypt = AESUtil.encrypt(strings.toString(), GlobalConstants.AES_PARAM_KEY);
                redisUtils.set(tenderNumber, encrypt);
            }
            return "修改成功";
        } else {
            return null;
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String batchUpdateApprovalStatus(String tenderNumber) {
        int update = this.baseMapper.batchUpdateApprovalStatus(tenderNumber);
        if (update > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }


    private Date parseSignTime(String signTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return inputFormat.parse(signTime);
        } catch (ParseException e) {
            log.error("转换时间字段失败: {}", signTime, e);
            throw new BusinessException("转换时间字段失败");
        }
    }


}
