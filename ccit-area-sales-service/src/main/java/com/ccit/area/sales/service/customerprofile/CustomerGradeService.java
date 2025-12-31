package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TCustomerGradePO;
import com.ccit.area.sales.dao.vo.customer.TCustomerGradeVO;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_customer_grade(客户级别表)】的数据库操作Service
* @createDate 2024-10-18 09:54:14
*/
public interface CustomerGradeService extends IService<TCustomerGradePO> {

    Page<TCustomerGradeVO> selectPageList(Map<String, Object> param);
}
