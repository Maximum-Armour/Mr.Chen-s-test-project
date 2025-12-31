package com.ccit.area.sales.service.system;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemConfigPO;
import com.ccit.area.sales.dao.dto.system.SystemConfigAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemConfigEditDTO;
import com.ccit.area.sales.dao.vo.system.SystemConfigDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemConfigPageListVO;
 
/**
 * 
 * 描述 : “参数配置”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:00:01
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemConfigService
 */
public interface ISystemConfigService extends IService<SystemConfigPO> {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:08:17
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : selectPageList
	 *  Page<SystemConfigPageListVO>  
	 *  @throws
	 */
	Page<SystemConfigPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:08:21
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(SystemConfigAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:08:25
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : get
	 *  SystemConfigDetailsVO  
	 *  @throws
	 */
	SystemConfigDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:08:31
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(SystemConfigEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月9日 下午2:08:38
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(SystemConfigDeleteDTO entity);
	
}
