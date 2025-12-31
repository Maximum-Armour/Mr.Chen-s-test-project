package com.ccit.area.sales.service.archives;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.archives.ProductClassPO;
import com.ccit.area.sales.dao.dto.archives.ProductClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassEditDTO;
import com.ccit.area.sales.dao.vo.archives.ProductClassDetailsVO;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
 
/**
 * 描述 : “产品分类表”服务类
 * 创建人 : yn
 * 创建时间 : 2024年07月15日 上午10:50:14
 * 版本 : 1.0
 * 包名 : com.chinacoal.microservice.service
 * 类名 : TProductClassService
 */
public interface IProductClassService extends IService<ProductClassPO> {
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:01
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : selectTreeList
	 *  List<ProductClassTreeVO>
	 *  @throws
	 */
	List<ProductClassTreeVO> selectTreeList(Map<String, Object> param);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:06
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	String add(ProductClassAddDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:10
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : get
	 *  ProductClassDetailsVO  
	 *  @throws
	 */
	ProductClassDetailsVO get(Long id);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:15
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	String edit(ProductClassEditDTO entity);

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:19
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	String delete(ProductClassDeleteDTO entity);
	
}
