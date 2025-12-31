package com.ccit.area.sales.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.system.SystemUserOrgPO;
import com.ccit.area.sales.dao.dto.system.SystemOrgUpdateStatusDTO;

/**
 * 
 * 描述 : “用户组织中间”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月9日 上午11:01:14
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system
 * 类名 : ISystemUserOrgService
 */
public interface ISystemUserOrgService extends IService<SystemUserOrgPO> {
	
	
	

    String updateDisplayStatus(SystemOrgUpdateStatusDTO updateStatusDTO);

}
