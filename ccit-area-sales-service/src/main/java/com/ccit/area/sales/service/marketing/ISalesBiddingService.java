package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.SalesBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingAddDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingDeleteDTO;
import com.ccit.area.sales.dao.dto.marketing.SalesBiddingEditDTO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingDetailsVO;
import com.ccit.area.sales.dao.vo.marketing.SalesBiddingPageListVO;

import java.util.Map;

/**
 *
  * 描述 : “产品竞价”服务类
  * 创建人 : tb
  * 创建时间 : 2024年09月06日 上午10:58:05
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.service.bidding
  * 类名 : ISalesBiddingService
 */
public interface ISalesBiddingService extends IService<SalesBiddingPO> {

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月06日 上午10:58:25
     * 描述 : 新增一条数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : add
     *  String
     *  @throws
     */
    String add(SalesBiddingAddDTO entity);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午08:54:25
     * 描述 : 数据分页查询
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : selectPageList
     *  String
     *  @throws
     */
    Page<SalesBiddingPageListVO> selectPageList(Map<String, Object> param);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 上午09:10:05
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : get
     *  SalesBiddingDetailsVO
     *  @throws
     */
    SalesBiddingDetailsVO get(Long id);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:23:23
     * 描述 : 修改一条数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : edit
     *  SalesBiddingEditDTO
     *  @throws
     */
    String edit(SalesBiddingEditDTO entity);

    /**
     *
     * 创建人 : tb
     * 创建时间 : 2024年09月09日 下午03:55:22
     * 描述 : 删除【一条&多条】数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : delete
     *  SalesBiddingDeleteDTO
     *  @throws
     */
    String delete(SalesBiddingDeleteDTO entity);

    /**
     * 更新状态
     */
    String updateStatus(String tenderNumber,String status);
}
