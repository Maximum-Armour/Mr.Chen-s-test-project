package com.ccit.area.sales.service.archives;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.DeliveryPlaceDepotPO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotAddDTO;
import com.ccit.area.sales.dao.dto.archives.DeliveryPlaceDepotDeleteDTO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotPageListVO;
import com.ccit.area.sales.dao.vo.archives.DeliveryPlaceDepotnoVO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 描述 : “提货地仓库中间表”服务类
 * 创建人 : yn
 * 创建时间 : 2024年8月7日 下午1:48:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives
 * 类名 : IDeliveryPlaceDepotService
 */
public interface IDeliveryPlaceDepotService extends IService<DeliveryPlaceDepotPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午1:52:51
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectPageList
	 *  Page<DeliveryPlaceDepotPageListVO>  
	 *  @throws
	 */
	Page<DeliveryPlaceDepotPageListVO> selectPageList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月7日 下午2:10:42
	 * 描述 : 修改【一条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String edit(DeliveryPlaceDepotAddDTO entity);


	String delete(DeliveryPlaceDepotDeleteDTO entity);

	List<String> selectdepotNo(DeliveryPlaceDepotAddDTO entity);


}
