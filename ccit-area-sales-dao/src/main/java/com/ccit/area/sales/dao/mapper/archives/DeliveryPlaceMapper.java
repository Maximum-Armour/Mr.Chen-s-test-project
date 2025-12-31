package com.ccit.area.sales.dao.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlacePO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlacePageListVO;

/**
 * 
 * 描述 : “提货地档案”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:44:45
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : DeliveryPlaceMapper
 */
public interface DeliveryPlaceMapper extends BaseMapper<DeliveryPlacePO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午10:16:50
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<DeliveryPlacePageListVO>  
	 *  @throws
	 */
	List<DeliveryPlacePageListVO> selectPageList(Page<DeliveryPlacePageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

}
