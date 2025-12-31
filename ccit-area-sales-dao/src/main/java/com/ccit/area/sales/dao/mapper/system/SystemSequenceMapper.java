package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.system.SystemSequencePO;
import com.ccit.area.sales.dao.vo.system.SystemGroupOrgNameVO;

/**
 * 
 * 描述 : “序列号”接口类
 * 创建人 : yn
 * 创建时间 : 2024年08月22日 上午10:05:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemSequenceMapper
 */
public interface SystemSequenceMapper extends BaseMapper<SystemSequencePO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月3日 下午5:28:29
     * 描述 : 获取组织信息
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : selectOrg
     *  SystemGroupOrgNameVO
     *  @throws
     */
    SystemGroupOrgNameVO selectOrg(String orgNo);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年12月3日 下午5:28:29
     * 描述 : 获取序列号值
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : selectValue
     *  Long
     *  @throws
     */
    Long selectValue(String code);
}