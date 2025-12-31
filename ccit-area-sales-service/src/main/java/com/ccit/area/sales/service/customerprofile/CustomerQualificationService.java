package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TCustomerQualificationPO;
import com.ccit.area.sales.dao.vo.customer.TCustomerQualificationVO;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_customer_qualification(客户资质)】的数据库操作Service
* @createDate 2024-10-18 17:42:00
*/
public interface CustomerQualificationService extends IService<TCustomerQualificationPO> {

    Page<TCustomerQualificationVO> selectPageList(Map<String, Object> param);
}
