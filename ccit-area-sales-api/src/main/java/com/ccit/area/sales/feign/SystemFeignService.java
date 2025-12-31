package com.ccit.area.sales.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ccit.area.sales.dto.SystemLogAddDTO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.area.sales.feign.fallback.SystemFeignFallback;
import com.ccit.area.sales.vo.SystemDictListVO;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 
 * 描述 : “管理系统”Feign接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 下午1:53:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.feign
 * 类名 : SystemFeignService
 */
@FeignClient(contextId = "systemFeignService", value = "ccit-area-sales-web", fallbackFactory = SystemFeignFallback.class)
public interface SystemFeignService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:47:42
	 * 描述 : 新增操作日志
	 * 包名 : com.ccit.area.sales.feign
	 * 方法名 : logAdd
	 *  String  
	 *  @throws
	 */
    @Operation(summary = "新增操作日志")
    @PostMapping(value = "/api/system/log/add")
	String logAdd(SystemLogAddDTO entity);
    
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月10日 下午4:47:37
	 * 描述 : 新增登录日志
	 * 包名 : com.ccit.area.sales.feign
	 * 方法名 : loginLogAdd
	 *  String  
	 *  @throws
	 */
    @Operation(summary = "新增登录日志")
    @PostMapping(value = "/api/system/login/log/add")
	String loginLogAdd(SystemLoginLogAddDTO entity);
    
    /**
     * 
     * 创建人 : yn
     * 创建时间 : 2024年7月25日 下午2:01:08
     * 描述 : 字典列表
     * 包名 : com.ccit.area.sales.feign
     * 方法名 : getseDictList
     *  List<SystemDictListVO>  
     *  @throws
     */
    @Operation(summary = "字典列表")
	@PostMapping(value = "/api/system/dict/list")
	List<SystemDictListVO> getseDictList();
    
}
