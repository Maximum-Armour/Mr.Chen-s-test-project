package com.ccit.area.sales.dao.mapper.archives;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.SalesAreaPO;
import com.ccit.area.sales.dao.vo.archives.SalesAreaPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “销售区域”接口类
 * 创建人 : yn
 * 创建时间 : 2024年8月12日 下午2:38:31
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : SalesAreaMapper
 */
public interface SalesAreaMapper extends BaseMapper<SalesAreaPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月12日 下午3:08:21
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<SalesAreaPageListVO>
	 *  @throws
	 */
	List<SalesAreaPageListVO> selectPageList(Page<SalesAreaPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月18日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<SalesAreaPageListVO>
	 *  @throws
	 */
	List<SalesAreaPageListVO> selectList(String status);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月18日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<SalesAreaPageListVO>
	 *  @throws
	 */
	List<SalesAreaPageListVO> getChildrenList(String productClassNo);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月18日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<SalesAreaPageListVO>
	 *  @throws
	 */
	List<SalesAreaPageListVO> getChildrensList(String tradeType);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午5:28:29
	 * 描述 : 获取编码个数
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : areaNoCount
	 *  Long
	 *  @throws
	 */
	Long areaNoCount();
}
