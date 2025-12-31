package com.ccit.area.sales.dao.mapper.archives;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.MaterielPO;
import com.ccit.area.sales.dao.vo.archives.MaterielPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “产品档案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:49:01
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : MaterielMapper
 */
public interface MaterielMapper extends BaseMapper<MaterielPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月16日 下午3:06:21
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<MaterielPageListVO>  
	 *  @throws
	 */
	List<MaterielPageListVO> selectPageList(Page<MaterielPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午3:06:21
	 * 描述 : 编码生成
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : materielNoCount
	 *  Long
	 *  @throws
	 */
	Long materielNoCount();
}
