package com.ccit.area.sales.common.aop.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ccit.area.sales.dto.SystemLogAddDTO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.area.sales.feign.SystemFeignService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 描述 : “异步”服务类
 * 创建人 : yn
 * 创建时间 : 2023年12月1日 下午3:08:01
 * 版本 : 1.0
 * 包名 : com.ccit.sem.common.aop.service
 * 类名 : AsyncService
 */
// @Slf4j
@Service
@RequiredArgsConstructor
public class AsyncService {
	
	/**
	 * “管理系统”Feign接口类
	 */
	private final SystemFeignService systemFeignService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2023年12月1日 下午3:08:23
	 * 描述 : 新增操作日志记录
	 * 包名 : com.ccit.sem.common.aop.service
	 * 方法名 : insertLog
	 *  void  
	 *  @throws
	 */
	@Async
	public void insertLog(SystemLogAddDTO entity) {
		systemFeignService.logAdd(entity);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:49:02
	 * 描述 : 新增登录日志
	 * 包名 : com.ccit.area.sales.common.aop.service
	 * 方法名 : insertLoginLog
	 *  void  
	 *  @throws
	 */
	@Async
	public void insertLoginLog(SystemLoginLogAddDTO entity) {
		systemFeignService.loginLogAdd(entity);
	}
	
}
