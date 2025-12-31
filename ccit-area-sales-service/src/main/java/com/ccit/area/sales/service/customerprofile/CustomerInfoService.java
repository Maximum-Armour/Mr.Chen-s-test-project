package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.CustomerPO;
import com.ccit.area.sales.dao.dto.customer.CustomerDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateCustomerDTO;
import com.ccit.area.sales.dao.vo.customer.CustomerVO;

import java.util.Map;

/**
 * @author Baishangqianxue
 * @description 针对表【t_company_info(企业基本信息表)】的数据库操作Service
 * @createDate 2024-10-12 14:14:30
 */
public interface CustomerInfoService extends IService<CustomerPO> {

    String updateStatus(UpdateCustomerDTO updateCustomerDTO);

    Page<CustomerVO> selectPageList(Map<String, Object> param);

//    Page<TCompanyInfoUpdateVO> selectCompanyInfoUpdatePageList(Map<String, Object> param);

    CustomerVO getBasicInformation(Long id);

    String updateCustomerInfo(CustomerDTO customerDTO);

    byte[] deriveExcel();

}
