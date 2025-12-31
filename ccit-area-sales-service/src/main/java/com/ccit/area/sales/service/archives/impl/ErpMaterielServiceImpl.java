package com.ccit.area.sales.service.archives.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.dao.domain.archives.ErpMaterielPO;
import com.ccit.area.sales.dao.mapper.archives.ErpMaterielMapper;
import com.ccit.area.sales.dao.vo.archives.ErpMaterielPageListVO;
import com.ccit.area.sales.service.archives.IErpMaterielService;

/**
 * 
 * 描述 : “ERP产品档案”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午4:53:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : ErpMaterielServiceImpl
 */
@Service
public class ErpMaterielServiceImpl extends ServiceImpl<ErpMaterielMapper, ErpMaterielPO> implements IErpMaterielService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月29日 下午5:05:55
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectPageList
	 *  Page<ErpMaterielPageListVO>  
	 *  @throws
	 */
	@Override
	public Page<ErpMaterielPageListVO> selectPageList(Map<String, Object> param) {
		Integer page = (Integer) param.get("page"), size = (Integer) param.get("size");
		Page<ErpMaterielPageListVO> pages = null;
		if (page == null && size == null || page <= 0 && size <= 0) {
			pages = new Page<>(0, Integer.MAX_VALUE);
		} else {
			pages = new Page<>(page, size);
		}
		param.put("deleted", GlobalConstants.DELETE_NO);
		pages.setRecords(this.baseMapper.selectPageList(pages, param));
		return pages;
	}
			
}
