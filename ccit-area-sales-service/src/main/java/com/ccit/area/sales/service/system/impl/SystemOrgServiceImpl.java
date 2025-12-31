package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.dao.dingding.po.DeptListPO;
import com.ccit.area.sales.dao.dingding.vo.Department;
import com.ccit.area.sales.dao.dingding.vo.RootDeptListVO;
import com.ccit.area.sales.dao.domain.system.SystemOrgPO;
import com.ccit.area.sales.dao.dto.system.SystemOrgAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemOrgEditDTO;
import com.ccit.area.sales.dao.mapper.system.SystemOrgMapper;
import com.ccit.area.sales.dao.vo.system.*;
import com.ccit.area.sales.service.dingding.ExamineService;
import com.ccit.area.sales.service.system.ISystemDictService;
import com.ccit.area.sales.service.system.ISystemOrgService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.area.sales.service.system.ISystemTreeNodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  * 描述 : “组织”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月9日 上午11:03:15
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.system.impl
 *  * 类名 : SystemOrgServiceImpl
 */
@Service
public class SystemOrgServiceImpl extends ServiceImpl<SystemOrgMapper, SystemOrgPO> implements ISystemOrgService {

    /**
     * “序列号”服务类
     */
    @Autowired
    private ISystemSequenceService systemSequenceService;

    /**
     * “树节点”服务类
     */
    @Autowired
    private ISystemTreeNodeService systemTreeNodeService;

    /**
     * “字典”服务类
     */
    @Autowired
    private ISystemDictService systemDictService;

    @Autowired
    @Lazy
    private ExamineService examineService;

    /**
     * 创建人 : yn
     * 创建时间 : 2024年9月25日 下午3:26:29
     * 描述 : 树列表，支持高级查询
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : selectTreeList
     * List<SystemOrgTreeVO>
     *
     * @throws
     */
    @Override
    @SuppressWarnings({"all"})
    public List<SystemOrgTreeVO> selectTreeList(Map<String, Object> param) {
        List<SystemOrgTreeVO> result = new ArrayList<>();
        LambdaQueryWrapper<SystemOrgPO> wrapper = SystemOrgPO.wrapper();
        wrapper.orderByAsc(SystemOrgPO::getOrgOrders);
        String orgName = (String) param.get("orgName");
        if (StringUtils.isNoneBlank(orgName)) {
            wrapper.like(SystemOrgPO::getOrgName, orgName);
        }
        Integer status = (Integer) param.get("status");
        if (status != null) {
            wrapper.eq(SystemOrgPO::getStatus, status);
        }
        List<SystemOrgPO> systemOrgList = this.baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(systemOrgList)) {
            List<SystemDictChildrenVO> childrenList = systemDictService.getChildrenList("orgType");
            Map<String, String> childrenMap = childrenList.stream()
                    .collect(Collectors.toMap(SystemDictChildrenVO::getDictValue, SystemDictChildrenVO::getDictName));
            List<SystemOrgTreeVO> list = ListUtil.arrayCopyTo(systemOrgList, SystemOrgTreeVO.class);
            list.forEach(v -> {
                v.setOrgTypeName(childrenMap.get(v.getOrgType()));
            });
            result = systemTreeNodeService.changeDataOrgTree(list);
        }
        return result;
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年7月9日 下午2:16:59
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : add
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String add(SystemOrgAddDTO entity) {
        LambdaQueryWrapper<SystemOrgPO> wrapper = SystemOrgPO.wrapper();
        wrapper.eq(SystemOrgPO::getOrgNo, entity.getOrgNo());
        Integer i1 = this.baseMapper.selectCount(wrapper);
        if (i1 > 0) {
            throw new BusinessException("组织编码已存在，请进行检查！");
        }
        List<String> strings = this.baseMapper.orgName(entity.getParentId());
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            String orgName = entity.getOrgName();
            if (Objects.equals(orgName, s)) {
                throw new BusinessException("组织全称已存在，请进行检查！");
            }
        }
        Long l1 = this.baseMapper.orgNoAbbreviationCount(entity.getOrgNoAbbreviation());
        if (l1 > 0) {
            throw new BusinessException("组织编码简称已存在，请进行检查！");
        }
        String code = "ZZ";
        String value = systemSequenceService.get(code, 6);
        SystemOrgPO systemOrg = new SystemOrgPO();
        entity.setOrgNo(value.substring(2, 8));
        BeanUtils.copyProperties(entity, systemOrg);
        wrapper = SystemOrgPO.wrapper();
        wrapper.eq(SystemOrgPO::getParentId, entity.getParentId());
        systemOrg.setOrgOrders(this.baseMapper.selectCount(wrapper) + 1);
        int insertFlag = this.baseMapper.insert(systemOrg);
        if (insertFlag > 0) {
            this.setOrgSeq(systemOrg);
            this.baseMapper.updateById(systemOrg);
            return "新增成功！";
        }
        throw new BusinessException("新增失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:17:01
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : get
     * SystemOrgDetailsVO
     *
     * @throws
     */
    @Override
    public SystemOrgDetailsVO get(Long id) {
        if (null == id) {
            throw new BusinessException("组织ID不能为空，请进行检查！");
        }
        SystemOrgPO systemOrg = this.baseMapper.selectById(id);
        if (null == systemOrg) {
            throw new BusinessException("获取组织失败，请刷新浏览器重新操作！");
        }
        SystemOrgDetailsVO result = new SystemOrgDetailsVO();
        BeanUtils.copyProperties(systemOrg, result);
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:17:03
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : edit
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String edit(SystemOrgEditDTO entity) {
        LambdaQueryWrapper<SystemOrgPO> wrapper = SystemOrgPO.wrapper();
        wrapper.eq(SystemOrgPO::getOrgName, entity.getOrgName());
        wrapper.ne(SystemOrgPO::getId, entity.getId());
        List<String> strings = this.baseMapper.editorgName(entity.getParentId(), entity.getId());
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            String orgName = entity.getOrgName();
            if (Objects.equals(orgName, s)) {
                throw new BusinessException("组织全称已存在，请进行检查！");
            }
        }
        Long id = entity.getId();
        Integer status = entity.getStatus();
        Long selectchildren = this.baseMapper.selectChildren(id);
        if (selectchildren > 0) {
            this.baseMapper.updateChildren(status, id);
        }
        SystemOrgPO systemOrg = new SystemOrgPO();
        BeanUtils.copyProperties(entity, systemOrg);
        this.setOrgSeq(systemOrg);
        int updateByIdFlag = this.baseMapper.updateById(systemOrg);
        if (updateByIdFlag > 0) {
            return "修改成功！";
        }
        throw new BusinessException("修改失败，请刷新浏览器重新操作！");
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月9日 下午2:17:06
     * 描述 : 删除【一条&多条】数据
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : delete
     * String
     *
     * @throws
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
            Exception.class})
    public String delete(SystemOrgDeleteDTO entity) {
        if (CollectionUtils.isEmpty(entity.getIds())) {
            throw new BusinessException("组织ID不能为空");
        }
        LambdaQueryWrapper<SystemOrgPO> wrapper = SystemOrgPO.wrapper();
        wrapper.in(SystemOrgPO::getId, entity.getIds());
        int updateFlag = this.baseMapper.update(new SystemOrgPO(true, false), wrapper);
        if (updateFlag > 0) {
            return "删除成功！";
        }
        throw new BusinessException("删除失败，请刷新浏览器重新操作！");
    }


