package com.ccit.area.sales.dao.mapper.customerprofile;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TCustomerQualificationPO;
import com.ccit.area.sales.dao.vo.customer.TCustomerQualificationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_customer_qualification(客户资质)】的数据库操作Mapper
* @createDate 2024-10-18 17:42:00
* @Entity com.ccit.area.sales.dao.bean.po.TCustomerQualification
*/
public interface CustomerQualificationMapper extends BaseMapper<TCustomerQualificationPO> {
    List<TCustomerQualificationVO> selectPageList(Page<TCustomerQualificationVO> pages,
                                                  @Param(value = "param") Map<String, Object> param);
}




