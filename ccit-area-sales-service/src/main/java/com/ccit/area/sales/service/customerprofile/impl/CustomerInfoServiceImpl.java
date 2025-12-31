package com.ccit.area.sales.service.customerprofile.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.SortUtil;
import com.ccit.area.sales.dao.domain.customer.CustomerPO;
import com.ccit.area.sales.dao.dto.customer.CustomerDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateCustomerDTO;
import com.ccit.area.sales.dao.mapper.customerprofile.CustomerInfoMapper;
import com.ccit.area.sales.dao.vo.customer.CustomerVO;
import com.ccit.area.sales.service.customerprofile.CustomerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Baishangqianxue
 * @description 针对表【t_company_info(企业基本信息表)】的数据库操作Service实现
 * @createDate 2024-10-12 14:14:30
 */
@Service
@Slf4j
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerPO>
        implements CustomerInfoService {

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateStatus(UpdateCustomerDTO updateCustomerDTO) {
        UpdateWrapper<CustomerPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", updateCustomerDTO.getStatus());
        updateWrapper.in("id", updateCustomerDTO.getIds());
        int update = this.baseMapper.update(null, updateWrapper);
        if (update > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败!");
    }

    @Override
    public Page<CustomerVO> selectPageList(Map<String, Object> param) {

        // 获取并验证页码和每页大小
        Integer page = param.containsKey("page") ? (Integer) param.get("page") : null;
        Integer size = param.containsKey("size") ? (Integer) param.get("size") : null;

        int defaultPage = 1; // 默认页码
        int defaultSize = 10; // 默认每页大小

        if (page == null || page <= 0) {
            page = defaultPage;
        }
        if (size == null || size <= 0) {
            size = defaultSize;
        }

        // 创建分页对象
        Page<CustomerVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("status", GlobalConstants.DELETE_NO);
        param.put("is_deleted", GlobalConstants.DELETE_NO);
        if (!Objects.isNull(param.get("field"))) {
            // 将转换后的 key 和原始的 value 放入 param
            param.put("field", SortUtil.convertCamelToUnderline(param.get("field")));
        }
        // 执行数据库查询
        pages.setRecords(this.baseMapper.selectPageList(pages, param));
        return pages;
    }


    @Override
    public CustomerVO getBasicInformation(Long id) {
        CustomerPO customerPO = this.baseMapper.selectById(id);
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customerPO, customerVO);
        return customerVO;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String updateCustomerInfo(CustomerDTO customerDTO) {
        CustomerPO customerPO = new CustomerPO();
        BeanUtils.copyProperties(customerDTO, customerPO);
        // 使用 UpdateWrapper 来构建更新条件
        UpdateWrapper<CustomerPO> updateWrapper = new UpdateWrapper<>();
        // 根据 id 更新，确保这里使用的字段是唯一的或主键
        updateWrapper.eq("id", customerDTO.getId());
        int update = this.baseMapper.update(customerPO, updateWrapper);
        if (update > 0) {
            return "修改成功";
        }
        throw new BusinessException("修改失败!");
    }

    @Override
    public byte[] deriveExcel() {
        try {
            // 使用 ByteArrayOutputStream 来保存 Excel 文件的内容
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 获取所有公司信息的列表
            List<CustomerPO> customerPOList = this.baseMapper.selectList(null);
            List<CustomerVO> customerVOList = customerPOList.stream().map(t -> {
                CustomerVO customerVO = new CustomerVO();
                BeanUtils.copyProperties(t, customerVO);
                return customerVO;
            }).collect(Collectors.toList());
            // 检查数据源是否为空
            if (customerPOList.isEmpty()) {
                throw new RuntimeException("客户信息为空，无法导出文件");
            }
            // 这里 需要指定写用哪个class去写
            EasyExcel.write(byteArrayOutputStream, CustomerVO.class).sheet("客户基本信息").doWrite(customerVOList);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("导出excel失败", e);
            throw new BusinessException("导出excel失败!");
        }
    }
}




