package com.ccit.area.sales.dao.mapper.customerprofile;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.customer.TAnnualAgreementPO;
import com.ccit.area.sales.dao.vo.customer.TAnnualAgreementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_annual_agreement(年度协议表)】的数据库操作Mapper
* @createDate 2024-10-18 15:26:38
* @Entity com.ccit.area.sales.dao.bean.po.TAnnualAgreementPO
*/
public interface AnnualAgreementMapper extends BaseMapper<TAnnualAgreementPO> {
    List<TAnnualAgreementVO> selectPageList(Page<TAnnualAgreementVO> pages,
                                              @Param(value = "param") Map<String, Object> param);
}




