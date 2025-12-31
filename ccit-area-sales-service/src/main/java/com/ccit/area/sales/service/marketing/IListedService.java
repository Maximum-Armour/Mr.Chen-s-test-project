package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.dingding.po.RabbitApprovalRequest;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.dto.marketing.ListedAddDTO;
import com.ccit.area.sales.dao.dto.marketing.ListedDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.ListedEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ListedSubmitDTO;
import com.ccit.area.sales.dao.vo.marketing.ListedDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.ListedPageListVO;

import java.util.Map;
 
/**
 * 	
 * 描述 : “挂牌”服务类
 * 创建人 : yn
 * 创建时间 : 2024年08月13日 下午04:05:04
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.marketing
 * 类名 : IListedService
 */
public interface IListedService extends IService<ListedPO> {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:04
	 * 描述 : 分页列表，支持分页查询和高级查询
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : selectPageList
	 *  Page<ListedPageListVO>
	 *  @throws
	 */
	Page<ListedPageListVO> selectPageList(Map<String, Object> param);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:04
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : add
	 *  String
	 *  @throws
	 */
	String add(ListedAddDTO entity);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:04
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : get
	 *  ListedDetailsVO
	 *  @throws
	 */
	ListedDetailsVO get(Long id);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:04
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : edit
	 *  String
	 *  @throws
	 */
	String edit(ListedEditDTO entity);
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年08月13日 下午04:05:04
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : delete
	 *  String
	 *  @throws
	 */
	String delete(ListedDeleteDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年10月31日 下午04:05:04
	 * 描述 : 挂牌提交
	 * 包名 : com.ccit.area.sales.service.marketing
	 * 方法名 : submit
	 *  String
	 *  @throws
	 */
	String submit(ListedSubmitDTO entity);

	/**
	 *
	 * 创建人 : tb
	 * 创建时间 : 2024年11月18日 下午03:46:35
	 * 描述 : 挂牌管理提交钉钉成功后续操作
	 * 包名 : com.ccit.area.sales.dao.mapper.marketing
	 * 方法名 : listedStatus
	 *  void
	 *  @throws
	 */
	void listedStatus(RabbitApprovalRequest rabbitApprovalRequest);
}
