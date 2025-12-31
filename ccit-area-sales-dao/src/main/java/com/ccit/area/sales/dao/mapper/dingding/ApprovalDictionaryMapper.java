package com.ccit.area.sales.dao.mapper.dingding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.dingding.dto.TApprovalDictionaryDTO;
import com.ccit.area.sales.dao.dingding.po.TApprovalDictionaryPO;
import org.apache.ibatis.annotations.Param;

public interface ApprovalDictionaryMapper extends BaseMapper<TApprovalDictionaryPO> {

    int updateApprovalDictionary(@Param("entity") TApprovalDictionaryDTO entity);
}
