package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.system.SystemLoginLogPO;
import com.ccit.area.sales.dao.vo.system.SystemLoginLogPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “登录日志”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:30:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemLoginLogMapper
 */
public interface SystemLoginLogMapper extends BaseMapper<SystemLoginLogPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月28日 下午3:52:07
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.system
     * 方法名 : selectPageList
     *  List<SystemLoginLogPageListVO>
     *  @throws
     */
    List<SystemLoginLogPageListVO> selectPageList(Page<SystemLoginLogPageListVO> pages,
                                                  @Param(value = "param") Map<String, Object> param);
}
