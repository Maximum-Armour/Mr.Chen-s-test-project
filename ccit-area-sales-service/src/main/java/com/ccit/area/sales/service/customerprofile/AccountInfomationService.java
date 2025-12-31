package com.ccit.area.sales.service.customerprofile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.customer.TAccountInfomationPO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationDTO;
import com.ccit.area.sales.dao.dto.customer.UpdateAccountInformationLockDTO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationVO;
import com.ccit.area.sales.dao.vo.customer.TAccountInfomationsVO;

import java.util.Map;

/**
* @author Baishangqianxue
* @description 针对表【t_account_infomation(企业注册账户信息表)】的数据库操作Service
* @createDate 2024-10-11 14:20:25
*/
public interface AccountInfomationService extends IService<TAccountInfomationPO> {

    String updateStatus(UpdateAccountInformationDTO accountInformation);

    Page<TAccountInfomationVO> selectPageList(Map<String, Object> param);

    TAccountInfomationsVO getRegisterAccountMessage(Long id);


    String updateLockStatus(UpdateAccountInformationLockDTO accountInformation);

}
