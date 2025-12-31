package com.ccit.area.sales.dao.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceClassPO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceClassPageListVO;

/**
 * 
 * 描述 : “提货地分类”接口类
 * 创建人 : yn
 * 创建时间 : 2024年7月31日 下午4:44:28
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : DeliveryPlaceClassMapper
 */
public interface DeliveryPlaceClassMapper extends BaseMapper<DeliveryPlaceClassPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月1日 上午9:45:54
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<DeliveryPlaceClassPageListVO>  
	 *  @throws
	 */
	List<DeliveryPlaceClassPageListVO> selectPageList(Page<DeliveryPlaceClassPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);

}
