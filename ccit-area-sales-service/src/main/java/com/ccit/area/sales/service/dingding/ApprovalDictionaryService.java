package com.ccit.area.sales.service.dingding;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.dingding.dto.TApprovalDictionaryDTO;
import com.ccit.area.sales.dao.dingding.po.TApprovalDictionaryPO;

/**
* @author Baishangqianxue
* @description 针对表【t_approval_dictionary(钉钉模板类型字典表)】的数据库操作Service
* @createDate 2024-10-23 13:58:03
*/
public interface
ApprovalDictionaryService extends IService<TApprovalDictionaryPO> {
   String addApprovalDictionary(TApprovalDictionaryPO tApprovalDictionaryPO);

   String updateApprovalDictionary(TApprovalDictionaryDTO tApprovalDictionaryDTO);

}
