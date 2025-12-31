package com.ccit.area.sales.service.sales.Impl;

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
import com.ccit.area.sales.dao.domain.sales.OrderLinePO;
import com.ccit.area.sales.dao.dto.sales.OrderLineDTO;
import com.ccit.area.sales.dao.dto.sales.OrderLinesDTO;
import com.ccit.area.sales.dao.dto.sales.OrderSummaryDTO;
import com.ccit.area.sales.dao.dto.sales.OrderUpDTO;
import com.ccit.area.sales.dao.mapper.sales.OrderLineMapper;
import com.ccit.area.sales.dao.vo.sales.OrderLineListVO;
import com.ccit.area.sales.dao.vo.sales.OrderLineVO;
import com.ccit.area.sales.dao.vo.sales.OrderMouldnoVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.dingding.ProcessOperationService;
import com.ccit.area.sales.service.marketing.ISalesBiddingService;
import com.ccit.area.sales.service.sales.OrderService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.STLineSpacingRule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderLineServiceImpl extends ServiceImpl<OrderLineMapper, OrderLinePO> implements OrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISystemUserService iSystemUserService;

    @Autowired
    private ISystemOrgService isSystemOrgService;

    @Autowired
    private ProcessOperationService processOperationService;

    @Autowired
    private ISalesBiddingService salesBiddingService;

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 上午9:12:12
     * 描述 : 根据listingcode查询相应数据
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : selectOrder
     * OrderLineVO
     *
     * @throws
     */
    @Override
    public List<OrderLineVO> selectOrder(OrderLineListVO entity) {

        SystemCurrentUserVO systemUser = null;
        try {
            JSONObject userJson = CurrentUserUtil.getCurrentUser();
            systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
            String userName = systemUser.getUserName();
            if (userName.equals("sysadmin")) {
                entity.setCompanyName("");
            } else {
                String companyName = entity.getCompanyName();
                if (companyName != null && companyName.contains("—")) {
                    // 使用 substring 和 indexOf 截取 - 前面的部分
                    String companyPrefix = companyName.substring(0, companyName.indexOf('—'));
                    // 将截取后的结果放入 param 中
                    entity.setCompanyName(companyPrefix);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }
        List<OrderLineVO> orderLineVOS = baseMapper.selectOrder(entity);

        if (orderLineVOS != null) {
            for (OrderLineVO orderLineVO : orderLineVOS) {
                if ("JJ".equals(orderLineVO.getOrderType())) {
                    LambdaQueryWrapper<SalesBiddingPO> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(SalesBiddingPO::getTenderNumber, orderLineVO.getListedNo());
                    SalesBiddingPO salesBiddingPO = salesBiddingService.getOne(wrapper);
                    orderLineVO.setMaterielNo(salesBiddingPO.getMaterielNo());
                    orderLineVO.setMaterielName(salesBiddingPO.getMaterielName());
                }
            }
            // 使用 stream API 处理每个 OrderLineVO 对象
            orderLineVOS = orderLineVOS.stream().peek(t -> {
                t.setOrderType(redisUtils.getDict("orderType_", t.getOrderType()));
                // 根据 productClassName 修改 productStatus
                if ("聚烯烃".equals(t.getProductCategoryName())) {
                    t.setProductStatus("Y");
                } else {
                    t.setProductStatus("N");
                }
            }).collect(Collectors.toList());
        }

        return orderLineVOS;
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:53:15
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(OrderUpDTO entity) {

//        OrderLinePO orderLinePO = new OrderLinePO();
//        BeanUtils.copyProperties(entity, orderLinePO);

        LambdaUpdateWrapper<OrderLinePO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrderLinePO::getId, entity.getId());
        wrapper.set(OrderLinePO::getTransactionPrice, entity.getTransactionPrice());
        wrapper.set(OrderLinePO::getTurnoverQuantity, entity.getTurnoverQuantity());
        int update = baseMapper.update(null, wrapper);
//        int updateByIdFlag = this.baseMapper.updateById(orderLinePO);
        if (update > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月25日 下午14:36:15
     * 描述 : 订单汇总
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : summary
     * BigDecimal
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public BigDecimal[] summary(OrderSummaryDTO entity) {
        if (entity.getIds() == null || entity.getIds().isEmpty()) {
            throw new BusinessException("订单 ID 不能为空");
        }

        BigDecimal totalTransactionQuantity = BigDecimal.ZERO;
        BigDecimal totalOrderSubmissionQuantity = BigDecimal.ZERO;

        List<OrderLineVO> summary = baseMapper.summary(entity);
        // 遍历汇总数据并累加数量
        for (OrderLineVO summ : summary) {
            if (summ.getOrderSubmissionQuantity() != null) {
                totalTransactionQuantity = totalTransactionQuantity.add(
                        new BigDecimal(summ.getOrderSubmissionQuantity().toString()));
            }
            if (summ.getTurnoverQuantity() != null) {
                totalOrderSubmissionQuantity = totalOrderSubmissionQuantity.add(
                        new BigDecimal(summ.getTurnoverQuantity().toString()));
            }
        }

        // 返回结果，不进行任何舍入处理
        return new BigDecimal[]{totalTransactionQuantity, totalOrderSubmissionQuantity};
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月18日 下午15:53:15
     * 描述 : 拒接订单
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : refuse
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String refuse(OrderSummaryDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("订单ID不能为空");
        }
        List<OrderLineVO> selectlist = this.baseMapper.selectBystatus(entity);
        for (OrderLineVO sel : selectlist) {
            if (sel.getStatus() == null || "".equals(sel.getStatus().trim())) {
                throw new BusinessException("数据状态有为空的");
            }
            if (!"待审核".equals(sel.getStatus()))
                throw new BusinessException("只有待审核的状态才能拒接");
        }
        int uprefuse = this.baseMapper.uprefuse(entity);
        if (uprefuse > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败");
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月27日 上午10:19:15
     * 描述 : 提交订单
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : submit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String submit(List<OrderLinesDTO> entity) {

        if (entity == null || entity.isEmpty()) {
            throw new BusinessException("订单详情不能为空");
        }
        List<Long> orderIds = entity.stream()
                .map(OrderLinesDTO::getId)
                .collect(Collectors.toList());
        // 检查产品类别是否为“聚烯烃”
        List<String> orderNo = entity.stream()
                .map(OrderLinesDTO::getOrderNo)
                .collect(Collectors.toList());
        List<OrderMouldnoVO> orderMouldnoVOList = this.baseMapper.selectMaterielno(orderNo);
        for (OrderMouldnoVO orderMouldnoVO : orderMouldnoVOList) {
            if ("聚烯烃".equals(orderMouldnoVO.getProductCategoryName())) {
                throw new BusinessException("危化品无法提交");
            }
        }

        List<OrderLineVO> selectlist = this.baseMapper.selectlist(orderIds);
        //从竞价获取产品信息
        List<Long> unsubmittedIds = new ArrayList<>();
        for (OrderLineVO sel : selectlist) {
            if (sel.getStatus() == null || "".equals(sel.getStatus().trim())) {
                throw new BusinessException("您提交数据状态有为空的");
            }
            if (sel.getStatus().equals("待审核") || sel.getStatus().equals("审核拒绝")) {
                unsubmittedIds.add(sel.getId());
            } else {
                throw new BusinessException("只有待审核或审核拒绝的状态才能提交");
            }
        }
        List<String> orderLineNos = entity.stream()
                .map(OrderLinesDTO::getOrderLineNo)
                .collect(Collectors.toList());
        List<OrderLineVO> orderLineVOS = this.baseMapper.selectOrderLine(orderLineNos);
        if (orderLineVOS == null || orderLineVOS.isEmpty()) {
            throw new BusinessException("未录入过订单信息，请先录入");
        }
        // 将查询到的订单行号放入集合
        Set<String> foundOrderLineNos = orderLineVOS.stream()
                .map(OrderLineVO::getOrderLineNo) // 假设OrderLineVO有一个getOrderLineNo方法
                .collect(Collectors.toSet());

        // 检查是否有未录入的订单行
        Set<String> missingOrderLineNos = new HashSet<>(orderLineNos);
        missingOrderLineNos.removeAll(foundOrderLineNos);

        if (!missingOrderLineNos.isEmpty()) {
            throw new BusinessException("部分订单行未录入： " + String.join(", ", missingOrderLineNos));
        }

        if (!CollectionUtils.isEmpty(unsubmittedIds)) {
            // int upsubmit = this.baseMapper.upsubmit(unsubmittedIds);
            //   if (upsubmit > 0) {
            return "200";
            //   }
        }
        throw new BusinessException("提交失败");

    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月29日 下午14:41:12
     * 描述 : 根据map查询相应数据
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : queryReview
     * OrderLineVO
     *
     * @throws
     */
    @Override
    public Page<OrderLineVO> queryReview(Map<String, Object> param) {

        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<OrderLineVO> pages = null;
        if (page == null && size == null || page <= 0 && size <= 0) {
            pages = new Page<>(0, Integer.MAX_VALUE);
        } else {
            pages = new Page<>(page, size);
        }
        param.put("deleted", GlobalConstants.DELETE_NO);
        List<OrderLineVO> orderLineVO = baseMapper.queryReview(pages, param);
        // 执行数据库查询
        pages.setRecords(orderLineVO);
        return pages;


    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月29日 下午14:41:12
     * 描述 : 审核成功或者拒绝
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : approvedorreject
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String approvedorreject(OrderLineDTO entity) {
        OrderLinePO orderLine = new OrderLinePO();
        BeanUtils.copyProperties(entity, orderLine);
        int i = this.baseMapper.updateById(orderLine);
        if (i > 0) {

            return "修改成功";
        }
        throw new BusinessException("修改失败");

    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月30日 下午14:56:12
     * 描述 : 审核成功
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : reviewAll
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String reviewAll(OrderSummaryDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("订单ID不能为空");
        }

        String status = entity.getStatus();
        if ("1".equals(status)) {
            status = "审核通过";
        } else if ("0".equals(status)) {
            status = "审核拒绝";
        }
        entity.setStatus(status);

        // 调用 Mapper 方法批量修改订单状态
        int result = this.baseMapper.reviewAll(entity);

        if (result > 0) {
            return "修改成功";
        } else {
            throw new BusinessException("修改失败");
        }
    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年10月11日 上午午8:56:12
     * 描述 :查询订单明细
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : orderDetails
     * OrderLineVO
     *
     * @throws
     */
    @Override
    public Page<OrderLineVO> orderDetails(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<OrderLineVO> pages = null;
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
            if (!userName.equals("sysadmin")) {
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(userName);
                List<String> existOrgNo = isSystemOrgService.isExistOrgNo(selectUserDetails.getOrgNo());
                param.put("orgNoList", existOrgNo);
            }
        } catch (Exception e) {
            throw new BusinessException("用户不存在!");
        }

        List<OrderLineVO> orderLine = baseMapper.orderDetails(pages, param);

        if (orderLine != null) {
            orderLine.stream().forEach(t -> {
                if ("JJ".equals(t.getOrderType())) {
                    LambdaQueryWrapper<SalesBiddingPO> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(SalesBiddingPO::getTenderNumber, t.getListedNo());
                    SalesBiddingPO salesBiddingPO = salesBiddingService.getOne(wrapper);
                    t.setMaterielNo(salesBiddingPO.getMaterielNo());
                    t.setMaterielName(salesBiddingPO.getMaterielName());
                }
                t.setOrderType(redisUtils.getDict("orderType_", t.getOrderType()));
                t.setShbz(redisUtils.getDict("approval_", t.getShbz()));
            });
        }
        // 执行数据库查询
        pages.setRecords(orderLine);
        return pages;

    }

    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月25日 下午14:36:15
     * 描述 : 订单行信息汇总
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : detailedSummary
     * BigDecimal
     *
     * @throws
     */
    @Override
    public BigDecimal detailedSummary(OrderSummaryDTO entity) {

        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("订单行 ID 不能为空");
        }
        try {
            List<OrderLineVO> orderLine = this.baseMapper.detailedSummary(entity);
            BigDecimal totalturnoverQuantity = BigDecimal.ZERO;

            for (OrderLineVO orderLineItem : orderLine) {
                // 检查对象是否为 null，并确保 orderSubmissionQuantity 不为 null
                if (orderLineItem != null && orderLineItem.getTurnoverQuantity() != null) {
                    totalturnoverQuantity = totalturnoverQuantity.add(orderLineItem.getTurnoverQuantity());
                }
            }
            return totalturnoverQuantity.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new BusinessException("处理订单汇总时出现错误");
        }
    }

    @Override
    public OrderLineVO get(Long id) {
        if (null == id) {
            throw new BusinessException("挂牌ID不能为空");
        }
        OrderLinePO orderLine = this.baseMapper.selectById(id);
        if (null == orderLine) {
            throw new BusinessException("获取挂牌失败");
        }
        OrderLineVO orderLineVO = null;
        if ("JJ".equals(orderLine.getOrderType())) {
            orderLineVO = this.baseMapper.getlineVo(id);
        } else {
            orderLineVO = this.baseMapper.getDing(id);
        }
        orderLineVO.setOrderType(redisUtils.getDict("orderType_", orderLine.getOrderType()));
        return orderLineVO;
    }


    /**
     * 创建人 : cf
     * 创建时间 : 2024年9月25日 下午14:36:15
     * 描述 : 订单详情
     * 包名 : com.ccit.area.sales.service.sales.Impl
     * 方法名 : orderDetail
     * OrderLineVO
     *
     * @throws
     */
    @Override
    public OrderLineVO orderDetail(OrderLineDTO entity) {
        if (null == entity.getId()) {
            throw new BusinessException("订单ID不能为空");
        }
        OrderLineVO orderLinevo = baseMapper.orderID(entity);

        OrderLineVO orderLine = this.baseMapper.orderDetail(orderLinevo.getOrderLineNo());
        orderLine.setOrderType(redisUtils.getDict("orderType_", orderLine.getOrderType()));
        return orderLine;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateStatus(List<Long> ids, String status) {
        LambdaUpdateWrapper<OrderLinePO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(OrderLinePO::getId, ids);
        wrapper.set(OrderLinePO::getStatus, status);
        int update = this.baseMapper.update(null, wrapper);
        if (update >= 1) {
            return "修改成功";
        }
        throw new BusinessException("修改状态失败!");
    }

    @Override
    public Page<OrderLineVO> querySHWCReview(Map<String, Object> param) {
        Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
        Page<OrderLineVO> pages = null;
        if (page == null && size == null || page <= 0 && size <= 0) {
            pages = new Page<>(0, Integer.MAX_VALUE);
        } else {
            pages = new Page<>(page, size);
        }
        param.put("deleted", GlobalConstants.DELETE_NO);
        List<OrderLineVO> orderLineVO = baseMapper.querySHWCReview(pages, param);
        // 执行数据库查询
        pages.setRecords(orderLineVO);
        return pages;
    }

    @Override
    public byte[] downloadSignature(String orderLineNo) {
        //查询订单详情
        OrderLineVO orderLineEntity = baseMapper.getPdf(orderLineNo);
        log.info("查询订单详情：{}", orderLineEntity);
        if (orderLineEntity == null) {
            throw new BusinessException("未找到指定的订单信息");
        }
        if (!"审核完成".equals(orderLineEntity.getStatus())) {
            throw new BusinessException("只有审核完成的才能下载合同");
        }

        try {
            Map<String, String> data = null;
            ByteArrayInputStream byteArrayInputStream = null;
            if ("000002".equals(orderLineEntity.getCompanyNo())) {
                data = getDataMap(orderLineEntity);
                byteArrayInputStream = loadWordTemplate(data);
            } else if ("000009".equals(orderLineEntity.getCompanyNo())) {
                data = tyGetDataMap(orderLineEntity);
                byteArrayInputStream = tyLoadWordTemplate(data);
            } else {
                throw new BusinessException("暂不支持该公司下载合同!");
            }
            // 构建数据映射
            byte[] bytes = convertDocxToPdf(byteArrayInputStream);
//            savePdfToFile(bytes, "D://test.pdf");
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("导出合同模板失败", e);
        }
    }

    private byte[] convertDocxToPdf(ByteArrayInputStream byteArrayInputStream) throws Exception {
        // 加载模板
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(byteArrayInputStream);
        // 设置字体映射（如果需要）
        Mapper fontMapper = new IdentityPlusMapper();
        wordMLPackage.setFontMapper(fontMapper);
        // 使用 try-with-resources 自动管理资源
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 将 Docx 转换为 PDF 并保存到输出流
        Docx4J.toPDF(wordMLPackage, outputStream);
        // 将字节数组返回
        return outputStream.toByteArray();
    }


    //如果需要保存到文件进行调试
//    private void savePdfToFile(byte[] pdfBytes, String filePath) throws IOException {
//        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
//            fileOutputStream.write(pdfBytes);
//        }
//    }

    /**
     * 加载word模板并替换占位符
     *
     * @param data 要赋值的数据
     * @return
     * @throws IOException
     */
    private ByteArrayInputStream tyLoadWordTemplate(Map<String, String> data) throws Exception {
        // 加载模板文件（这里假设模板位于classpath下）
        InputStream templateStream = getClass().getResourceAsStream("/templates/TY_Templete.docx");
        if (templateStream == null) {
            throw new IllegalArgumentException("模板文件未找到");
        }
        // 加载模板
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
        // 准备变量替换
        VariablePrepare.prepare(wordMLPackage);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        documentPart.variableReplace(data); // 填充字段
        // 设置字体映射（如需要）
        Mapper fontMapper = new IdentityPlusMapper();
        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
        wordMLPackage.setFontMapper(fontMapper);
        // 设置行间距为1.5倍
        setLineSpacingToOneAndHalf(wordMLPackage);
        // 将修改后的文档写入字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wordMLPackage.save(baos);
        byte[] bytes = baos.toByteArray();
//        //将填充后的Word文件保存到本地
//        FileOutputStream wordOutputStream = new FileOutputStream("D://filled_sales_confirmation_template.docx");
//        wordOutputStream.write(bytes);
//        wordOutputStream.close(); // 确保文件流被关闭
        // 返回一个新的输入流以供后续PDF转换
        ByteArrayInputStream newWordInputStream = new ByteArrayInputStream(bytes);
        return newWordInputStream;
    }

    /**
     * 加载word模板并替换占位符
     *
     * @param data 要赋值的数据
     * @return
     * @throws IOException
     */
    private ByteArrayInputStream loadWordTemplate(Map<String, String> data) throws Exception {

        // 加载模板文件（这里假设模板位于classpath下）
        InputStream templateStream = getClass().getResourceAsStream("/templates/sales_confirmation_template.docx");
        if (templateStream == null) {
            throw new IllegalArgumentException("模板文件未找到");
        }

        // 加载模板
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);

        // 准备变量替换
        VariablePrepare.prepare(wordMLPackage);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        documentPart.variableReplace(data); // 填充字段

        // 设置字体映射（如需要）
        Mapper fontMapper = new IdentityPlusMapper();
        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
        wordMLPackage.setFontMapper(fontMapper);

        // 设置行间距为1.5倍
        setLineSpacingToOneAndHalf(wordMLPackage);

        // 将修改后的文档写入字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wordMLPackage.save(baos);
        byte[] bytes = baos.toByteArray();

        //将填充后的Word文件保存到本地
//        FileOutputStream wordOutputStream = new FileOutputStream("D://filled_sales_confirmation_template.docx");
//        wordOutputStream.write(bytes);
//        wordOutputStream.close(); // 确保文件流被关闭

        // 返回一个新的输入流以供后续PDF转换
        ByteArrayInputStream newWordInputStream = new ByteArrayInputStream(bytes);
        return newWordInputStream;
    }

    private void setLineSpacingToOneAndHalf(WordprocessingMLPackage wordMLPackage) {
        // 获取主文档部分
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        // 遍历所有的段落
        List<Object> paragraphs = documentPart.getContent();
        for (Object obj : paragraphs) {
            if (obj instanceof P) { // 检查是否为段落对象
                P paragraph = (P) obj;
                PPr ppr = paragraph.getPPr();
                if (ppr == null) {
                    ppr = Context.getWmlObjectFactory().createPPr();
                    paragraph.setPPr(ppr);
                }
                // 设置行距
                PPrBase.Spacing spacing = ppr.getSpacing() != null ? ppr.getSpacing() : Context.getWmlObjectFactory().createPPrBaseSpacing();
                spacing.setLine(BigInteger.valueOf(330)); // 1.5倍行距对应的值是240（twips）
                spacing.setLineRule(STLineSpacingRule.AUTO); // 自动调整行距规则

                ppr.setSpacing(spacing);
            }
        }
    }

    /**
     * 构建用于填充文档的数据映射。
     *
     * @param entity 订单行 VO
     * @return 数据映射
     */
    private Map<String, String> getDataMap(OrderLineVO entity) {
        Map<String, String> map = new HashMap<>();
        // 提取并保存订单类型以供后续逻辑使用
        String orderType = safeToString(entity.getOrderType());
        // 设置确认单类型的选中状态
        switch (orderType) {
            case "GP":
                map.put("orderTypeGP", "✓");
                map.put("orderTypeJJ", "");
                map.put("orderTypeZDJ", "");
                break;
            case "JJ":
                map.put("orderTypeGP", "");
                map.put("orderTypeJJ", "✓");
                map.put("orderTypeZDJ", "");
                break;
            case "ZDJ":
                map.put("orderTypeGP", "");
                map.put("orderTypeJJ", "");
                map.put("orderTypeZDJ", "✓");
                break;
            default:
                map.put("orderTypeGP", "");
                map.put("orderTypeJJ", "");
                map.put("orderTypeZDJ", "");
                break;
        }
        // 将订单类型添加到映射中，以便在模板中显示
        map.put("orderType", orderType);
        // 其他字段填充
        if (entity.getOrderNo() != null) {
            map.put("orderNo", safeToString(entity.getOrderNo()));
        } else {
            map.put("orderNo", "");
        }
        if (entity.getMaterielName() != null) {
            map.put("materielName", safeToString(entity.getMaterielName()));
        } else {
            map.put("materielName", "");
        }
        if (entity.getListedAmount() != null) {
            map.put("listedAmount", formatDecimal(entity.getListedAmount()));
        } else {
            map.put("listedAmount", "");
        }
        if (entity.getListedTaxPrice() != null) {
            map.put("listedTaxPrice", formatDecimal(entity.getListedTaxPrice()));
        } else {
            map.put("listedTaxPrice", "");
        }
        if (entity.getTotalAmount() != null) {
            map.put("totalAmount", formatDecimal(entity.getTotalAmount()));
        } else {
            map.put("totalAmount", "");
        }
        if (entity.getAreaName() != null) {
            map.put("areaName", safeToString(entity.getAreaName()));
        } else {
            map.put("areaName", "");
        }
        if (entity.getCashtransfercycle() != null) {
            map.put("cashtransfercycle", safeToString(entity.getCashtransfercycle()));
        } else {
            map.put("cashtransfercycle", "");
        }
        if (entity.getAcceptancepaymentcycle() != null) {
            map.put("acceptancepaymentcycle", safeToString(entity.getAcceptancepaymentcycle()));
        } else {
            map.put("acceptancepaymentcycle", "");
        }
        if (entity.getTransactionPrice() != null && entity.getTurnoverQuantity() != null) {
            // 计算成交金额
            BigDecimal totalAmount = entity.getTransactionPrice().multiply(entity.getTurnoverQuantity());
            // 定义税率
            BigDecimal taxRate = new BigDecimal("0.09"); // 9%
            // 计算不含税金额
            BigDecimal amountMoney = totalAmount.divide(BigDecimal.ONE.add(taxRate), 2, RoundingMode.HALF_UP);
            // 格式化不含税金额
            String formattedAmountMoney = formatDecimal(amountMoney);
            //不含税金额
            map.put("amountMoney", formattedAmountMoney);
            // 计算税额
            BigDecimal taxAmount = totalAmount.subtract(amountMoney);
            //税额
            map.put("taxAmount", formatDecimal(taxAmount));
        } else {
            map.put("amountMoney", "");
            map.put("taxAmount", "");
        }
        if (entity.getOrderApprovalTime() != null){
            // 格式化并设置生效日期
            Map<String, String> effectiveDate = formatDate(entity.getOrderApprovalTime());
            map.put("effectiveYear", effectiveDate.get("year"));
            map.put("effectiveMonth", effectiveDate.get("month"));
            map.put("effectiveDay", effectiveDate.get("day"));
        }else {
            // 格式化并设置生效日期
            map.put("effectiveYear", " ");
            map.put("effectiveMonth", " ");
            map.put("effectiveDay", " ");
        }
        if (entity.getListedAmount() != null) {
            // 将 Integer 类型的数量转换为 BigDecimal 类型
            BigDecimal amount = BigDecimal.valueOf(entity.getListedAmount());
            // 执行乘法操作
            BigDecimal totalPrice = entity.getListedTaxPrice().multiply(amount);
            // 如果需要，可以指定舍入模式。这里我们使用四舍五入，保留两位小数作为示例。
            // 注意：如果你不需要舍入，可以省略这一步。
            BigDecimal totalAmountCapital = totalPrice.setScale(2, RoundingMode.HALF_UP);
            String toChineseCapital = convertToChineseCapital(totalAmountCapital);
            // 设置大写的总金额
            map.put("totalAmountCapital", toChineseCapital);
        } else {
            map.put("totalAmountCapital", "");
        }
        return map;
    }

    /**
     * 构建用于填充文档的数据映射。
     *
     * @param entity 订单行 VO
     * @return 数据映射
     */
    private Map<String, String> tyGetDataMap(OrderLineVO entity) {
        Map<String, String> map = new HashMap<>();
        // 提取并保存订单类型以供后续逻辑使用
        String orderType = safeToString(entity.getOrderType());
        // 将订单类型添加到映射中，以便在模板中显示
        if ("GP".equals(orderType)) {
            map.put("orderType", "竞价销售");
        } else if ("JJ".equals(orderType)) {
            map.put("orderType", "定价销售");
        } else {
            map.put("orderType", "暂定价销售");
        }
        if (entity.getOrderNo() != null) {
            // 其他字段填充
            map.put("orderNo", safeToString(entity.getOrderNo()));
        } else {
            map.put("orderNo", "");
        }
        if (entity.getMaterielName() != null) {
            map.put("materielName", safeToString(entity.getMaterielName()));
        } else {
            map.put("materielName", "");
        }
        if (entity.getListedAmount() != null) {
            map.put("listedAmount", formatDecimal(entity.getListedAmount()));
        } else {
            map.put("listedAmount", "");
        }
        if (entity.getListedTaxPrice() != null) {
            map.put("listedTaxPrice", formatDecimal(entity.getListedTaxPrice()));
        } else {
            map.put("listedTaxPrice", "");
        }
        if (entity.getTotalAmount() != null) {
            map.put("totalAmount", formatDecimal(entity.getTotalAmount()));
        } else {
            map.put("totalAmount", "");
        }
        if (entity.getAreaName() != null) {
            map.put("areaName", safeToString(entity.getAreaName()));
        } else {
            map.put("areaName", "");
        }
        if (entity.getTransactionPrice() != null && entity.getTurnoverQuantity() != null) {
            // 计算成交金额
            BigDecimal totalAmount = entity.getTransactionPrice().multiply(entity.getTurnoverQuantity());
            // 定义税率
            BigDecimal taxRate = new BigDecimal("0.09"); // 9%
            // 计算不含税金额
            BigDecimal amountMoney = totalAmount.divide(BigDecimal.ONE.add(taxRate), 2, RoundingMode.HALF_UP);
            // 格式化不含税金额
            String formattedAmountMoney = formatDecimal(amountMoney);
            //不含税金额
            map.put("amountMoney", formattedAmountMoney);
            // 计算税额
            BigDecimal taxAmount = totalAmount.subtract(amountMoney);
            //税额
            map.put("taxAmount", formatDecimal(taxAmount));
        } else {
            map.put("amountMoney", "");
            map.put("taxAmount", "");
        }
        if (entity.getOrderApprovalTime() != null){
            // 格式化并设置生效日期
            Map<String, String> effectiveDate = formatDate(entity.getOrderApprovalTime());
            map.put("effectiveYear", effectiveDate.get("year"));
            map.put("effectiveMonth", effectiveDate.get("month"));
            map.put("effectiveDay", effectiveDate.get("day"));
        }else {
            // 格式化并设置生效日期
            map.put("effectiveYear", " ");
            map.put("effectiveMonth", " ");
            map.put("effectiveDay", " ");
        }
        if (entity.getListedAmount() != null) {
            // 将 Integer 类型的数量转换为 BigDecimal 类型
            BigDecimal amount = BigDecimal.valueOf(entity.getListedAmount());
            // 执行乘法操作
            BigDecimal totalPrice = entity.getListedTaxPrice().multiply(amount);

            // 如果需要，可以指定舍入模式。这里我们使用四舍五入，保留两位小数作为示例。
            // 注意：如果你不需要舍入，可以省略这一步。
            BigDecimal totalAmountCapital = totalPrice.setScale(2, RoundingMode.HALF_UP);
            String toChineseCapital = convertToChineseCapital(totalAmountCapital);
            // 设置大写的总金额
            map.put("totalAmountCapital", toChineseCapital);
        } else {
            map.put("totalAmountCapital", "");
        }
        return map;
    }


    /**
     * 安全地将对象转换为字符串。
     *
     * @param value 对象值
     * @return 字符串表示
     */
    private String safeToString(Object value) {
        return value != null ? value.toString() : "";
    }

    /**
     * 格式化小数点后两位。
     *
     * @param number 数值对象
     * @return 格式化后的字符串，如果输入为 null，则返回 "0.00"
     */
    private String formatDecimal(Number number) {
        if (number == null) {
            return "0.00";
        }

        // 将输入的 Number 转换为 BigDecimal
        BigDecimal decimal = new BigDecimal(number.toString());

        // 设置小数点后保留两位，并指定舍入模式
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        // 返回格式化后的字符串表示形式
        return decimal.toString();
    }

    /**
     * 格式化日期为“年 月 日”的各个部分。
     *
     * @param date 日期对象
     * @return 包含年、月、日的映射
     */
    private Map<String, String> formatDate(Date date) {
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

        return Map.of(
                "year", sdfYear.format(date),
                "month", sdfMonth.format(date),
                "day", sdfDay.format(date)
        );
    }

    // 汉字数字字符数组
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] CN_MONETARY_UNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆"};
    private static final String CN_INTEGER = "整";
    private static final String CN_DECIMAL = "角分";

    /**
     * 将数值金额转换为中文大写形式。
     *
     * @param roundedAmount 数值金额
     * @return 中文大写形式的金额字符串
     */
    private String convertToChineseCapital(BigDecimal roundedAmount) {

        // 设置精度并四舍五入
        StringBuilder result = new StringBuilder();

        // 分离整数和小数部分
        String intPart = roundedAmount.toBigInteger().toString();
        BigDecimal decPartBD = roundedAmount.subtract(new BigDecimal(intPart)).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN);
        String decPart = decPartBD.toString();

        // 处理整数部分
        boolean hasNonZero = false; // 标记是否有非零数字
        for (int i = 0; i < intPart.length(); i++) {
            char ch = intPart.charAt(i);
            int unitPos = intPart.length() - i - 1;
            if (ch != '0') {
                result.append(CN_UPPER_NUMBER[ch - '0']).append(CN_MONETARY_UNIT[unitPos]);
                hasNonZero = true;
            } else if (hasNonZero && !result.toString().endsWith(CN_UPPER_NUMBER[0])) {
                result.append(CN_UPPER_NUMBER[0]);
            }
        }

        // 如果整数部分全是零，则直接返回"零元整"
        if (!hasNonZero) {
            return CN_UPPER_NUMBER[0] + CN_MONETARY_UNIT[0] + CN_INTEGER;
        }

        // 添加“元”
//        result.append(CN_MONETARY_UNIT[0]);

        // 去除末尾多余的“零”
        while (result.length() > 1 && result.substring(result.length() - 2).equals("零元")) {
            result.setLength(result.length() - 2);
        }

        // 处理小数部分
        boolean isDecZero = true;
        for (int i = 0; i < decPart.length(); i++) {
            if (decPart.charAt(i) != '0') {
                result.append(CN_UPPER_NUMBER[decPart.charAt(i) - '0']).append(CN_DECIMAL.charAt(i));
                isDecZero = false;
            }
        }

        // 如果小数部分为零，则添加“整”
        if (isDecZero) {
            result.append(CN_INTEGER);
        }

        return result.toString();
    }


}
