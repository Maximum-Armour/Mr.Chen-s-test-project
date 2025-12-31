package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemLoginLogPO;
import com.ccit.area.sales.dao.vo.system.SystemLoginLogPageListVO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;

import java.util.Map;

/**
 * 
 * 描述 : “登录日志”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月10日 下午4:31:36
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemLoginLogService
 */
public interface ISystemLoginLogService extends IService<SystemLoginLogPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:52:50
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemLoginLogAddDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月28日 下午3:45:42
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectPageList
	 *  Page<SystemLoginLogPageListVO>
	 *  @throws
	 */
	Page<SystemLoginLogPageListVO> selectPageList(Map<String, Object> param);
}
