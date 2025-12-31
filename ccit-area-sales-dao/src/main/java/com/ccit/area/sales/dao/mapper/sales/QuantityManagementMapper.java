package com.ccit.area.sales.dao.mapper.sales;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccit.area.sales.dao.domain.marketing.ListedPO;
import com.ccit.area.sales.dao.vo.sales.WeightPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  * 描述 : “分量管理”接口类
 *  * 创建人 : cf
 *  * 创建时间 : 2024年9月9日 下午16:45:46
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.dao.mapper.sales
 *  * 类名 : QuantityManagementMapper
 */
public interface QuantityManagementMapper extends BaseMapper<ListedPO> {

    /**
     * 创建人 : cf
     * 创建时间 :2024年9月9日 下午16:45:46
     * 描述 : 分页列表，支持分页查询和高级查询
     * 包名 : com.ccit.area.sales.dao.mapper.sales
     * 方法名 : selectPageList
     * List<WeightPageListVO>
     *
     * @throws
     */
    List<WeightPageListVO> selectPageList(@Param(value = "param") Map<String, Object> param);

    WeightPageListVO selectlist(long id);

    List<WeightPageListVO> selectBiddingPageList(@Param(value = "param") Map<String, Object> param);
}
