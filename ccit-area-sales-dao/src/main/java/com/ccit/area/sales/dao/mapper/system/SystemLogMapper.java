package com.ccit.area.sales.dao.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.system.SystemLogPO;
import com.ccit.area.sales.dao.vo.system.SystemLogPageListVO;

/**
 * 
 * 描述 : “操作日志记录”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:55:16
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemLogMapper
 */
public interface SystemLogMapper extends BaseMapper<SystemLogPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午3:52:07
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.system
	 * 方法名 : selectPageList
	 *  List<SystemLogPageListVO>  
	 *  @throws
	 */
	List<SystemLogPageListVO> selectPageList(Page<SystemLogPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

}
