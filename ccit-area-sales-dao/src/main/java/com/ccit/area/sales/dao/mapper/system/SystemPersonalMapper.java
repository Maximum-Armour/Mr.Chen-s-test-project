package com.ccit.area.sales.dao.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.vo.system.SystemSecurityLogPageListVO;

/**
 * 
 * 描述 : “个人中心”接口
 * 创建人 : yn
 * 创建时间 : 2024年10月30日 下午4:12:12
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemPersonalMapper
 */
public interface SystemPersonalMapper {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年9月24日 下午3:30:14
	 * 描述 : 安全日志分页列表
	 * 包名 : com.ning.music.common.impl.system.mapper
	 * 方法名 : securityLogPageList
	 *  List<SystemSecurityLogPageListVO>  
	 *  @throws
	 */
	List<SystemSecurityLogPageListVO> securityLogPageList(Page<SystemSecurityLogPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);
	
}
