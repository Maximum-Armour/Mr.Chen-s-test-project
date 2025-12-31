package com.ccit.area.sales.dao.mapper.archives;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.SkuFilePO;
import com.ccit.area.sales.dao.vo.archives.SkuFilePageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “牌号档案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:49:46
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : SkuFileMapper
 */
public interface SkuFileMapper extends BaseMapper<SkuFilePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:04:35
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<SkuFilePageListVO>  
	 *  @throws
	 */
	List<SkuFilePageListVO> selectPageList(Page<SkuFilePageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月15日 下午2:04:35
	 * 描述 : 生成编码
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : skuNoCount
	 *  Long
	 *  @throws
	 */
	Long skuNoCount();

}
