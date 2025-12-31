package com.ccit.area.sales.service.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.vo.sales.WeightPageListVO;



import java.util.Map;

/**
 *
  * 描述 : “分量管理”服务类
  * 创建人 : cf
  * 创建时间 : 2024年9月9日 下午15:50:20
  * 版本 : 1.0
  * 包名 : com.ccit.area.sales.service.sales
  * 类名 : QuantityManagementSevice
 */
public interface QuantityManagementSevice extends IService<ListedPO> {


    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年9月10日 下午1:50:54
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : selectPageList
     *  Page<WeightPageListVO>
     *  @throws
     */
    Page<WeightPageListVO> selectPageList(Map<String, Object> param);


    /**
     *
     * 创建人 : cf
     * 创建时间 : 2024年7月17日 下午16:53:03
     * 描述 : 根据主键ID查询相应数据
     * 包名 : com.ccit.area.sales.service.sales
     * 方法名 : get
     *  ListedPageListVO
     *  @throws
     */
    WeightPageListVO get(Long id);


}