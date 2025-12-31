package com.ccit.area.sales.feign.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ccit.area.sales.dto.SystemLogAddDTO;
import com.ccit.area.sales.dto.SystemLoginLogAddDTO;
import com.ccit.area.sales.feign.SystemFeignService;
import com.ccit.area.sales.vo.SystemDictListVO;

/**
 * 
 * 描述 : “管理系统”服务降级兜底类
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 下午1:54:11
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.feign.fallback
 * 类名 : SystemFeignFallback
 */
@Component
public class SystemFeignFallback implements SystemFeignService {

	@Override
	public String logAdd(SystemLogAddDTO entity) {
		return null;
	}

	@Override
	public String loginLogAdd(SystemLoginLogAddDTO entity) {
		return null;
	}

	@Override
	public List<SystemDictListVO> getseDictList() {
		return null;
	}

}
