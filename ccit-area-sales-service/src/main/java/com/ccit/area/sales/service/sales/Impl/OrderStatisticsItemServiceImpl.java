package com.ccit.area.sales.service.sales.Impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.CurrentUserUtil;
import com.ccit.area.sales.common.utils.CustomCellStyleStrategy;
import com.ccit.area.sales.common.utils.CustomMergeStrategy;
import com.ccit.area.sales.dao.domain.sales.OrderStatisticsItemPO;
import com.ccit.area.sales.dao.dto.sales.OrderStatisticsItemUpdateDTO;
import com.ccit.area.sales.dao.mapper.sales.OrderStatisticsItemMapper;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsItemExcelConverter;
import com.ccit.area.sales.dao.vo.sales.OrderStatisticsItemVO;
import com.ccit.area.sales.dao.vo.system.SystemCurrentUserVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;
import com.ccit.area.sales.dao.vo.system.SystemUserDetailVO;
import com.ccit.area.sales.service.sales.OrderStatisticsItemService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Baishangqianxue
 * @description 针对表【t_order_statistics_item(接单统计明细表)】的数据库操作Service实现
 * @createDate 2025-02-24 16:55:48
 */
@Service
public class OrderStatisticsItemServiceImpl extends ServiceImpl<OrderStatisticsItemMapper, OrderStatisticsItemPO>
        implements OrderStatisticsItemService {

    @Autowired
    private ISystemUserService iSystemUserService;
    @Autowired
    private ISystemOrgService isSystemOrgService;

    @Override
    public List<OrderStatisticsItemVO> getItem(String takeOrderNo) {

        LambdaQueryWrapper<OrderStatisticsItemPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderStatisticsItemPO::getTakeOrderNo, takeOrderNo);
        wrapper.eq(OrderStatisticsItemPO::getDeleted, 0);
        List<OrderStatisticsItemPO> orderStatisticsItemPOList = this.baseMapper.selectList(wrapper);

        if (!orderStatisticsItemPOList.isEmpty()) {
            return orderStatisticsItemPOList.stream().map(t -> {
                OrderStatisticsItemVO orderStatisticsItemVO = new OrderStatisticsItemVO();
                BeanUtils.copyProperties(t, orderStatisticsItemVO);
                return orderStatisticsItemVO;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String deleteItem(List<Long> ids) {
        LambdaUpdateWrapper<OrderStatisticsItemPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(OrderStatisticsItemPO::getId, ids);
        wrapper.set(OrderStatisticsItemPO::getDeleted, 1);
        int update = this.baseMapper.update(null, wrapper);
        if (update > 0) {
            return "删除成功";
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public byte[] deriveExcel() {

        // 获取用户信息
        JSONObject userJson = CurrentUserUtil.getCurrentUser();
        SystemCurrentUserVO systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
        if (systemUser == null) {
            throw new BusinessException("用户信息不存在");
        }

        // 获取公司信息
        SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemUser.getUserName());
        if (selectUserDetails == null) {
            throw new BusinessException("公司信息不存在");
        }
        SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(selectUserDetails.getOrgNo());
        if (fatherOrgNo == null) {
            throw new BusinessException("公司信息不存在");
        }

        // 查询数据库
        LambdaQueryWrapper<OrderStatisticsItemPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderStatisticsItemPO::getCompanyNo, fatherOrgNo.getOrgNo());
        List<OrderStatisticsItemPO> orderStatisticsItemPOS = this.baseMapper.selectList(wrapper);
        if (orderStatisticsItemPOS.isEmpty()) {
            throw new RuntimeException("客户信息为空，无法导出文件");
        }
        try {
            // 创建输出流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 数据转换和分组
            Map<String, List<OrderStatisticsItemPO>> groupedByCustomer = orderStatisticsItemPOS.stream()
                    .collect(Collectors.groupingBy(OrderStatisticsItemPO::getMaterielName));

            List<OrderStatisticsItemExcelConverter> dataList = new ArrayList<>();
            // 计算小计并插入数据
            BigDecimal totalQuantity = BigDecimal.ZERO;

            for (Map.Entry<String, List<OrderStatisticsItemPO>> entry : groupedByCustomer.entrySet()) {
                String customerName = entry.getKey();
                List<OrderStatisticsItemPO> items = entry.getValue();
                AtomicInteger atomicInteger = new AtomicInteger(0);
                // 转换数据
                List<OrderStatisticsItemExcelConverter> convertedItems = items.stream()
                        .map(t -> {
                            OrderStatisticsItemExcelConverter converter = new OrderStatisticsItemExcelConverter();
                            BeanUtils.copyProperties(t, converter);
                            converter.setRemark("已签订协议");
                            converter.setSerialNumber(atomicInteger.incrementAndGet());
                            return converter;
                        }).collect(Collectors.toList());
                // 计算小计
                BigDecimal subtotalQuantity = items.stream()
                        .map(OrderStatisticsItemPO::getOrderSubmissionPrice)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 添加小计行
                OrderStatisticsItemExcelConverter subtotalRow = OrderStatisticsItemExcelConverter.createSubtotalRow(customerName, subtotalQuantity);
                convertedItems.add(subtotalRow);

                // 更新总计
                totalQuantity = totalQuantity.add(subtotalQuantity);

                // 添加到数据列表
                dataList.addAll(convertedItems);
            }

            // 添加总计行
            OrderStatisticsItemExcelConverter totalRow = OrderStatisticsItemExcelConverter.createTotalRow(totalQuantity);
            dataList.add(totalRow);

            // 计算字段数量
            long fieldCount = Arrays.stream(OrderStatisticsItemExcelConverter.class.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(ExcelProperty.class))
                    .count();

            // 获取字段标题（横向插入）
            List<List<String>> excelHeader = List.of(
                    List.of("序号", "客户", "产品", "销售单价(元/吨)", "需求吨数", "备注")
            );

            // 需要合并的行
            List<Integer> mergeRows = List.of(0, dataList.size() + 1);

            // 创建 EasyExcel 写入对象
            ExcelWriter excelWriter = EasyExcel.write(byteArrayOutputStream, OrderStatisticsItemExcelConverter.class)
                    .registerWriteHandler(new CustomMergeStrategy(mergeRows, (int) fieldCount))
                    .registerWriteHandler(new CustomCellStyleStrategy()) // 自定义样式
                    .needHead(Boolean.FALSE) // 禁用 EasyExcel 自动插入表头
                    .build();

            WriteSheet writeSheet = EasyExcel.writerSheet("接单情况").build();

//            // 插入第一行：标题
//            if ("000002".equals(fatherOrgNo.getOrgNo())){
                List<List<String>> titleRow = List.of(List.of("蒙陕公司尿素地销接单情况"));
                excelWriter.write(titleRow, writeSheet);
//            }else if ("000009".equals(fatherOrgNo.getOrgNo())){
//                List<List<String>> titleRow = List.of(List.of("蒙陕公司尿素地销接单情况"));
//                excelWriter.write(titleRow, writeSheet);
//            }else {
//                throw new BusinessException("该公司不支持导出报表");
//            }

            // 插入第二行：字段标题
            excelWriter.write(excelHeader, writeSheet);

            // 写入数据
            excelWriter.write(dataList, writeSheet);

            excelWriter.finish();

            // 保存 Excel 文件到本地
//            String filePath = "E:/蒙陕公司尿素地销接单情况（1.14）.xlsx";
//            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
//                fileOutputStream.write(byteArrayOutputStream.toByteArray());
//            } catch (IOException e) {
//                log.error("写入本地文件失败", e);
//                throw new BusinessException("写入本地文件失败!");
//            }

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("导出 Excel 失败", e);
            throw new RuntimeException("导出excel失败",e);
        }
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateItem(List<OrderStatisticsItemUpdateDTO> entity) {
        for (OrderStatisticsItemUpdateDTO order : entity) {
            // 判断id是否为空，不为空则更新，为空则新增
            if (order.getId() != null) {
                // 如果 isDeleted 不为空则为删除逻辑
                OrderStatisticsItemPO orderStatisticsItemPO = new OrderStatisticsItemPO();
                BeanUtils.copyProperties(order, orderStatisticsItemPO);
                LambdaUpdateWrapper<OrderStatisticsItemPO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OrderStatisticsItemPO::getId, order.getId());
                int update = this.baseMapper.update(orderStatisticsItemPO, updateWrapper);
                if (update < 0) {
                    throw new BusinessException("更新失败");
                }
            } else {
                //获取用户信息
                JSONObject userJson = CurrentUserUtil.getCurrentUser();
                SystemCurrentUserVO systemUser = JSON.toJavaObject(userJson, SystemCurrentUserVO.class);
                if (systemUser == null) {
                    throw new BusinessException("用户信息不存在");
                }
                //获取公司信息
                SystemUserDetailVO selectUserDetails = iSystemUserService.selectUserDetails(systemUser.getUserName());
                if (selectUserDetails == null) {
                    throw new BusinessException("公司信息不存在");
                }
                SystemOrgVO fatherOrgNo = isSystemOrgService.getFatherOrgNo(selectUserDetails.getOrgNo());
                if (fatherOrgNo == null) {
                    throw new BusinessException("公司信息不存在");
                }
                //新增接单统计表
                OrderStatisticsItemPO orderStatisticsItemPO = new OrderStatisticsItemPO();
                BeanUtils.copyProperties(order, orderStatisticsItemPO);
                orderStatisticsItemPO.setCreateBy(systemUser.getUserName());
                orderStatisticsItemPO.setCreateByName(systemUser.getRealName());
                orderStatisticsItemPO.setCompanyNo(fatherOrgNo.getOrgNo());
                orderStatisticsItemPO.setCompanyName(fatherOrgNo.getOrgName());
                int insert = this.baseMapper.insert(orderStatisticsItemPO);
                if (insert < 0) {
                    throw new BusinessException("新增失败");
                }
            }
        }
        return "保存成功";
    }


}




