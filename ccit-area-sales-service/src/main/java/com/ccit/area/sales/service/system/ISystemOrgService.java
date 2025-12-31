package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemOrgPO;
import com.ccit.area.sales.dao.dto.system.SystemOrgAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgEditDTO;
import com.ccit.area.sales.dao.vo.system.*;

import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “组织”服务类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月9日 上午11:00:22
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.system
 *  * 类名 : ISystemOrgService
 */
public interface ISystemOrgService extends IService<SystemOrgPO> {

    /**
     * 创建人 : yn
     * 创建时间 : 2024年9月25日 下午3:22:49
     * 描述 : 树列表，支持高级查询
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : selectTreeList
     * List<SystemOrgTreeVO>
     *
     * @throws
     */
    List<SystemOrgTreeVO> selectTreeList(Map<String, Object> param);

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:14:35
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : add
     * String
     *
     * @throws
     */
    String add(SystemOrgAddDTO entity);

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:14:40
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : get
     * SystemOrgDetailsVO
     *
     * @throws
     */
    SystemOrgDetailsVO get(Long id);

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:14:44
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : edit
     * String
     *
     * @throws
     */
    String edit(SystemOrgEditDTO entity);

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:14:49
     * 描述 : 删除【一条&多条】数据
     * 包名 : com.ccit.area.sales.service.system
     * 方法名 : delete
     * String
     *
     * @throws
     */
    String delete(SystemOrgDeleteDTO entity);

    List<SystemOrgVO> selectList();

    List<SystemGroupOrgNameVO> selectGroupOrgName();

    String synchronizationOAOrgMassages(List<String> deptList);

//    /**
//     *
//     * 创建人 : tb
//     * 创建时间 : 2024年11月18日 下午5:28:29
//     * 描述 : 根据组织编码获取当前所在公司下所有组织编码
//     * 包名 : com.ccit.area.sales.dao.mapper.archives
//     * 方法名 : orgAbbreviation
//     *  String
//     *  @throws
//     */
//    String orgAbbreviation(String orgNo);
    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 下午5:28:29
     * 描述 : 根据组织编码获取当前所在公司下所有组织编码
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : isExistOrgNo
     *  String
     *  @throws
     */
    List<String> isExistOrgNo(String orgNo);

    /**
     *
     */
    SystemOrgVO getFatherOrgNo(String orgNo);

}
