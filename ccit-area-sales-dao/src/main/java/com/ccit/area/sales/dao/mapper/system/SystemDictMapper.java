package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.system.SystemDictPO;
import com.ccit.area.sales.dao.vo.system.SystemDictTreeVO;

import java.util.List;

/**
 * 
 * 描述 : “字典”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午9:13:39
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemDictMapper
 */
public interface SystemDictMapper extends BaseMapper<SystemDictPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月27日 下午5:28:29
     * 描述 : 获取子节点个数
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : childrenCount
     *  Long
     *  @throws
     */
    Long childrenCount(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月27日 下午5:28:29
     * 描述 : 获取子节点个数
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : childrenCount
     *  Long
     *  @throws
     */
    List<SystemDictTreeVO> selectChildren(Long id);
}
