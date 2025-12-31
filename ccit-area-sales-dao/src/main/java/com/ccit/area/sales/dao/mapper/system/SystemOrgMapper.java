package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.system.SystemOrgPO;
import com.ccit.area.sales.dao.vo.system.SystemGroupOrgNameVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgVO;

import java.util.List;

/**
 *  * 描述 : “组织”接口类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月9日 上午10:55:33
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.mapper.system
 *  * 类名 : SystemOrgMapper
 */
public interface SystemOrgMapper extends BaseMapper<SystemOrgPO> {
    List<SystemGroupOrgNameVO> selectGroupOrgName();

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月15日 下午5:28:29
     * 描述 : 获取编码个数
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : orgNoCount
     * Long
     *
     * @throws
     */
    Long orgNoCount();

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 下午5:28:29
     * 描述 : 获取编码简称
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : orgAbbreviation
     * Long
     *
     * @throws
     */
    Long orgNoAbbreviationCount(String orgNoAbbreviation);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 下午5:28:29
     * 描述 : 获取编码简称
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : orgAbbreviation
     * String
     *
     * @throws
     */
    String orgAbbreviation(String orgNo);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月30日 下午5:28:29
     * 描述 : 获取编码全称
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : orgName
     * String
     *
     * @throws
     */
    List<String> orgName(Long parentId);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月30日 下午5:28:29
     * 描述 : 获取编码全称
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : orgName
     * String
     *
     * @throws
     */
    List<String> editorgName(Long parentId, Long id);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月6日 下午5:28:29
     * 描述 : 获取子集个数
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : selectchildren
     * Long
     *
     * @throws
     */
    Long selectChildren(Long parentId);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月6日 下午5:28:29
     * 描述 : 更新子集
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : updateChildren
     * void
     *
     * @throws
     */
    void updateChildren(Integer status, Long parentId);

    String getCorporationOrg(String orgNo);

   List<String> getOrgList(String orgNoAbbreviation);

    SystemOrgVO getFatherOrgNo(String orgNoAbbreviation);
}
