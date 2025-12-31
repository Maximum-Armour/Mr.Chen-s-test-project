package com.ccit.area.sales.service.system;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemLogPO;
import com.ccit.area.sales.dao.dto.system.SystemLogAddDTO;
import com.ccit.area.sales.dao.vo.system.SystemLogPageListVO;
 
/**
 * 
 * 描述 : “操作日志记录”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:00:56
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemLogService
 */
public interface ISystemLogService extends IService<SystemLogPO> {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年10月30日 下午3:45:42
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectPageList
	 *  Page<SystemLogPageListVO> 
	 *  @throws
	 */
	Page<SystemLogPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午4:18:40
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemLogAddDTO entity);

}
