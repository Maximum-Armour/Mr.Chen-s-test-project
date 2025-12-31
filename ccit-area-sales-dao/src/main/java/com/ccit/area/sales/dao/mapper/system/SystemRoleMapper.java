package com.ccit.area.sales.dao.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.system.SystemRolePO;
import com.ccit.area.sales.dao.vo.system.SystemRolePageListVO;

/**
 * 
 * 描述 : “角色”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月8日 下午5:39:25
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.system
 * 类名 : SystemRoleMapper
 */
public interface SystemRoleMapper extends BaseMapper<SystemRolePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午9:19:01
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.system
	 * 方法名 : selectPageList
	 *  List<SystemRolePageListVO>  
	 *  @throws
	 */
	List<SystemRolePageListVO> selectPageList(Page<SystemRolePageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

}
