package com.ccit.area.sales.dao.mapper.archives;

import java.util.List;
import java.util.Map;

import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotAddDTO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceDepotPO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotPageListVO;

/**
 * 
 * 描述 : “提货地仓库中间表”接口类
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午1:49:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.dao.mapper.archives
 * 类名 : DeliveryPlaceDepotMapper
 */
public interface DeliveryPlaceDepotMapper extends BaseMapper<DeliveryPlaceDepotPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午1:53:42
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.dao.mapper.archives
	 * 方法名 : selectPageList
	 *  List<DeliveryPlaceDepotPageListVO>  
	 *  @throws
	 */
	List<DeliveryPlaceDepotPageListVO> selectPageList(Page<DeliveryPlaceDepotPageListVO> pages,
			@Param(value = "param") Map<String, Object> param);


	List<String> selectdepotNo(@Param(value = "entity") DeliveryPlaceDepotAddDTO entity);

	int updatedeliveryPlaceNo(String deliveryPlaceNo, String depotNo);



}