    /**
     * 创建人 : yn
     * 创建时间 : 2024年10月11日 下午2:28:40
     * 描述 : 设置组织序列号(私有方法)
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : setOrgSeq
     * void
     *
     * @throws
     */
    private void setOrgSeq(SystemOrgPO systemOrg) {
        SystemOrgPO parent = this.getById(systemOrg.getParentId());
        if (parent != null) {
            systemOrg.setOrgSeq(parent.getOrgSeq() + systemOrg.getId() + ".");
        } else {
            systemOrg.setOrgSeq("." + systemOrg.getId() + ".");
        }
    }

    @Override
    public List<SystemOrgVO> selectList() {

        List<SystemOrgPO> systemOrgPOList = this.baseMapper.selectList(null);
        if (systemOrgPOList == null) {
            throw new BusinessException("查询失败!");
        }

        return systemOrgPOList.stream().map(t -> {
            SystemOrgVO systemOrgVO = new SystemOrgVO();
            BeanUtils.copyProperties(t, systemOrgVO);
            return systemOrgVO;
        }).collect(Collectors.toList());

    }

    @Override
    public List<SystemGroupOrgNameVO> selectGroupOrgName() {
        return this.baseMapper.selectGroupOrgName();
    }

    /**
     * 同步钉钉部门信息.
     *
     * @param deptList
     * @return
     */
    @Override
    @Transactional
    public String synchronizationOAOrgMassages(@RequestBody List<String> deptList) {
        DeptListPO deptListPO = new DeptListPO();
        deptListPO.setDeptIdList(deptList);
        List<RootDeptListVO> rootDeptListVOList = examineService.selectDeptList(deptListPO);
        List<SystemOrgPO> systemOrgPOS = this.baseMapper.selectList(null);

        for (RootDeptListVO rootDeptListVO : rootDeptListVOList) {
            for (Department department : rootDeptListVO.getDepartmentList()) {
                for (SystemOrgPO systemOrgPO : systemOrgPOS) {
                    if (department.getName().equals(systemOrgPO.getOrgName())) {
                        LambdaUpdateWrapper<SystemOrgPO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(SystemOrgPO::getOrgName, systemOrgPO.getOrgName());
                        updateWrapper.set(SystemOrgPO::getDingdingDeptId, department.getDeptId());
                        int update = this.baseMapper.update(null, updateWrapper);
                        if (update > 0) {
                            return "同步成功!";
                        }
                    }
                }
            }
        }
        return "同步失败!";
    }

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月18日 下午5:28:29
     * 描述 : 根据组织编码获取当前所在公司下所有组织编码
     * 包名 : com.ccit.area.sales.dao.mapper.archives
     * 方法名 : isExistOrgNo
     * String
     *
     * @throws
     */
    @Override
    public List<String> isExistOrgNo(String orgNo) {
        String orgNoAbbreviation = this.baseMapper.getCorporationOrg(orgNo);
        if (orgNoAbbreviation == null) {
            throw new BusinessException("组织编码不存在!");
        }
        String[] split = orgNoAbbreviation.split("_");

        return this.baseMapper.getOrgList(split[0]);
    }

    @Override
    public SystemOrgVO getFatherOrgNo(String orgNo) {
        String orgNoAbbreviation = this.baseMapper.getCorporationOrg(orgNo);
        if (orgNoAbbreviation == null) {
            throw new BusinessException("组织编码不存在!");
        }
        String[] split = orgNoAbbreviation.split("_");
        return this.baseMapper.getFatherOrgNo(split[0]);
    }

}
