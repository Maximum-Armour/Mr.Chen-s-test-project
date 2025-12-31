package com.ccit.area.sales.dao.mapper.archives;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.archives.ProductClassPO;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “产品分类”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:49:24
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : ProductClassMapper
 */
public interface ProductClassMapper extends BaseMapper<ProductClassPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月19日 下午5:28:29
	 * 描述 : 获取各个子节点数量
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenCount
	 *  List<Map<String,Object>>
	 *  @throws
	 */
	List<Map<String, Object>> getChildrenCount(@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月17日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<ProductClassTreeVO>
	 *  @throws
	 */
	List<ProductClassTreeVO> getChildrenList(Long parentId);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月17日 下午5:28:29
	 * 描述 : 获取节点
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : getChildrenList
	 *  List<ProductClassTreeVO>
	 *  @throws
	 */
	List<ProductClassTreeVO> getChildrenLists(Long parentId, String productClassName, String status);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午5:28:29
	 * 描述 : 获取编码
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : productClassNoCount
	 *  Long
	 *  @throws
	 */
	Long productClassNoCount();

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
