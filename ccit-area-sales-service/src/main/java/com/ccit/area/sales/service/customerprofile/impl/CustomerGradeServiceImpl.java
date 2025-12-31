package com.ccit.area.sales.service.customerprofile.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.dao.domain.customer.TCustomerGradePO;
import com.ccit.area.sales.dao.mapper.customerprofile.CustomerGradeMapper;
import com.ccit.area.sales.dao.vo.customer.TCustomerGradeVO;
import com.ccit.area.sales.service.customerprofile.CustomerGradeService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_customer_grade(客户级别表)】的数据库操作Service实现
* @createDate 2024-10-18 09:54:14
*/
@Service
public class CustomerGradeServiceImpl extends ServiceImpl<CustomerGradeMapper, TCustomerGradePO>
    implements CustomerGradeService {


    @Override
    public Page<TCustomerGradeVO> selectPageList(Map<String, Object> param) {
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
        Page<TCustomerGradeVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);

        // 执行数据库查询
        pages.setRecords(this.baseMapper.selectPageList(pages, param));

        return pages;
    }
}




