package com.ccit.area.sales.dao.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.system.SystemFilePO;
import com.ccit.area.sales.dao.vo.system.SystemFilePageListVO;

import java.util.List;

/**
 * 
 * 描述 : “附件”接口类
 * 创建人 : yn
 * 创建时间 : 2024年10月29日 上午09:57:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemFileMapper
 */
public interface SystemFileMapper extends BaseMapper<SystemFilePO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年10月31日 上午08:49:35
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : selectPageList
     *  List<SystemFilePageListVO>
     *  @throws
     */
    List<SystemFilePageListVO> selectPageList(String businessCode);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年11月6日 上午08:49:35
     * 描述 : 下载附件
     * 包名 : com.ccit.area.sales.dao.mapper.bidding
     * 方法名 : uploadFile
     *  String
     *  @throws
     */
    String uploadFile(String fileName, String businessId, String businessCode);
}