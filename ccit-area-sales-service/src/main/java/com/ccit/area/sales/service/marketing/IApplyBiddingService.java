package com.ccit.area.sales.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccit.area.sales.dao.domain.marketing.ApplyBiddingPO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingAddDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingEditDTO;
import com.ccit.area.sales.dao.dto.marketing.ApplyBiddingSubmitDTO;
import com.ccit.area.sales.dao.vo.marketing.ApplyBiddingPageListVO;

import java.util.Map;

/**
 *  * 描述 : “竞价审核”服务类
 *  * 创建人 : tb
 *  * 创建时间 : 2024年09月12日 下午3:58:05
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.service.bidding
 *  * 类名 : IApplyBiddingService
 */
public interface IApplyBiddingService extends IService<ApplyBiddingPO> {

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午2:54:25
     * 描述 : 数据分页查询
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : selectPageList
     * String
     *
     * @throws
     */
    Page<ApplyBiddingPageListVO> selectPageList(Map<String, Object> param);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年09月12日 下午4:43:14
     * 描述 : 提交数据
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : submit
     * ApplyBiddingSubmitDTO
     *
     * @throws
     */
    String submit(ApplyBiddingSubmitDTO entity);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年10月16日 上午09:10:05
     * 描述 : 根据主键ID结果编辑
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : edit
     * ApplyBiddingEditDTO
     *
     * @throws
     */
    String edit(ApplyBiddingEditDTO entity);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年11月19日 上午09:10:05
     * 描述 : 报名新增
     * 包名 : com.ccit.area.sales.service.bidding
     * 方法名 : add
     * ApplyBiddingSignDTO
     *
     * @throws
     */
    String add(ApplyBiddingAddDTO entity);

    /**
     * 创建人 : tb
     * 创建时间 : 2024年12月20日 下午03:23:23
     * 描述 : 客户报价
     * 包名 : com.ccit.area.sales.web.controller.bidding
     * 方法名 : quote
     * ResponseVO<String>
     *
     * @throws
     */

    String updateApprovalStatus(Long id, String tenderNumber, String status);

    /**
     * 批量更改审批拒绝.
     */
    String batchUpdateApprovalStatus(String tenderNumber);
}
