package com.ccit.area.sales.dao.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.ErpMaterielPO;
import com.ccit.area.sales.dao.vo.archives.ErpMaterielPageListVO;

/**
 * 
 * 描述 : “ERP产品档案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月29日 下午4:51:37
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : ErpMaterielMapper
 */
public interface ErpMaterielMapper extends BaseMapper<ErpMaterielPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月29日 下午5:05:03
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<ErpMaterielPageListVO>  
	 *  @throws
	 */
	List<ErpMaterielPageListVO> selectPageList(Page<ErpMaterielPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);
	
}
