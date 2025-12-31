package com.ccit.area.sales.service.archives;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.ErpMaterielPO;
import com.ccit.area.sales.dao.vo.archives.ErpMaterielPageListVO;
 
/**
 * 
 * 描述 : “ERP产品档案”服务类
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午4:52:27
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : IErpMaterielService
 */
public interface IErpMaterielService extends IService<ErpMaterielPO> {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月29日 下午5:05:44
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<ErpMaterielPageListVO>  
	 *  @throws
	 */
	Page<ErpMaterielPageListVO> selectPageList(Map<String, Object> param);
	
}
