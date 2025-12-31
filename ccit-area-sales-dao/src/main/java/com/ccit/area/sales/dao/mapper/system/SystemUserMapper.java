package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.system.SystemUserPO;
import com.ccit.area.sales.dao.vo.system.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “用户”接口类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月4日 上午10:31:46
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.mapper
 *  * 类名 : SystemUserMapper
 */
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUserPO> {

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午1:57:56
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectPageList
     * List<SystemUserPageListVO>
     *
     * @throws
     */
    List<SystemUserPageListVO> selectPageList(Page<SystemUserPageListVO> pages,
                                              @Param(value = "param") Map<String, Object> param);

    SystemUserDetailVO selectUserDetails(@Param(value = "userName") String userName);

    List<SystemUserDetailVO> selectUserDetailsList(@Param(value = "userName") String userName);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 组织
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectOrg
     * List<SystemOrgVO>
     *
     * @throws
     */
    List<SystemOrgVO> selectOrg(String userName);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 组织类型
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectOrgType
     * List<SystemGroupOrgNameVO>
     *
     * @throws
     */
    List<SystemGroupOrgNameVO> selectOrgType(String userName);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 组织类型
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectOrgName
     * String
     *
     * @throws
     */
    String selectOrgName(Long parentId);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 更新组织
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : display
     * void
     *
     * @throws
     */
    int display(String userId);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 更新组织
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : displayStatus
     * void
     *
     * @throws
     */
    int displayStatus(String orgId, String userId);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月20日 下午1:57:56
     * 描述 : 获取展示
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectDisplay
     * List<SystemGroupOrgNameVO>
     * @throws
     */
    List<SystemGroupOrgNameVO> selectDisplay(String userId);

    SystemUserDetailsVO getUserName(@Param(value = "userName") String userName);
}
