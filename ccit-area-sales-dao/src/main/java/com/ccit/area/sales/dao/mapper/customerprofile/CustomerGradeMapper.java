package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TCustomerGradePO;
import com.ccit.area.sales.dao.vo.customer.TCustomerGradeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_customer_grade(客户级别表)】的数据库操作Mapper
* @createDate 2024-10-18 09:54:14
* @Entity com.ccit.area.sales.dao.bean.po.TCustomerGradePO
*/
public interface CustomerGradeMapper extends BaseMapper<TCustomerGradePO> {

    List<TCustomerGradeVO> selectPageList(Page<TCustomerGradeVO> pages,
                                          @Param(value = "param") Map<String, Object> param);
}




