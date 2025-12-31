package com.ccit.area.sales.service.customerprofile.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.dao.domain.customer.TAnnualAgreementPO;
import com.ccit.area.sales.dao.mapper.customerprofile.AnnualAgreementMapper;
import com.ccit.area.sales.dao.vo.customer.TAnnualAgreementVO;
import com.ccit.area.sales.service.customerprofile.AnnualAgreementService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_annual_agreement(年度协议表)】的数据库操作Service实现
* @createDate 2024-10-18 15:26:38
*/
@Service
public class AnnualAgreementServiceImpl extends ServiceImpl<AnnualAgreementMapper, TAnnualAgreementPO>
    implements AnnualAgreementService {

    @Override
    public Page<TAnnualAgreementVO> selectPageList(Map<String, Object> param) {
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
        Page<TAnnualAgreementVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);
        // 执行数据库查询
        pages.setRecords(baseMapper.selectPageList(pages, param));

        return pages;
    }
}




