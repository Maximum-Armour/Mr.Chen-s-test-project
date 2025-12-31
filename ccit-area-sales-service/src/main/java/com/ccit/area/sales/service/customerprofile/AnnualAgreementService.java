package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TAnnualAgreementPO;
import com.ccit.area.sales.dao.vo.customer.TAnnualAgreementVO;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_annual_agreement(年度协议表)】的数据库操作Service
* @createDate 2024-10-18 15:26:38
*/
public interface AnnualAgreementService extends IService<TAnnualAgreementPO> {

   Page<TAnnualAgreementVO> selectPageList(Map<String, Object> param);
}
