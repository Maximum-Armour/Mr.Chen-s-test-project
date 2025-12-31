package com.ccit.area.sales.dao.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.system.SystemConfigPO;
import com.ccit.area.sales.dao.vo.system.SystemConfigPageListVO;

/**
 * 
 * 描述 : “参数配置”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午10:52:58
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemConfigMapper
 */
public interface SystemConfigMapper extends BaseMapper<SystemConfigPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:30:49
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.system
	 * 方法名 : selectPageList
	 *  List<SystemConfigPageListVO>  
	 *  @throws
	 */
	List<SystemConfigPageListVO> selectPageList(Page<SystemConfigPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

}
