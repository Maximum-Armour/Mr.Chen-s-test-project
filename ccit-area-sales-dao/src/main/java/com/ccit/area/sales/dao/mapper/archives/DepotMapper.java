package com.ccit.area.sales.dao.mapper.archives;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.DepotPO;
import com.ccit.area.sales.dao.vo.archives.DepotPageListVO;
import com.ccit.area.sales.dao.vo.archives.DepotTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “仓库档案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:44:57
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : DepotMapper
 */
public interface DepotMapper extends BaseMapper<DepotPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:14:27
	 * 描述 : 获取各个子节点数量
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenCount
	 *  List<Map<String,Object>>  
	 *  @throws
	 */
	List<Map<String, Object>> getChildrenCount(@Param(value = "param") Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 下午4:34:31
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<DepotPageListVO>
	 *  @throws
	 */
	List<DepotPageListVO> selectPageList(Page<DepotPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月23日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<DepotTreeVO>
	 *  @throws
	 */
	List<DepotTreeVO> getChildrenList(Long parentId);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月23日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<DepotTreeVO>
	 *  @throws
	 */
	List<DepotTreeVO> getChildrenLists(Long parentId, String depotName, String status);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午5:28:29
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : depotNoCount
	 *  Long
	 *  @throws
	 */
	Long depotNoCount();

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年12月6日 下午5:28:29
	 * 描述 : 停用父类更新子类
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : updatechildren
	 *  void
	 *  @throws
	 */
	void updateChildren(String status, Long parentId);
}
