package com.ccit.area.sales.service.system.impl;

import com.ccit.area.sales.dao.vo.system.SystemCurrentUserMenuVO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;
import com.ccit.area.sales.dao.vo.system.SystemMenuTreeVO;
import com.ccit.area.sales.dao.vo.system.SystemOrgTreeVO;
import com.ccit.area.sales.service.system.ISystemTreeNodeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  * 描述 : “树节点”服务实现类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年6月15日 下午1:47:42
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.system.impl
 *  * 类名 : SystemTreeNodeServiceImpl
 */
@Service
public class SystemTreeNodeServiceImpl implements ISystemTreeNodeService {

    /**
     * 创建人 : yn
     * 创建时间 : 2024年6月15日 下午2:07:10
     * 描述 : 转化用户菜单树结构
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : changeDataUserMenuTree
     * List<SystemCurrentUserMenuVO>
     *
     * @throws
     */
    @Override
    public List<SystemCurrentUserMenuVO> changeDataUserMenuTree(List<SystemCurrentUserMenuVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<SystemCurrentUserMenuVO> nodeList = new ArrayList<>();
        boolean mark;
        for (SystemCurrentUserMenuVO node1 : list) {
            mark = false;
            for (SystemCurrentUserMenuVO node2 : list) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (CollectionUtils.isEmpty(node2.getChildren())) {
                        node2.setChildren(new ArrayList<>());
                    }
                    node2.setMenuIsLeaf(true);
                    node2.getChildren().add(node1);
                }
            }
            if (!mark) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年6月15日 下午9:34:45
     * 描述 : 转化菜单树结构
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : changeDataMenuTree
     * List<SystemMenuTreeVO>
     *
     * @throws
     */
    @Override
    public List<SystemMenuTreeVO> changeDataMenuTree(List<SystemMenuTreeVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<SystemMenuTreeVO> nodeList = new ArrayList<>();
        boolean mark;
        for (SystemMenuTreeVO node1 : list) {
            mark = false;
            for (SystemMenuTreeVO node2 : list) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (CollectionUtils.isEmpty(node2.getChildren())) {
                        node2.setChildren(new ArrayList<>());
                    }
                    node2.setMenuIsLeaf(true);
                    node2.getChildren().add(node1);
                    node2.setMaxOrders(node2.getMaxOrders() + 1);
                }
            }
            if (!mark) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年6月16日 上午9:41:41
     * 描述 : 转化字典树结构
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : changeDataDictTree
     * List<SystemDictTreeVO>
     *
     * @throws
     */
    @Override
    public List<SystemDictTreeVO> changeDataDictTree(List<SystemDictTreeVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<SystemDictTreeVO> nodeList = new ArrayList<>();
        boolean mark;
        for (SystemDictTreeVO node1 : list) {
            mark = false;
            for (SystemDictTreeVO node2 : list) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (CollectionUtils.isEmpty(node2.getChildren())) {
                        node2.setChildren(new ArrayList<>());
                    }
                    node2.setDictIsLeaf(true);
                    node2.getChildren().add(node1);
                }
            }
            if (!mark) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年9月25日 下午3:54:18
     * 描述 : 转化组织树结构
     * 包名 : com.ccit.area.sales.service.system.impl
     * 方法名 : changeDataOrgTree
     * List<SystemOrgTreeVO>
     *
     * @throws
     */
    @Override
    public List<SystemOrgTreeVO> changeDataOrgTree(List<SystemOrgTreeVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<SystemOrgTreeVO> nodeList = new ArrayList<>();
        boolean mark;
        for (SystemOrgTreeVO node1 : list) {
            mark = false;
            for (SystemOrgTreeVO node2 : list) {
                if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
                    mark = true;
                    if (CollectionUtils.isEmpty(node2.getChildren())) {
                        node2.setChildren(new ArrayList<>());
                    }
                    node2.setOrgIsLeaf(true);
                    node2.getChildren().add(node1);
                }
            }
            if (!mark) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }

}
