package com.ccit.area.sales.service.dingding.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.dao.dingding.dto.TApprovalDictionaryDTO;
import com.ccit.area.sales.dao.dingding.po.TApprovalDictionaryPO;
import com.ccit.area.sales.dao.mapper.dingding.ApprovalDictionaryMapper;
import com.ccit.area.sales.service.dingding.ApprovalDictionaryService;
import org.springframework.stereotype.Service;

/**
 * @author Baishangqianxue
 * @description 针对表【t_approval_dictionary(钉钉模板类型字典表)】的数据库操作Service实现
 * @createDate 2024-10-23 13:58:03
 */
@Service
public class TApprovalDictionaryServiceImpl extends ServiceImpl<ApprovalDictionaryMapper, TApprovalDictionaryPO>
        implements ApprovalDictionaryService {

    @Override
    public String addApprovalDictionary(TApprovalDictionaryPO tApprovalDictionaryPO) {
        int insert = this.baseMapper.insert(tApprovalDictionaryPO);
        if (insert > 0) {
            return "新增成功";
        } else {
            return "新增失败";
        }
    }

    @Override
    public String updateApprovalDictionary(TApprovalDictionaryDTO tApprovalDictionaryDTO) {
        int status = this.baseMapper.updateApprovalDictionary(tApprovalDictionaryDTO);
        if (status > 0) {
            return "新增成功";
        }
        throw new BusinessException("更新字典值失败！");
    }
}




